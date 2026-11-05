package com.example.usedcar.dto;

import java.util.Map;

/**
 * FastAPI가 보내는 사진 분류 결과.
 * { "car_type": "SUV", "confidence": 0.87, "scores": { "경차": 0.05, "세단": 0.08, "SUV": 0.87 } }
 */
public record CarTypeResponse(
        String carType,
        Double confidence,
        Map<String, Double> scores
) {
}
