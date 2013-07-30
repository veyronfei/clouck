package com.clouck.exception;

@SuppressWarnings("serial")
public class ClouckError extends Error {

    public ClouckError(Throwable cause) {
        super(cause);
    }
}
