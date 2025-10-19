package com.library.api.event.book;

import java.time.LocalDateTime;

public record BookCreatedEvent(
        Long bookId,
        String isbn,
        Long authorId,
        LocalDateTime createdAt
) {
    public BookCreatedEvent(Long bookId, String isbn, Long authorId){
        this(bookId, isbn, authorId, LocalDateTime.now());
    }
}
