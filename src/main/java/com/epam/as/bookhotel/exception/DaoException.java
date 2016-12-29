package com.epam.as.bookhotel.exception;

/**
 * General exception for wrapping any exceptions in Dao layer
 */

public class DaoException extends Exception {


    DaoException(String message, Exception e) {
        super(message, e);
    }


    public DaoException(Exception e) {
        super(e);
    }


    DaoException() {
    }
}
