package com.epam.as.bookhotel.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceException extends Exception {

    private static final Logger logger = LoggerFactory.getLogger(ValidatorException.class);

    public ServiceException(Exception e) {
        logger.error("Service exception occurred", e);
    }
}
