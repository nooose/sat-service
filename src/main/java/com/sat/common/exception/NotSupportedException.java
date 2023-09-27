package com.sat.common.exception;

import javax.naming.AuthenticationException;

public class NotSupportedException extends AuthenticationException {
    public NotSupportedException(String message) {
        super(message);
    }
}
