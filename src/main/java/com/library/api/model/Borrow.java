package com.library.api.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "borrows")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private LocalDateTime borrowDate;
    private LocalDateTime dueDate;

    @Setter
    private LocalDateTime returnDate;

    public Borrow(User user, Book book, LocalDateTime dueDate){
        this.user = user;
        this.book = book;
        this.dueDate = dueDate;
        this.borrowDate = LocalDateTime.now();
    }

    public boolean isReturned(){
        return returnDate != null;
    }

    public Long getBookId(){
        return book.getId();
    }

    public boolean wasOverdue(){
        return dueDate.isAfter(returnDate);
    }
}
