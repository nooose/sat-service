package com.sat.common.exception;

public class NotSupportedException extends NoStackTraceException {
    public NotSupportedException(String message) {
        super(message);
    }

    public NotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }
}
