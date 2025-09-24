package com.library.api.model;

import com.library.api.enums.BookType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="author_id")
    private Client author;

    private LocalDateTime createdAt;
    private boolean isBorrowed;

    @Enumerated(EnumType.STRING)
    private BookType type;

    public Book(String author, BookType type){}

}
