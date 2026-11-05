package com.example.usedcar.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * 시세 예측 요청. JSON에서는 car_type, year_age ... (snake_case)
 *
 * 허용 범위 = 학습 데이터(used_cars.csv)의 범위.
 * 범위 밖 값은 모델이 본 적 없는 값이라 FastAPI까지 보내지 않고 여기서 막는다.
 */
public record PriceRequest(

        @NotNull(message = "차종을 골라 주세요")
        @Pattern(regexp = "경차|세단|SUV", message = "차종은 경차, 세단, SUV 중 하나여야 합니다")
        String carType,

        @NotNull(message = "연식을 입력해 주세요")
        @Min(value = 0, message = "연식은 0~15년이어야 합니다")
        @Max(value = 15, message = "연식은 0~15년이어야 합니다")
        Integer yearAge,

        @NotNull(message = "주행거리를 입력해 주세요")
        @Min(value = 5000, message = "주행거리는 5,000~200,000km여야 합니다")
        @Max(value = 200000, message = "주행거리는 5,000~200,000km여야 합니다")
        Integer mileageKm,

        @NotNull(message = "배기량을 입력해 주세요")
        @Min(value = 1000, message = "배기량은 1,000~3,500cc여야 합니다")
        @Max(value = 3500, message = "배기량은 1,000~3,500cc여야 합니다")
        Integer engineCc,

        @NotNull(message = "사고 이력을 골라 주세요")
        @Min(value = 0, message = "사고 이력은 0 또는 1이어야 합니다")
        @Max(value = 1, message = "사고 이력은 0 또는 1이어야 합니다")
        Integer accident
) {
}
