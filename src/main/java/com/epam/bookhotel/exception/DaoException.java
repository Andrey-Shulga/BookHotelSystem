package com.epam.bookhotel.exception;

/**
 * General exception for wrapping any exceptions in Dao layer
 */

public class DaoException extends Exception {

    private static final String INTERNAL_ERROR_FAILURE_MSG = "internal.error.failure.msg";

    DaoException(String message, Exception e) {

        super(message, e);

    }

    public DaoException(Exception e) {

        super(INTERNAL_ERROR_FAILURE_MSG, e);

    }

    DaoException(String logErrorMsg) {

        super(logErrorMsg);
    }
}
