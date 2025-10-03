package com.library.api.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() { super("O usuário já existe."); }
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
