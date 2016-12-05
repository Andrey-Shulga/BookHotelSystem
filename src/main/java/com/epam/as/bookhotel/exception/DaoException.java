package com.epam.as.bookhotel.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaoException extends Exception {

    private static final Logger logger = LoggerFactory.getLogger(DaoException.class);

    DaoException() {
    }

    public DaoException(Exception e) {
        logger.error("DaoException occurred.", e);
    }
}
