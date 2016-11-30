package com.epam.as.bookhotel.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyManagerException extends Throwable {

    private static final Logger logger = LoggerFactory.getLogger(PropertyManagerException.class);

    public PropertyManagerException(String message) {
        super(message);
    }

    public PropertyManagerException(Exception e, String propertyFileName) {
        logger.error("Unable to open file {} for reading properties.", e, propertyFileName);
    }
}
