package com.example.jwt.service;

import com.example.jwt.domain.Role;
import com.example.jwt.domain.User;
import com.example.jwt.entity.UserEntity;
import com.example.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
}
