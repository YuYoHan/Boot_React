package com.example.jwt.controller;

import com.example.jwt.domain.User;
import com.example.jwt.domain.jwt.TokenDTO;
import com.example.jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authorize(@RequestBody User user) {

        String email = user.getEmail();
        String password = user.getPassword();

        TokenDTO login = userService.login(email, password);

        return ResponseEntity.ok().body(login);

    }
}
