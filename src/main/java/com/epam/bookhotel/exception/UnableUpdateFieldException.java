package com.epam.bookhotel.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * General exception, throws if field update ends without result.
 */

public class UnableUpdateFieldException extends JdbcDaoException {

    private static final Logger logger = LoggerFactory.getLogger(UnableUpdateFieldException.class);
    private static final String LOG_ERROR_MSG = "Unable update field";

    public UnableUpdateFieldException() {

        super(LOG_ERROR_MSG);

    }
}
