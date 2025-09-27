package com.library.api.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Embedded
    private Password password;

    private LocalDateTime createdAt;

    public User(String username, Password password){
        this.username = username;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }

    public void changePassword(String oPassword){
        this.password = password.of(oPassword);
    }
}
