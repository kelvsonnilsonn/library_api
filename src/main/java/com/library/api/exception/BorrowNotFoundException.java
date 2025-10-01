package com.library.api.exception;

public class BorrowNotFoundException extends RuntimeException {
    public BorrowNotFoundException() { super("O empréstimo não foi encontrado"); }
    public BorrowNotFoundException(String message) {
        super(message);
    }
}
