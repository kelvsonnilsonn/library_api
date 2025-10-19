package com.library.api.model;

import com.library.api.enums.BookType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    private ISBN isbn;

    private String title;

    @ManyToOne
    @JoinColumn(name="author_id")
    private User author;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private BookType type;

    public Book(User author, BookType type, ISBN isbn, String title){
        this.author = author;
        this.type = type;
        this.isbn = isbn;
        this.title = title;
        this.createdAt = LocalDateTime.now();
    }

    public String getIsbnNumber(){
        return isbn.getNumber();
    }

    public Long getAuthorId(){
        return this.author.getId();
    }

}
