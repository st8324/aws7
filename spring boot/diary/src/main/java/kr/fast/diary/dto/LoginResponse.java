package kr.fast.diary.dto;

public record LoginResponse(boolean success, String message, String accessToken) {

}
