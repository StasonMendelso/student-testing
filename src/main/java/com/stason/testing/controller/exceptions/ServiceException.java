package com.stason.testing.controller.exceptions;

public class ServiceException extends RuntimeException{
    private final String HttpStatusCode;

    public ServiceException(String httpStatusCode) {
        this.HttpStatusCode = httpStatusCode;
    }

    public ServiceException(String HttpStatusCode, String message) {
        super(message);
        this.HttpStatusCode = HttpStatusCode;
    }

    public ServiceException(String HttpStatusCode, String message, Throwable cause) {
        super(message, cause);
        this.HttpStatusCode = HttpStatusCode;
    }

    public String getHttpStatusCode() {
        return HttpStatusCode;
    }
}
