package com.library.api.event.user;

import java.time.LocalDateTime;

public record UserCreatedEvent(
        Long userId,
        String username,
        LocalDateTime createdAt
) {
    public UserCreatedEvent(Long userId, String username){
        this(userId, username, LocalDateTime.now());
    }
}
