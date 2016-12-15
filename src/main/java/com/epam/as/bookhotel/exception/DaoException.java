package com.epam.as.bookhotel.exception;

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
