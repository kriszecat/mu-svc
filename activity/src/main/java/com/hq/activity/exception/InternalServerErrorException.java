package com.hq.activity.exception;

public class InternalServerErrorException extends ApiException {
    private static final long serialVersionUID = 8265708003274839968L;

    public InternalServerErrorException(Throwable cause) {
        super(cause);
    }
}
