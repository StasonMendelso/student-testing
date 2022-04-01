package com.stason.testing.controller.exceptions;

public class EmailException extends ServiceException{
    public EmailException() {
        super("500");
    }

    public EmailException(String message) {
        super("500", message);
    }

    public EmailException(String message, Throwable cause) {
        super("500", message, cause);
    }
}
