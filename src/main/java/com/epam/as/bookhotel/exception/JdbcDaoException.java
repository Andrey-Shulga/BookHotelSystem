package com.epam.as.bookhotel.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcDaoException extends Exception {

    private static final Logger logger = LoggerFactory.getLogger(JdbcDaoException.class);
    private String message;

    public JdbcDaoException(String message, Exception e) {
        this.message = message;
        logger.debug("DAO MESSAGE {}", this.message);
    }

    public JdbcDaoException(Exception e) {
        this.message = e.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
