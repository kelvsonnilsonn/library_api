package com.library.api.model;

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
    public String number;

    public ISBN(String number){
        validate(number);
        this.number = number;
    }

    private void validate(String number){
        if(number == null || !(number.matches("\\d{13}"))){
            throw new InvalidISBNException();
        }
    }
}

