package com.epam.bookhotel.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception for wrapping any exceptions in Service layer
 */

public class ServiceException extends Exception {

    private static final Logger logger = LoggerFactory.getLogger(ServiceException.class);
    private static final String LOG_ERROR_MSG = "ServiceException occurred";
    private String message;


    public ServiceException(String message, Exception e) {

        super(e);
        this.message = message;
        logger.error(LOG_ERROR_MSG, e);
    }

    public ServiceException(Exception e) {

        this.message = e.getMessage();
        logger.error(LOG_ERROR_MSG, e);
    }

    public ServiceException(String message) {

        this.message = message;
        logger.error(message);
    }

    public ServiceException() {

        logger.error(LOG_ERROR_MSG);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
