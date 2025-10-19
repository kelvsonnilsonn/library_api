package com.library.api.event.user;

import java.time.LocalDateTime;

public record UserDeletedEvent(
        Long userId,
        String reason,
        Long adminId,
        LocalDateTime createdAt
) {
    public UserDeletedEvent(Long userId, String reason, Long adminId){
        this(userId, reason, adminId, LocalDateTime.now());
    }
}
