package com.epam.as.bookhotel.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyManagerException extends Throwable {

    private static final Logger logger = LoggerFactory.getLogger(PropertyManagerException.class);

    public PropertyManagerException(Exception e) {
        logger.error("Can't open file {} for reading properties.", e);
    }
}
