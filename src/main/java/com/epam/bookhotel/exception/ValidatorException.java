package com.epam.bookhotel.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception for wrapping any exceptions with validation
 */

public class ValidatorException extends Exception {

    private static final Logger logger = LoggerFactory.getLogger(ValidatorException.class);

    public ValidatorException(Exception e) {

        super(e);
        logger.error("ValidatorException occurred", e);
    }
}
