package com.library.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.library.api.exception.InvalidISBNException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ISBN {
    @Column(name = "isbn_number", unique = true, nullable = false)
    private String number;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public ISBN(String number){
        validate(number);
        this.number = number;
    }

    @JsonValue
    public String getNumber(){
        return number;
    }

    private void validate(String number){
        if(number == null || !(number.matches("\\d{13}"))){
            throw new InvalidISBNException();
        }
    }
}

