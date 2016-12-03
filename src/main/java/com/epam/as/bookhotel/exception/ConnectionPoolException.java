package com.epam.as.bookhotel.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionPoolException extends Exception {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionPoolException.class);

    public ConnectionPoolException(Exception e) {
        logger.error("An error with the database connection occurred", e);
    }
}
