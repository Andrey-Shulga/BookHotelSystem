package com.epam.bookhotel.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

/**
 * Exception throws if attempt to insert already exists value in column's field with unique constraint.
 */

public class NonUniqueFieldException extends JdbcDaoException {

    private static final Logger logger = LoggerFactory.getLogger(NonUniqueFieldException.class);
    private static final String LOG_ERROR_MSG = "NonUniqueFieldException occurred";

    public NonUniqueFieldException(SQLException e) {

        super(LOG_ERROR_MSG, e);
        logger.error(LOG_ERROR_MSG, e);

    }
}
