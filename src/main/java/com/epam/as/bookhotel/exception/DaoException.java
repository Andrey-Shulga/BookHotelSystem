package com.epam.as.bookhotel.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class DaoException extends Throwable {

    private static final Logger logger = LoggerFactory.getLogger(DaoException.class);

    public DaoException(SQLException e) {
        logger.error("Dao exception occurred", e);
    }
}
