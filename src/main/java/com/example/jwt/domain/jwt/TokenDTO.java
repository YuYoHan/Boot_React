package com.example.jwt.domain.jwt;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {
    // JWT 대한 인증 타입, 여기서는 Bearer를 사용하고
    // 이후 HTTP 헤더에 prefix로 붙여주는 타입
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
