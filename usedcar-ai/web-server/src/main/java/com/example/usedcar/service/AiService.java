package com.example.usedcar.service;

import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.usedcar.client.AiClient;
import com.example.usedcar.dto.CarTypeResponse;
import com.example.usedcar.dto.CarTypeResult;
import com.example.usedcar.dto.FastApiHealth;
import com.example.usedcar.dto.HealthResponse;
import com.example.usedcar.dto.ListingRequest;
import com.example.usedcar.dto.ListingResponse;
import com.example.usedcar.dto.PriceRequest;
import com.example.usedcar.dto.PriceResponse;
import com.example.usedcar.exception.AiServerException;

/**
 * FastAPI 결과를 받아서 화면에 보내기 전에 한 번 더 확인하고 다듬는다.
 *
 * DB가 없어도 Spring이 하는 일:
 *   - 입력 검증 (DTO의 @Valid)
 *   - 결과 후처리 (음수 가격 자르기, 확신이 낮은 분류 표시)
 *   - FastAPI 응답이 명세와 다를 때 알려 주기
 *   - API 키 같은 비밀 값은 브라우저에 보내지 않기 (키는 FastAPI의 .env 에만 있다)
 */
@Service
public class AiService {

    private static final Set<String> CAR_TYPES = Set.of("경차", "세단", "SUV");
    private static final double CONFIDENCE_THRESHOLD = 0.5;
    private static final Set<String> IMAGE_TYPES = Set.of("image/jpeg", "image/png");

    private static final Map<String, Boolean> ALL_OFF =
            Map.of("price", false, "car_type", false, "listing", false);

    private final AiClient aiClient;

    public AiService(AiClient aiClient) {
        this.aiClient = aiClient;
    }

    /** FastAPI가 꺼져 있어도 에러를 내지 않고 "모두 준비 중"으로 알려 준다 */
    public HealthResponse health() {
        FastApiHealth h = aiClient.health();
        if (h == null) {
            return new HealthResponse(false, ALL_OFF);
        }
        Map<String, Boolean> f = h.features() == null ? Map.of() : h.features();
        return new HealthResponse(true, Map.of(
                "price", Boolean.TRUE.equals(f.get("price")),
                "car_type", Boolean.TRUE.equals(f.get("car_type")),
                "listing", Boolean.TRUE.equals(f.get("listing"))
        ));
    }

    /** 1단계: 시세 예측 */
    public PriceResponse predictPrice(PriceRequest request) {
        PriceResponse res = aiClient.predictPrice(request);

        if (res == null || res.price() == null) {
            throw specMismatch("시세 예측 응답에 price가 없습니다");
        }
        // 모델은 직선을 늘려서 답하기 때문에 음수가 나올 수 있다. 가격은 0보다 작을 수 없다.
        return new PriceResponse(Math.max(0, res.price()));
    }

    /** 2단계: 사진으로 차종 분류 */
    public CarTypeResult classifyCarType(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new AiServerException(HttpStatus.BAD_REQUEST, "사진 파일을 올려 주세요");
        }
        if (file.getContentType() == null || !IMAGE_TYPES.contains(file.getContentType())) {
            throw new AiServerException(HttpStatus.BAD_REQUEST, "jpg 또는 png 사진만 올릴 수 있습니다");
        }

        CarTypeResponse res = aiClient.classifyCarType(file);

        if (res == null || res.carType() == null) {
            throw specMismatch("사진 분류 응답에 car_type이 없습니다");
        }
        if (!CAR_TYPES.contains(res.carType())) {
            throw specMismatch("car_type은 경차, 세단, SUV 중 하나여야 합니다 (받은 값: " + res.carType() + ")");
        }

        double confidence = res.confidence() == null ? 0.0 : res.confidence();
        boolean needsConfirm = confidence < CONFIDENCE_THRESHOLD;
        String message = needsConfirm
                ? "확실하지 않아요. 차종을 직접 확인해 주세요"
                : res.carType() + "(으)로 보여요";

        return new CarTypeResult(
                res.carType(),
                confidence,
                res.scores() == null ? Map.of() : res.scores(),
                needsConfirm,
                message
        );
    }

    /** 3단계: 판매글 작성 */
    public ListingResponse writeListing(ListingRequest request) {
        ListingResponse res = aiClient.writeListing(request.withDefaults());

        if (res == null || isBlank(res.title()) || isBlank(res.body())) {
            throw specMismatch("판매글 응답에 title 또는 body가 없습니다");
        }
        return new ListingResponse(res.title().trim(), res.body().trim());
    }

    private AiServerException specMismatch(String message) {
        return new AiServerException(HttpStatus.BAD_GATEWAY, "AI 서버 응답이 명세와 다릅니다: " + message);
    }

    private boolean isBlank(String s) {
        return s == null || s.isBlank();
    }
}
