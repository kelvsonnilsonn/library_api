package com.library.api.dto.borrow;

import java.time.LocalDateTime;

public record BorrowRequestDTO(Long bookId, LocalDateTime dueDate) {
}
