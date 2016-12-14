package com.epam.as.bookhotel.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceException extends Exception {

    private static final Logger logger = LoggerFactory.getLogger(ServiceException.class);
    private String message;

    ServiceException(String message) {
        super(message);
    }

    public ServiceException(Exception e) {
        super(e);
        this.message = e.getMessage();
    }


}
