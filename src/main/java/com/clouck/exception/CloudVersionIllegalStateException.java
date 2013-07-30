package com.clouck.exception;
/**
 * use this exception for human error unhandled exception
 * e.g. in switch, there is one case u didn't handle
 * @author steng
 *
 */
@SuppressWarnings("serial")
public class CloudVersionIllegalStateException extends RuntimeException {

    public CloudVersionIllegalStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public CloudVersionIllegalStateException(Throwable cause) {
        super(cause);
    }
    
    public CloudVersionIllegalStateException(String message) {
        super(message);
    }
}
