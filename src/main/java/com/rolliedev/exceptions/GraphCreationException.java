package com.rolliedev.exceptions;

public class GraphCreationException extends RuntimeException {

    public GraphCreationException() {
    }

    public GraphCreationException(String message) {
        super(message);
    }

    public GraphCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public GraphCreationException(Throwable cause) {
        super(cause);
    }

    public GraphCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
