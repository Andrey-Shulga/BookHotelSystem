package com.epam.as.bookhotel.action;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ActionException extends Throwable {

    private static final Logger logger = LoggerFactory.getLogger(ActionException.class);

    ActionException(ReflectiveOperationException e) {
        logger.error("Reflection operation exceptions with ActionFactory occurred", e);
    }
}
