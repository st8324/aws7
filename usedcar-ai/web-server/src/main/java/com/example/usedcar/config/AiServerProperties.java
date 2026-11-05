package com.example.usedcar.config;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * application.yml 의 ai-server 설정을 읽는다.
 */
@ConfigurationProperties(prefix = "ai-server")
public record AiServerProperties(
        String baseUrl,
        Duration connectTimeout,
        Timeout timeout
) {
    public record Timeout(
            Duration health,
            Duration price,
            Duration carType,
            Duration listing
    ) {
    }
}
