package com.library.api.dto.books;

import com.library.api.enums.BookType;
import com.library.api.model.ISBN;

public record BookRequestDTO(
        String title,
        ISBN isbn,
        BookType type
) {
}
