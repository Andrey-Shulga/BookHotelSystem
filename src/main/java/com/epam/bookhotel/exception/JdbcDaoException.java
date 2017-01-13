package com.epam.bookhotel.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception for wrapping any exceptions in JdbcDao layer
 */

public class JdbcDaoException extends DaoException {

    private static final Logger logger = LoggerFactory.getLogger(JdbcDaoException.class);

    public JdbcDaoException(Exception e) {

        super(e);
        logger.error("", e);

    }

    JdbcDaoException(String message, Exception e) {

        super(message, e);

    }


    JdbcDaoException(String logErrorMsg) {

        super(logErrorMsg);
    }
}
