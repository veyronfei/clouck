package com.clouck.exception;

@SuppressWarnings("serial")
public class CloudVersionException extends RuntimeException {

    public CloudVersionException(String message, Throwable cause) {
        super(message, cause);
    }

    public CloudVersionException(Throwable cause) {
        super(cause);
    }
    
    public CloudVersionException(String message) {
        super(message);
    }
}
