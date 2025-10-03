package com.library.api.exception;

public class InvalidUpdateRequestException extends RuntimeException {
    public InvalidUpdateRequestException() { super("Dados de alteração enviados estão inválidos"); }
    public InvalidUpdateRequestException(String message) {
        super(message);
    }
}
