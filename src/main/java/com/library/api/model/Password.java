package com.library.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.library.api.exception.InvalidCreatePasswordException;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {
    private String password;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public Password(String password){
        this.password = password;
    }

    public static Password of(String oPassword, PasswordEncoder encoder){
        validate(oPassword);
        return new Password(encoder.encode(oPassword));
    }

    private static void validate(String pass){
        if(pass == null || pass.length() < 3){
            throw new InvalidCreatePasswordException();
        }
    }
}
