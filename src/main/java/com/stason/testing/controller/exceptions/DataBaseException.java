package com.stason.testing.controller.exceptions;

public class DataBaseException  extends ServiceException{
    public DataBaseException() {
        super("500");
    }

    public DataBaseException(String message) {
        super("500", message);
    }

    public DataBaseException(String message, Throwable cause) {
        super("500", message, cause);
    }
}
