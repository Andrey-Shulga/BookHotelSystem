package com.epam.bookhotel.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Exception for wrapping any exceptions in Property Manager
 */

public class PropertyManagerException extends Exception {

    private static final Logger logger = LoggerFactory.getLogger(PropertyManagerException.class);
    private static final String LOG_ERROR_MSG = "PropertyManagerException occurred";

    public PropertyManagerException(IOException e) {

        super(e);
        logger.error(LOG_ERROR_MSG, e);
    }


    public PropertyManagerException(String message) {

        super(message);
        logger.error(message);
    }
}
