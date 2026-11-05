package com.example.usedcar.dto;

import java.util.Map;

/**
 * FastAPI GET /health 응답.
 * { "status": "ok", "features": { "price": true, "car_type": false, "listing": false } }
 */
public record FastApiHealth(String status, Map<String, Boolean> features) {
}
