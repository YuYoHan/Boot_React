package com.example.jwt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // 모든 ip에 응답을 허용
                .allowedOrigins("/*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")    // 허용되는 헤더
                // 내서버가 응답을 할때 json을 자바스크립트에서 처리할 수 있게 할지를 설정
                .allowCredentials(true) // 자격증명 허용
                .maxAge(3600);          // 허용시간
    }

}
