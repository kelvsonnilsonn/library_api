package com.library.api.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record BorrowResponseDTO(
        Long id,
        String username,
        String bookTitle,
        LocalDateTime borrowDate,
        LocalDateTime dueDate,
        LocalDateTime returnDate,
        Boolean returned
) implements Serializable {
}
