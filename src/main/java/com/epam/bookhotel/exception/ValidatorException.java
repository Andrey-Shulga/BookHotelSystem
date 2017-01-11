package com.epam.bookhotel.exception;

/**
 * Exception for wrapping any exceptions with validation
 */

public class ValidatorException extends Exception {

    public ValidatorException(Exception e) {
        super(e);
    }
}
