package com.library.api.exception;

public class NameAlreadyInUseUpdateException extends RuntimeException {
    public NameAlreadyInUseUpdateException() { super("A alteração não é possível, pois o nome já está em uso por você."); }
    public NameAlreadyInUseUpdateException(String message) {
        super(message);
    }
}
