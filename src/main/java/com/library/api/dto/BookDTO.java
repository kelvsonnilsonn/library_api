package com.library.api.dto;

import com.library.api.enums.BookType;

public record BookDTO(
        String title,
        String isbn,
        Long authorId,
        BookType type
) {
}
