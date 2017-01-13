package com.epam.bookhotel.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception for wrapping any exceptions in Service layer
 */

public class ServiceException extends Exception {

    private static final Logger logger = LoggerFactory.getLogger(ServiceException.class);
    private String message;


    public ServiceException(String message, Exception e) {

        super(e);
        this.message = message;

    }

    public ServiceException(Exception e) {

        this.message = e.getMessage();

    }

    public ServiceException(String message) {

        this.message = message;

    }

    public ServiceException() {


    }

    @Override
    public String getMessage() {
        return message;
    }
}
