package com.library.api.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Embedded
    private Password password;

    public User(String username, String password){
        this.username = username;
        this.password = new Password(password);
    }

    public void changePassword(String oPassword){
        this.password = password.of(oPassword);
    }
}
