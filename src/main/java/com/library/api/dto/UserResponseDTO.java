package com.library.api.dto;

import java.time.LocalDateTime;

public record UserResponseDTO(
        Long id,
        String username,
        LocalDateTime createdAt
) {
}
