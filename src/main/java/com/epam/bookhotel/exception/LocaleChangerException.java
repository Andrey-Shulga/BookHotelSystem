package com.epam.bookhotel.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception for wrapping any exceptions in LocaleChanger
 */

public class LocaleChangerException extends Exception {

    private static final Logger logger = LoggerFactory.getLogger(LocaleChangerException.class);

    public LocaleChangerException(ServiceException e) {

        super(e);
        logger.error("LocaleChangerException occurred", e);

    }
}
