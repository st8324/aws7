package com.example.usedcar.dto;

/**
 * 판매글 작성 응답. { "title": "...", "body": "..." }
 */
public record ListingResponse(String title, String body) {
}
