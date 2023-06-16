package com.example.jwt.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // JWT 대한 인증 타입, 여기서는 Bearer를 사용하고
    // 이후 HTTP 헤더에 prefix로 붙여주는 타입
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private String userEmail;

    @Builder
    public TokenEntity(Long id, String grantType, String accessToken, String refreshToken, String userEmail) {
        this.id = id;
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userEmail = userEmail;
    }
}
