package com.example.usedcar.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS 설정.
 *
 * 브라우저(React, 3000)가 다른 주소(Spring, 8080)를 부를 때만 필요하다.
 * Spring → FastAPI 호출은 서버끼리의 호출이라 CORS와 상관없다.
 *
 * (React 개발 서버는 package.json 의 "proxy" 설정으로 /api 를 8080으로 넘기기 때문에
 *  개발 중에는 이 설정 없이도 동작한다. proxy 없이 직접 부를 때를 위해 넣어 둔다.)
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST");
    }
}
