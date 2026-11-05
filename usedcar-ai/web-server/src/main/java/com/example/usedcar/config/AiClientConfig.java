package com.example.usedcar.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

/**
 * FastAPI를 부르는 RestClient를 기능별로 만든다.
 *
 * 기능마다 기다리는 시간(timeout)이 다르다.
 *   시세 예측: 계산이 빠르다 → 3초
 *   사진 분류: 딥러닝 모델이 CPU에서 돈다 → 15초
 *   판매글:   외부 LLM API를 한 번 더 부른다 → 30초
 *
 * RestClient.Builder 는 Spring이 만들어 준 것을 쓴다.
 * 그래야 application.yml 의 snake_case 설정이 FastAPI와 주고받는 JSON에도 적용된다.
 */
@Configuration
public class AiClientConfig {

    private final AiServerProperties props;

    public AiClientConfig(AiServerProperties props) {
        this.props = props;
    }

    @Bean
    public RestClient healthClient(RestClient.Builder builder) {
        return build(builder, props.timeout().health());
    }

    @Bean
    public RestClient priceClient(RestClient.Builder builder) {
        return build(builder, props.timeout().price());
    }

    @Bean
    public RestClient carTypeClient(RestClient.Builder builder) {
        return build(builder, props.timeout().carType());
    }

    @Bean
    public RestClient listingClient(RestClient.Builder builder) {
        return build(builder, props.timeout().listing());
    }

    private RestClient build(RestClient.Builder builder, Duration readTimeout) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout((int) props.connectTimeout().toMillis());
        factory.setReadTimeout((int) readTimeout.toMillis());

        return builder.clone()          // 기능마다 따로 설정하려고 복사해서 쓴다
                .baseUrl(props.baseUrl())
                .requestFactory(factory)
                .build();
    }
}
