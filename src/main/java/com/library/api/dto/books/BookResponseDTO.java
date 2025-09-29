package com.library.api.dto.books;

import com.library.api.enums.BookType;
import com.library.api.model.ISBN;

import java.time.LocalDateTime;

public record BookResponseDTO (
        Long id,
        String title,
        ISBN isbn,
        Long authorId,
        BookType type,
        LocalDateTime createdAt
) {
}
