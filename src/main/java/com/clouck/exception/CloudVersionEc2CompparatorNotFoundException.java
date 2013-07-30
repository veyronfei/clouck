package com.clouck.exception;
/**
 * @author steng
 *
 */
@SuppressWarnings("serial")
public class CloudVersionEc2CompparatorNotFoundException extends RuntimeException {

    public CloudVersionEc2CompparatorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CloudVersionEc2CompparatorNotFoundException(Throwable cause) {
        super(cause);
    }
    
    public CloudVersionEc2CompparatorNotFoundException(String message) {
        super(message);
    }
}
