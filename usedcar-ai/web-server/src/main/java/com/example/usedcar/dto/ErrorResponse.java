package com.example.usedcar.dto;

/**
 * React에 보내는 에러. 화면에는 message를 그대로 보여 준다.
 * { "message": "AI 서버에 연결할 수 없습니다" }
 */
public record ErrorResponse(String message) {
}
