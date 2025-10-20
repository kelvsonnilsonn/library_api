package com.library.api.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record UserResponseDTO(
        Long id,
        String username,
        LocalDateTime createdAt
) implements Serializable {
}
