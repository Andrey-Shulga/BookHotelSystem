package com.epam.as.bookhotel.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcDaoException extends DaoException {

    private static final Logger logger = LoggerFactory.getLogger(JdbcDaoException.class);
    private static final String DB_CONNECT_ERROR_MSG = "database.connection.failure.msg";

    public JdbcDaoException(Exception e) {
        super(DB_CONNECT_ERROR_MSG, e);
        logger.error("JDBC error occurred.", e);

    }

    JdbcDaoException(String message, Exception e) {
        super(message, e);
    }
}
