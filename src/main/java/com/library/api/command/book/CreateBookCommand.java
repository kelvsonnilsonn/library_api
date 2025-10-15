package com.library.api.command.book;

import com.library.api.enums.BookType;
import com.library.api.model.ISBN;

public record CreateBookCommand(
        String title,
        ISBN isbn,
        BookType type
) {
}
