package com.example.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {
    @Value("${jwt.password}")
    private String secretKey;

    // 토큰 생성 메소드
    public String createToken(String subject) {
        Date now = new Date();
        // 만료기간 1일
        Date expiration = new Date(now.getTime() + Duration.ofDays(1).toMillis());

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                // 토큰 발급자
                .setIssuer("test")
                // 발급시간
                .setIssuedAt(now)
                // 만료시간
                .setExpiration(expiration)
                // 토큰 제목
                .setSubject(subject)
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secretKey.getBytes()))
                .compact();
    }

    // JWt 토큰의 유효성 체크 메소드
    public Claims parseJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes()))
                .parseClaimsJws(token)
                .getBody();
    }

    // 토큰 앞 부분('Bearer') 제거 메소드
    private String BearerRemove(String token) {
        return token.substring("Bearer".length());
    }
}
