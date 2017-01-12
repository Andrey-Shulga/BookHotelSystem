package com.epam.bookhotel.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception for wrapping any exceptions in Action factory
 */

public class ActionFactoryException extends Exception {

    private static final Logger logger = LoggerFactory.getLogger(ActionFactoryException.class);
    private static final String LOG_ERROR_MSG = "ActionFactoryException occurred";

    public ActionFactoryException(Exception e) {

        super(e);
        logger.error(LOG_ERROR_MSG, e);
    }
}
