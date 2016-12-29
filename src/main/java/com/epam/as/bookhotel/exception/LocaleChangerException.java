package com.epam.as.bookhotel.exception;

/**
 * Exception for wrapping any exceptions in LocaleChanger
 */

public class LocaleChangerException extends Exception {

    public LocaleChangerException(ServiceException e) {
        super(e);
    }
}
