package com.epam.bookhotel.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception for wrapping any exceptions in Action layer
 */

public class ActionException extends Exception {

    private static final Logger logger = LoggerFactory.getLogger(ActionException.class);
    private static final String LOG_ERROR_MSG = "ActionException occurred";

    public ActionException(Exception e) {

        super(e);
        logger.error(LOG_ERROR_MSG, e);
    }
}
