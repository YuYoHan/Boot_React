package com.example.jwt.entity;


import com.example.jwt.domain.user.Role;
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
    private String userEmail;
    private String userName;
    private String userPw;
    @Enumerated(EnumType.STRING)
    private Role role;       // USER, ADMIN


    @Builder
    public UserEntity(String userEmail, String userName, String userPw, Role role) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPw = userPw;
        this.role = role;
    }
}
