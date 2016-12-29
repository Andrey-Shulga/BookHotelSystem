package com.epam.as.bookhotel.exception;

/**
 * General exception for wrapping any exceptions in Dao layer
 */

public class DaoException extends Exception {

    private static final String DB_CONNECT_ERROR_MSG = "database.connection.failure.msg";

    DaoException(String message, Exception e) {
        super(message, e);
    }


    public DaoException(Exception e) {
        super(DB_CONNECT_ERROR_MSG, e);
    }


    DaoException() {
    }
}
