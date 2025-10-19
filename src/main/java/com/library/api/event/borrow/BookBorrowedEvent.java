package com.library.api.event.borrow;

import java.time.LocalDateTime;

public record BookBorrowedEvent(
        Long borrowId,
        Long bookId,
        Long userId,
        LocalDateTime dueDate,
        LocalDateTime createdAt
) {
    public BookBorrowedEvent(Long borrowId, Long bookId, Long userId, LocalDateTime dueDate){
        this(borrowId, bookId, userId, dueDate, LocalDateTime.now());
    }
}
