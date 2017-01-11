package com.epam.bookhotel.exception;


/**
 * Exception for wrapping any exceptions in Action layer
 */

public class ActionException extends Exception {

    public ActionException(Exception e) {
        super(e);
    }
}
