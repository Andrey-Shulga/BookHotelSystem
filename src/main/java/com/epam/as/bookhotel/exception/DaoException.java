package com.epam.as.bookhotel.exception;

public class DaoException extends Exception {
    private String message;

    DaoException(String message, Exception e) {
        super(message, e);
        this.message = e.getMessage();

    }


    public DaoException(Exception e) {
        this.message = e.getMessage();

    }

    @Override
    public String getMessage() {
        return message;
    }
}
