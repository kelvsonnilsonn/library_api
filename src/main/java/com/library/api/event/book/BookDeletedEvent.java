package com.library.api.event.book;

import java.time.LocalDateTime;

public record BookDeletedEvent(
        Long bookId,
        String reason,
        Long userId,
        LocalDateTime createdAt
) {
    public BookDeletedEvent(Long bookId, String reason, Long userId){
        this(bookId, reason, userId, LocalDateTime.now());
    }
}
