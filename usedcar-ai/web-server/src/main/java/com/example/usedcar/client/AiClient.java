package com.example.usedcar.client;

import java.net.SocketTimeoutException;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.multipart.MultipartFile;

import com.example.usedcar.dto.CarTypeResponse;
import com.example.usedcar.dto.FastApiHealth;
import com.example.usedcar.dto.ListingRequest;
import com.example.usedcar.dto.ListingResponse;
import com.example.usedcar.dto.PriceRequest;
import com.example.usedcar.dto.PriceResponse;
import com.example.usedcar.exception.AiServerException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * FastAPI(8000)를 부르는 곳. Spring에서 FastAPI와 이야기하는 코드는 여기에만 있다.
 *
 *   GET  /health             → health()
 *   POST /price/predict      → predictPrice()
 *   POST /car-type/classify  → classifyCarType()
 *   POST /listing/write      → writeListing()
 *
 * FastAPI에서 생긴 문제는 전부 AiServerException 으로 바꿔서 던진다.
 */
@Component
public class AiClient {

    private static final Logger log = LoggerFactory.getLogger(AiClient.class);

    private final RestClient healthClient;
    private final RestClient priceClient;
    private final RestClient carTypeClient;
    private final RestClient listingClient;
    private final ObjectMapper objectMapper;

    public AiClient(@Qualifier("healthClient") RestClient healthClient,
                    @Qualifier("priceClient") RestClient priceClient,
                    @Qualifier("carTypeClient") RestClient carTypeClient,
                    @Qualifier("listingClient") RestClient listingClient,
                    ObjectMapper objectMapper) {
        this.healthClient = healthClient;
        this.priceClient = priceClient;
        this.carTypeClient = carTypeClient;
        this.listingClient = listingClient;
        this.objectMapper = objectMapper;
    }

    /** FastAPI 상태. 꺼져 있거나 응답이 이상하면 null */
    public FastApiHealth health() {
        try {
            return healthClient.get()
                    .uri("/health")
                    .retrieve()
                    .body(FastApiHealth.class);
        } catch (RestClientException e) {
            log.warn("FastAPI /health 실패: {}", e.getMessage());
            return null;
        }
    }

    /** 1단계: 시세 예측 */
    public PriceResponse predictPrice(PriceRequest request) {
        return call("/price/predict", () -> priceClient.post()
                .uri("/price/predict")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(PriceResponse.class));
    }

    /** 2단계: 사진으로 차종 분류. 받은 파일을 그대로 multipart로 넘긴다 */
    public CarTypeResponse classifyCarType(MultipartFile file) {
    	String filename = file.getOriginalFilename() == null ? "upload" : file.getOriginalFilename();
        String contentType = file.getContentType() == null
                ? MediaType.APPLICATION_OCTET_STREAM_VALUE : file.getContentType();

        HttpHeaders partHeaders = new HttpHeaders();
        partHeaders.setContentDisposition(ContentDisposition.formData().name("file").filename(filename).build());
        partHeaders.setContentType(MediaType.parseMediaType(contentType));

        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("file", new HttpEntity<>(file.getResource(), partHeaders));

        return call("/car-type/classify", () -> carTypeClient.post()
                .uri("/car-type/classify")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(parts)
                .retrieve()
                .body(CarTypeResponse.class));
    }

    /** 3단계: 판매글 작성 */
    public ListingResponse writeListing(ListingRequest request) {
        return call("/listing/write", () -> listingClient.post()
                .uri("/listing/write")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(ListingResponse.class));
    }

    /**
     * FastAPI 호출을 감싸서, 생길 수 있는 문제를 화면용 메시지로 바꾼다.
     * 학생이 어디가 문제인지 바로 알 수 있게 메시지를 구체적으로 쓴다.
     */
    private <T> T call(String path, Supplier<T> request) {
        try {
            return request.get();

        } catch (RestClientResponseException e) {
            // FastAPI가 응답은 했는데 200이 아님
            int code = e.getStatusCode().value();
            String detail = extractDetail(e.getResponseBodyAsString());
            log.warn("FastAPI {} → {} {}", path, code, e.getResponseBodyAsString());

            throw switch (code) {
                case 400 -> new AiServerException(HttpStatus.BAD_REQUEST,
                        detail != null ? detail : "입력을 확인해 주세요");
                case 422 -> new AiServerException(HttpStatus.BAD_REQUEST,
                        "입력을 확인해 주세요 (AI 서버가 요청 형식을 받아들이지 않았습니다)");
                case 404 -> new AiServerException(HttpStatus.SERVICE_UNAVAILABLE,
                        "AI 서버에 " + path + " 주소가 아직 없습니다");
                case 503 -> new AiServerException(HttpStatus.SERVICE_UNAVAILABLE,
                        detail != null ? detail : "잠시 후 다시 시도해 주세요");
                default -> new AiServerException(HttpStatus.BAD_GATEWAY,
                        "AI 서버 코드에서 오류가 났습니다 (" + code + "). FastAPI 터미널의 에러를 확인하세요");
            };

        } catch (ResourceAccessException e) {
            // 연결 자체가 안 됨, 또는 시간 초과
            log.warn("FastAPI {} 연결 실패: {}", path, e.getMessage());
            if (isReadTimeout(e)) {
                throw new AiServerException(HttpStatus.GATEWAY_TIMEOUT,
                        "AI 서버 응답이 너무 늦습니다. 잠시 후 다시 시도해 주세요");
            }
            throw new AiServerException(HttpStatus.SERVICE_UNAVAILABLE,
                    "AI 서버에 연결할 수 없습니다. FastAPI(8000)가 켜져 있는지 확인하세요");

        } catch (RestClientException e) {
            // 응답은 왔는데 JSON으로 읽을 수 없음
            log.warn("FastAPI {} 응답 해석 실패: {}", path, e.getMessage());
            throw new AiServerException(HttpStatus.BAD_GATEWAY,
                    "AI 서버 응답 형식이 명세와 다릅니다 (" + path + ")");
        }
    }

    /** FastAPI 에러 응답 { "detail": "..." } 에서 메시지를 꺼낸다. 문자열이 아니면 null */
    private String extractDetail(String body) {
        try {
            JsonNode detail = objectMapper.readTree(body).get("detail");
            return (detail != null && detail.isTextual()) ? detail.asText() : null;
        } catch (Exception e) {
            return null;
        }
    }

    private boolean isReadTimeout(Throwable e) {
        for (Throwable t = e; t != null; t = t.getCause()) {
            if (t instanceof SocketTimeoutException && t.getMessage() != null
                    && t.getMessage().toLowerCase().contains("read timed out")) {
                return true;
            }
        }
        return false;
    }
}
