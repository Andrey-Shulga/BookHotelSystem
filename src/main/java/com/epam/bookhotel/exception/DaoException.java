package com.epam.bookhotel.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * General exception for wrapping any exceptions in Dao layer
 */

public class DaoException extends Exception {

    private static final Logger logger = LoggerFactory.getLogger(DaoException.class);
    private static final String DB_CONNECT_ERROR_MSG = "database.connection.failure.msg";
    private static final String LOG_ERROR_MSG = "DaoException occurred";

    DaoException(String message, Exception e) {

        super(message, e);
        logger.error(LOG_ERROR_MSG, e);
    }


    public DaoException(Exception e) {

        super(DB_CONNECT_ERROR_MSG, e);
        logger.error(LOG_ERROR_MSG, e);
    }


    DaoException() {

        super();
    }
}
