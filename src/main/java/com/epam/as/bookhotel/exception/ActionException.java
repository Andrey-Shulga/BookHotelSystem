package com.epam.as.bookhotel.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionException extends Exception {

    private static final Logger logger = LoggerFactory.getLogger(ActionException.class);

    public ActionException(ReflectiveOperationException e) {
        logger.error("Reflection operation exceptions with ActionFactory occurred", e);
    }

}
