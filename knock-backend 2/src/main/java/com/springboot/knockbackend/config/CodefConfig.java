// src/main/java/com/springboot/knockbackend/config/CodefConfig.java
package com.springboot.knockbackend.config;

import com.springboot.knockbackend.properties.CodefProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CodefConfig {

    private final CodefProperties props;

    public CodefConfig(CodefProperties props) {
        this.props = props;
    }

    @Bean
    public RestTemplate restTemplate() {
        // 타임아웃 설정 포함 RestTemplate 생성 예시
        var factory = new org.springframework.http.client.SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(props.getTimeout() * 1000);
        factory.setReadTimeout(props.getTimeout() * 1000);
        return new RestTemplate(factory);
    }
}