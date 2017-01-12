package com.epam.bookhotel.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception for wrapping any exceptions in Connection pool.
 */

public class ConnectionPoolException extends Exception {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionPoolException.class);
    private static final String LOG_ERROR_MSG = "An error with the database connection occurred";

    public ConnectionPoolException(Exception e) {

        super(e);
        logger.error(LOG_ERROR_MSG, e);
    }

    public ConnectionPoolException(String message, Exception e) {

        super(message, e);
        logger.error(LOG_ERROR_MSG, e);
    }

    public ConnectionPoolException(String message) {

        super(message);
        logger.error(message);

    }
}
