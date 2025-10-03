package com.library.api.exception;

public class FailedLoginAttemptException extends RuntimeException {
    public FailedLoginAttemptException() { super("Tentativa de login falhou."); }
    public FailedLoginAttemptException(String message) {
        super(message);
    }
}
