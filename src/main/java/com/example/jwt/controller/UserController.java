package com.example.jwt.controller;

import com.example.jwt.domain.Role;
import com.example.jwt.domain.User;
import com.example.jwt.entity.UserEntity;
import com.example.jwt.repository.UserRepository;
import com.example.jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @PostMapping("/")
    public ResponseEntity<?> join(@RequestBody User user) throws Exception {

        UserEntity userEntity = userService.signUp(user);

        return ResponseEntity.ok().body(userEntity);

    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody User user) {
//        log.info("로그인 유저 : " + user);
//
//        String email = user.getEmail();
//        String password = user.getPassword();
//
//        UserEntity byEmailAndPassword = userRepository.findByEmailAndPassword(email, password);
//
//        return ResponseEntity.ok().body(byEmailAndPassword);
//
//    }


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
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(
                request,
                response,
                SecurityContextHolder.getContext().getAuthentication());
        return "로그아웃 했습니다.";
    }
}
