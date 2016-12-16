package com.epam.as.bookhotel.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnableConfirmOrderException extends ServiceException {

    private static final Logger logger = LoggerFactory.getLogger(UnableConfirmOrderException.class);
    private static final String CONFIRM_ORDER_ERROR_MSG = "confirm.order.error.message";

    public UnableConfirmOrderException(Exception e) {
        super(CONFIRM_ORDER_ERROR_MSG, e);
        logger.error("Unable update filed for order confirmation.", e);
    }
}