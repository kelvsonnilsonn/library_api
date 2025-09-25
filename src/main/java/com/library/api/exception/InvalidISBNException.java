package com.library.api.exception;

import com.library.api.util.AppConstants;

public class InvalidISBNException extends RuntimeException {
    public InvalidISBNException() { super(AppConstants.INVALID_ISBN_MESSAGE); }
    public InvalidISBNException(String message) {
        super(message);
    }
}
