package com.epam.bookhotel.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception for wrapping any exceptions in JdbcDao layer
 */

public class JdbcDaoException extends DaoException {

    private static final Logger logger = LoggerFactory.getLogger(JdbcDaoException.class);
    private static final String DB_CONNECT_ERROR_MSG = "database.connection.failure.msg";
    private static final String LOG_ERROR_MSG = "JdbcDaoException occurred";

    public JdbcDaoException(Exception e) {

        super(DB_CONNECT_ERROR_MSG, e);


    }

    JdbcDaoException(String message, Exception e) {

        super(message, e);

    }


    JdbcDaoException(String logErrorMsg) {

        super(logErrorMsg);
    }
}
