package com.sat.common.exception;

public class UnAuthorizedException extends NoStackTraceException {
    public UnAuthorizedException(String message) {
        super(message);
    }

    public UnAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
