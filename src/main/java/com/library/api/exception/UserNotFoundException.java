package com.library.api.exception;

import com.library.api.util.AppConstants;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() { super(AppConstants.USER_NOT_FOUND_MESSAGE); }
    public UserNotFoundException(String message) {
        super(message);
    }
}
