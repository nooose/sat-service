package com.sat.common.exception;

public abstract class NoStackTraceException extends RuntimeException {
    public NoStackTraceException(String message) {
        super(message);
    }

    public NoStackTraceException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
