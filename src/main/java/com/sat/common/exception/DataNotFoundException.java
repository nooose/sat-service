package com.sat.common.exception;

public class DataNotFoundException extends NoStackTraceException {
    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
