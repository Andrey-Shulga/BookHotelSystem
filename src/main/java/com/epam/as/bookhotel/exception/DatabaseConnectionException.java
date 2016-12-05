package com.epam.as.bookhotel.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class DatabaseConnectionException extends JdbcDaoException {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnectionException.class);
    private static final String DB_CONN_FAILURE_MSG = "database.connection.failure.msg";


    public DatabaseConnectionException(SQLException e) {
        super(DB_CONN_FAILURE_MSG, e);
        logger.error("Database connection failure.", e);

    }
}
