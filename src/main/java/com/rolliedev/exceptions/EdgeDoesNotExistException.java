package com.rolliedev.exceptions;

public class EdgeDoesNotExistException extends RuntimeException {

    public EdgeDoesNotExistException() {
    }

    public EdgeDoesNotExistException(String message) {
        super(message);
    }

    public EdgeDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public EdgeDoesNotExistException(Throwable cause) {
        super(cause);
    }

    public EdgeDoesNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
