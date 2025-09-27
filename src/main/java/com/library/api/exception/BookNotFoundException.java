package com.library.api.exception;

import com.library.api.util.AppConstants;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException() { super(AppConstants.BOOK_NOT_FOUND_MESSAGE); }
    public BookNotFoundException(String message) {
        super(message);
    }
}
