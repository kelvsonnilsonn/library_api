package com.library.api.exception;

import com.library.api.util.AppConstants;

public class InvalidCreatePasswordException extends RuntimeException {
    public InvalidCreatePasswordException() { super(AppConstants.CANT_CREATE_PASSWORD_MESSAGE); }
    public InvalidCreatePasswordException(String message) {
        super(message);
    }
}
