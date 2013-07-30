package com.clouck.exception;

@SuppressWarnings("serial")
public class ClouckInvalidParrameterException extends RuntimeException {
    public ClouckInvalidParrameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClouckInvalidParrameterException(Throwable cause) {
        super(cause);
    }

    public ClouckInvalidParrameterException(String message) {
        super(message);
    }
}
