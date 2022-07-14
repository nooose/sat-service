package com.noose.storemanager.domain.ex;

public class IllegalPhoneNumber extends RuntimeException {
    public IllegalPhoneNumber() {
        this("중복된 휴대폰 번호입니다.");
    }

    public IllegalPhoneNumber(String message) {
        super(message);
    }

    public IllegalPhoneNumber(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalPhoneNumber(Throwable cause) {
        super(cause);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
