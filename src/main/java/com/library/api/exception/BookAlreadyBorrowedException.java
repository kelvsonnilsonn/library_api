package com.library.api.exception;

public class BookAlreadyBorrowedException extends RuntimeException {
    public BookAlreadyBorrowedException() { super("O livro já foi emprestado"); }
    public BookAlreadyBorrowedException(String message) {
        super(message);
    }
}
