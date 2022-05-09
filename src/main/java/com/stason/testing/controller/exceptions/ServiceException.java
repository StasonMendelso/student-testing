package com.stason.testing.controller.exceptions;
/**
 * Customized runtime exceptions which appeared when web-site is working
 * @author Stanislav Hlova
 * @version 1.0
 * @see RuntimeException
 */
public class ServiceException extends RuntimeException{
    private final String httpStatusCode;

    public ServiceException(String httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public ServiceException(String httpStatusCode, String message) {
        super(message);
        this.httpStatusCode = httpStatusCode;
    }

    public ServiceException(String httpStatusCode, String message, Throwable cause) {
        super(message, cause);
        this.httpStatusCode = httpStatusCode;
    }

    public String getHttpStatusCode() {
        return httpStatusCode;
    }
}
