package com.epam.as.bookhotel.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidatorException extends Exception {

    private static final Logger logger = LoggerFactory.getLogger(ValidatorException.class);

    public ValidatorException(Exception e) {
        logger.error("Reflection operation exceptions with creating Validator occurred", e);
    }
}
