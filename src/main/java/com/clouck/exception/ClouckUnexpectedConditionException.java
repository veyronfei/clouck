package com.clouck.exception;

@SuppressWarnings("serial")
public class ClouckUnexpectedConditionException extends RuntimeException {
    public ClouckUnexpectedConditionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClouckUnexpectedConditionException(Throwable cause) {
        super(cause);
    }
    
    public ClouckUnexpectedConditionException(String message) {
        super(message);
    }
}
