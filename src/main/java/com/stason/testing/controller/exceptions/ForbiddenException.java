package com.stason.testing.controller.exceptions;

public class ForbiddenException extends ServiceException{

    public ForbiddenException() {
        super("403");
    }

    public ForbiddenException(String message) {
        super("403", message);
    }

    public ForbiddenException(String message, Throwable cause) {
        super("403", message, cause);
    }
}
