package com.library.api.dto;

import com.library.api.enums.BookType;
import com.library.api.model.ISBN;

public record BookRequestDTO(
        String title,
        ISBN isbn,
        Long authorId,
        BookType type
) {
}
