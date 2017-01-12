package com.epam.bookhotel.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception for wrapping any exceptions in LocaleChanger
 */

public class LocaleChangerException extends Exception {

    private static final Logger logger = LoggerFactory.getLogger(LocaleChangerException.class);
    private static final String LOG_ERROR_MSG = "LocaleChangerException occurred";

    public LocaleChangerException(ServiceException e) {

        super(e);
        logger.error(LOG_ERROR_MSG, e);

    }
}
