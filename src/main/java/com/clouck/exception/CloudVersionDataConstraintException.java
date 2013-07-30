package com.clouck.exception;
/**
 * use this exception for validation check for data consistency
 * @author steng
 *
 */
@SuppressWarnings("serial")
public class CloudVersionDataConstraintException extends RuntimeException {

    public CloudVersionDataConstraintException(String message, Throwable cause) {
        super(message, cause);
    }

    public CloudVersionDataConstraintException(Throwable cause) {
        super(cause);
    }
    
    public CloudVersionDataConstraintException(String message) {
        super(message);
    }
}
