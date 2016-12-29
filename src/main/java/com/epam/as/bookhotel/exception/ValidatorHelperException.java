package com.epam.as.bookhotel.exception;

public class ValidatorHelperException extends Exception {

    public ValidatorHelperException(ValidatorException e) {
        super(e);
    }
}
