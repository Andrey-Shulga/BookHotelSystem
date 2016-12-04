package com.epam.as.bookhotel.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class JdbcDaoException extends Throwable {

    private static final Logger logger = LoggerFactory.getLogger(JdbcDaoException.class);

    public JdbcDaoException(SQLException e) {

        logger.error("JdbcDao exception occurred:", e);
    }
}
