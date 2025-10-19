package com.library.api.event.borrow;

import java.time.LocalDateTime;

public record BookReturnedEvent(
        Long borrowId,
        Long bookId,
        Long userId,
        LocalDateTime returnDate,
        LocalDateTime borrowDate,
        LocalDateTime createdAt,
        boolean wasOverdue
) {
    public BookReturnedEvent(Long borrowId, Long bookId, Long userId, LocalDateTime returnDate, LocalDateTime borrowDate, boolean wasOverdue){
        this(borrowId, bookId, userId, returnDate, borrowDate, LocalDateTime.now(), wasOverdue);
    }
}
