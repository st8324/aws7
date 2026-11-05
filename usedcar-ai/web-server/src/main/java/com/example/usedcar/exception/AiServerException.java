package com.example.usedcar.exception;

import org.springframework.http.HttpStatus;

/**
 * FastAPI 호출 중 생긴 문제를 화면에 보여 줄 메시지와 상태 코드로 바꿔 담는다.
 * GlobalExceptionHandler 가 받아서 { "message": ... } 로 응답한다.
 */
public class AiServerException extends RuntimeException {

    private final HttpStatus status;

    public AiServerException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
