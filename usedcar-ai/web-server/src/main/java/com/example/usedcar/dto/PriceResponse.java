package com.example.usedcar.dto;

/**
 * 시세 예측 응답. 가격 단위는 만 원.
 * FastAPI 응답과 React 응답이 같은 모양이다: { "price": 1850 }
 */
public record PriceResponse(Integer price) {
}
