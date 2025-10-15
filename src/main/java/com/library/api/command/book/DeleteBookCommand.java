package com.library.api.command.book;

import jakarta.validation.constraints.NotNull;

public record DeleteBookCommand(
        @NotNull Long bookId,
        String reason
) {
}
