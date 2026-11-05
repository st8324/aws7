package com.example.usedcar.dto;

import java.util.Map;

/**
 * React에 보내는 상태 정보.
 * { "ai_server": true, "features": { "price": true, "car_type": false, "listing": false } }
 *
 * ai_server 가 false면 FastAPI가 꺼져 있다는 뜻이다.
 */
public record HealthResponse(boolean aiServer, Map<String, Boolean> features) {
}
