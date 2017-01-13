package com.epam.bookhotel.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Exception for wrapping any exceptions in Property Manager
 */

public class PropertyManagerException extends Exception {

    private static final Logger logger = LoggerFactory.getLogger(PropertyManagerException.class);

    public PropertyManagerException(IOException e) {

        super(e);
        logger.error("PropertyManagerException occurred", e);
    }


    public PropertyManagerException(String message) {

        super(message);
        logger.error(message);
    }
}