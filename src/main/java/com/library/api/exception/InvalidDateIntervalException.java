package com.library.api.exception;

public class InvalidDateIntervalException extends RuntimeException {
    public InvalidDateIntervalException() { super("O intervalo de data é invalido"); }
    public InvalidDateIntervalException(String message) {
        super(message);
    }
}
