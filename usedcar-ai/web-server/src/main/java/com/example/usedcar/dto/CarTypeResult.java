package com.example.usedcar.dto;

import java.util.Map;

/**
 * React에 보내는 사진 분류 결과.
 * FastAPI 결과에 Spring이 판단한 값(needs_confirm, message)을 더한다.
 */
public record CarTypeResult(
        String carType,
        double confidence,
        Map<String, Double> scores,
        boolean needsConfirm,   // true면 화면에서 "직접 골라 주세요"를 보여 준다
        String message
) {
}
