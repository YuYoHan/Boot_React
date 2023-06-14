package com.example.jwt.controller;

import com.example.jwt.domain.user.LoginDTO;
import com.example.jwt.domain.user.UserDTO;
import com.example.jwt.domain.jwt.TokenDTO;
import com.example.jwt.entity.user.UserEntity;
import com.example.jwt.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/")
    // BindingResult 타입의 매개변수를 지정하면 BindingResult 매개 변수가 입력값 검증 예외를 처리한다.
    public ResponseEntity<?> join(@Validated @RequestBody UserDTO userDTO,
                                  BindingResult result) throws Exception {

        // 입렵값 검증 예외가 발생하면 예외 메시지를 응답한다.
        if(result.hasErrors()) {
            log.info("result.hasErrors() : " + result.hasErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getClass().getSimpleName());
        }

        try {
            String join = userService.signUp(userDTO);
            // 아이디가 있으면 아이디가 존재합니다.가 리턴
            // 아이디가 없으면 회원가입에 성공했습니다. 가 리턴
            return ResponseEntity.ok().body(join);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }


    }

    @GetMapping("/api/v1/users/{id}")
    public ResponseEntity<?> search(@PathVariable Long id) throws Exception{
        try {
            Optional<UserEntity> byId = userService.findById(id);
            return ResponseEntity.ok().body(byId);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) throws Exception {

        try {
            ResponseEntity<TokenDTO> login =
                    userService.login(userDTO.getUserEmail(), userDTO.getUserPw());

            return ResponseEntity.ok().body(login);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }


//    @GetMapping("/user")
//    // @PreAuthorize("hasAnyRole('USER', 'ADMIN')") : 이런식으로 어노테이션을 추가하면
//    // 권한 별로 접근을 통제하게 됩니다. 이 기능을 사용하려면 SecurityConfig에서
//    // @EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)를
//    // 추가해서 활성화를 시켜 줘야 합니다.
//    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
//    public ResponseEntity<UserEntity> getMyUserInfo() {
//    }



    // /logout 요청을 하면 로그아웃을 담당하는 핸들러인 SecurityContextLogoutHandler의
    // logout() 메소드를 호출해서 로그아웃합니다.
    @GetMapping("/api/v1/users/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(
                request,
                response,
                SecurityContextHolder.getContext().getAuthentication());
        return "로그아웃 했습니다.";
    }
}
