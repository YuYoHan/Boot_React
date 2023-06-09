package com.example.jwt.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
// 자바 클래스를 프로퍼티 값을 가져와서 사용하는 어노테이션
@ConfigurationProperties("jwt")
public class JwtProperties {
    private String issuer;
    private String secretKey;
    private String tokenValidityInSecondes;
}
