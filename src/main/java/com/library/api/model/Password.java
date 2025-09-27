package com.library.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.library.api.exception.InvalidCreatePasswordException;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {
    public String password;

    @JsonCreator
    public Password(@JsonProperty("password") String password){
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
