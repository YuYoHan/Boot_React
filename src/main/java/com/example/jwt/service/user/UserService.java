package com.example.jwt.service.user;

import com.example.jwt.config.jwt.JwtAuthenticationFilter;
import com.example.jwt.config.jwt.JwtProvider;
import com.example.jwt.domain.user.Role;
import com.example.jwt.domain.user.UserDTO;
import com.example.jwt.domain.jwt.TokenDTO;
import com.example.jwt.entity.user.UserEntity;
import com.example.jwt.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;

    // 회원가입
    public String signUp(UserDTO userDTO) throws Exception {

        // 아이디가 없다면 DB에 넣어서 등록을 해준다.
        UserEntity userEntity = UserEntity.builder()
                .userEmail(userDTO.getUserEmail())
                .userPw(bCryptPasswordEncoder.encode(userDTO.getUserPw()))
                .userName(userDTO.getUserName())
                .role(Role.USER)
                .build();

        userRepository.save(userEntity);
        return "회원가입에 성공했습니다.";
    }

    // 아이디 조회
    public Optional<UserEntity> findById(Long userId) {
        Optional<UserEntity> byId = userRepository.findById(userId);
        return byId;
    }

    // 로그인
    public ResponseEntity<TokenDTO> login(String userEmail, String userPw) throws Exception {
        // 로그인시 건너 받은 이메일로 회원을 조회해보고 찾은 회원 정보를 담아준다.
//        UserEntity byEmail = userRepository.findByUserEmail(userEmail);
//        log.info("userEmail : " + byEmail);


            // 1. Login ID/PW를 기반으로 UsernamePasswordAuthenticationToken 생성
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userEmail, userPw);

            // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
            // authenticateToken을 이용해서 Authentication 객체를 생성하려고
            // authentication 매서드가 실행될 때
            // CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
            Authentication authentication = authenticationManagerBuilder
                    .getObject()
                    .authenticate(authenticationToken);

            // 해당 객체를 SecurityContextHolder에 저장하고
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // authentication 객체를 createToken 메소드를 통해서 JWT Token을 생성
            // 3. 인증 정보를 기반으로 JWT 토큰 생성
            TokenDTO tokenDTO = jwtProvider.createToken(authentication);

            HttpHeaders headers = new HttpHeaders();
            // response header에 jwt token을 넣어줌
            headers.add(JwtAuthenticationFilter.HEADER_AUTHORIZATION, "Bearer " + tokenDTO);

            return new ResponseEntity<>(tokenDTO, headers, HttpStatus.OK);
        }
}
