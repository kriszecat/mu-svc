package com.hq.activity.exception;

/**
 * Created by cchabot on 23/03/2016.
 */
public class ErrorInformation {
    private String message;
    private String requestURI;

    public ErrorInformation(String message, String requestURI) {
        this.message = message;
        this.requestURI = requestURI;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }
}
