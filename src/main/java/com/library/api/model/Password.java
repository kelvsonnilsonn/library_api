package com.library.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.library.api.exception.InvalidCreatePasswordException;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {
    private String password;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public Password(String password){
        validate(password);
        this.password = password;
    }

    public Password of(String oPassword){
        return new Password(oPassword);
    }

    private void validate(String pass){
        if(pass == null || pass.length() < 3){
            throw new InvalidCreatePasswordException();
        }
    }
}
