package com.example.jwt.service;

import com.example.jwt.config.jwt.JwtAuthenticationFilter;
import com.example.jwt.config.jwt.JwtProvider;
import com.example.jwt.domain.Role;
import com.example.jwt.domain.User;
import com.example.jwt.domain.jwt.TokenDTO;
import com.example.jwt.entity.UserEntity;
import com.example.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;

    @Transactional
    public UserEntity signUp(User user) throws Exception {

        UserEntity userEntity = UserEntity.builder()
                .email(user.getEmail())
                .password(bCryptPasswordEncoder.encode(user.getPassword()))
                .userName(user.getUserName())
                .role(Role.USER)
                .build();

        if(userEntity != null) {
            return userRepository.save(userEntity);
        } else {
            throw new Exception("가입이 실패하셨습니다.");
        }
    }

    public Optional<UserEntity> findById(Long userId) {
        Optional<UserEntity> byId = userRepository.findById(userId);
        return byId;
    }

    public TokenDTO login(String userEmail, String userPw) {


        UserEntity byEmailAndPassword = userRepository.findByEmailAndPassword(userEmail, userPw);

        // 1. Login ID/PW를 기반으로 Authentication 객체 생성
        // 이 때 authentication는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(byEmailAndPassword.getEmail(), byEmailAndPassword.getPassword());

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 해당 객체를 SecurityContextHolder에 저장하고
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // authentication 객체를 createToken 메소드를 통해서 JWT Token을 생성

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDTO tokenDTO = jwtProvider.createToken(authentication);

        HttpHeaders headers = new HttpHeaders();
        // response header에 jwt token을 넣어줌
        headers.add(JwtAuthenticationFilter.HEADER_AUTHORIZATION, "Bearer " + tokenDTO);

        return tokenDTO;
    }
}
