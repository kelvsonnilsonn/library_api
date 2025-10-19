package com.library.api.model;

import com.library.api.enums.UserRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Setter
    private String username;

    @Embedded
    private Password password;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User(String username, Password password){
        this.username = username;
        this.password = password;
        this.role = UserRole.USER_ROLE;
        this.createdAt = LocalDateTime.now();
    }

    public void changePassword(Password oPassword){
        this.password = oPassword;
    }

    public String getPassword(){
        return this.password.getPassword();
    }
}
