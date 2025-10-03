package com.library.api.exception;

public class PasswordAlreadyInUseUpdateException extends RuntimeException {
    public PasswordAlreadyInUseUpdateException() { super("A alteração não é possível, pois a senha já está em uso por você.");}
    public PasswordAlreadyInUseUpdateException(String message) {
        super(message);
    }
}
