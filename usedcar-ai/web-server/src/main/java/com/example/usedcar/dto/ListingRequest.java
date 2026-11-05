package com.example.usedcar.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * 판매글 작성 요청. 차 정보 5개 + 가격 + 메모 + 말투.
 */
public record ListingRequest(

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
        Integer accident,

        @NotNull(message = "가격을 입력해 주세요")
        @Min(value = 1, message = "가격은 1만 원 이상이어야 합니다")
        Integer price,

        @Size(max = 200, message = "메모는 200자 이하로 입력해 주세요")
        String memo,

        @Pattern(regexp = "친근하게|전문적으로|짧게", message = "말투는 친근하게, 전문적으로, 짧게 중 하나여야 합니다")
        String tone
) {
    public static final String DEFAULT_TONE = "친근하게";

    /** 말투를 비워 보내면 기본값을 채운 요청을 만든다. */
    public ListingRequest withDefaults() {
        String t = (tone == null || tone.isBlank()) ? DEFAULT_TONE : tone;
        String m = (memo == null) ? "" : memo.trim();
        return new ListingRequest(carType, yearAge, mileageKm, engineCc, accident, price, m, t);
    }
}
