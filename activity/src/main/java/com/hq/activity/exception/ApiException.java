package com.hq.activity.exception;

public class ApiException extends Exception {
    private static final long serialVersionUID = -5183458597970645187L;

    public ApiException() {
        super();
    }

    public ApiException(Throwable cause) {
        super(cause);
    }
}