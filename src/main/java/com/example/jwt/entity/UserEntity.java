package com.example.jwt.entity;


import com.example.jwt.domain.Role;
import com.example.jwt.domain.jwt.TokenDTO;
import lombok.*;

import javax.persistence.*;

@ToString
@Getter
@Entity
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String email;
    private String userName;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;       // USER, ADMIN

    @Builder
    public UserEntity(String email, String userName, String password, Role role) {
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.role = role;
    }
}
