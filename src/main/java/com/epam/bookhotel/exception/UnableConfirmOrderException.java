package com.epam.bookhotel.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Extends exception, throws if JdbcOrderDao couldn't update order status
 */

public class UnableConfirmOrderException extends ServiceException {

    private static final Logger logger = LoggerFactory.getLogger(UnableConfirmOrderException.class);
    private static final String CONFIRM_ORDER_ERROR_MSG = "confirm.order.error.message";
    private static final String LOG_ERROR_MSG = "Unable confirm order.";


    public UnableConfirmOrderException(Exception e) {

        super(CONFIRM_ORDER_ERROR_MSG, e);
        logger.error(LOG_ERROR_MSG, e);
    }

    public UnableConfirmOrderException() {

        super(CONFIRM_ORDER_ERROR_MSG);
        logger.error(LOG_ERROR_MSG);
    }
}
