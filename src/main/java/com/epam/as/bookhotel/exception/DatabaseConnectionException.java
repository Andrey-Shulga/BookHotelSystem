package com.epam.as.bookhotel.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class DatabaseConnectionException extends Throwable {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnectionException.class);
    private static final String DB_CONN_FAILURE_MSG = "database.connection.failure.msg";
    private String message;

    public DatabaseConnectionException(SQLException e) {
        this.message = DB_CONN_FAILURE_MSG;
        logger.error("Database connection failure.", e);

    }

    @Override
    public String getMessage() {
        return message;
    }
}
