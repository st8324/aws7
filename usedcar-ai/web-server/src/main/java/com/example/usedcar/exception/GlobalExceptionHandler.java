package com.example.usedcar.exception;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.example.usedcar.dto.ErrorResponse;

/**
 * 모든 에러를 { "message": "..." } 한 가지 모양으로 바꿔서 React에 보낸다.
 * React는 message 를 그대로 화면에 보여 주면 된다.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /** FastAPI 호출 중 생긴 문제 (AiClient, AiService 에서 던진다) */
    @ExceptionHandler(AiServerException.class)
    public ResponseEntity<ErrorResponse> handleAiServer(AiServerException e) {
        return ResponseEntity.status(e.getStatus()).body(new ErrorResponse(e.getMessage()));
    }

    /** @Valid 검증 실패: 범위 밖 값, 빈 값 */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getDefaultMessage())
                .distinct()
                .collect(Collectors.joining(", "));
        return ResponseEntity.badRequest().body(new ErrorResponse(message));
    }

    /** JSON 모양이 틀림 (숫자 칸에 글자 등) */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleNotReadable(HttpMessageNotReadableException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse("요청 형식이 올바르지 않습니다"));
    }

    /** 사진이 5MB보다 큼 (application.yml 의 max-file-size) */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleTooLarge(MaxUploadSizeExceededException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse("사진은 5MB 이하만 올릴 수 있습니다"));
    }

    /** 사진 파일 없이 요청함 */
    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ErrorResponse> handleMissingFile(MissingServletRequestPartException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse("사진 파일을 올려 주세요"));
    }

    /** 없는 주소를 부름 */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NoResourceFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("없는 주소입니다"));
    }

    /** 그 밖의 예상하지 못한 에러 */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOthers(Exception e) {
        log.error("처리하지 못한 에러", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("서버에서 오류가 났습니다"));
    }
}
