package com.epam.bookhotel.exception;

/**
 * General exception, throws if field update ends without result.
 */

public class UnableUpdateFieldException extends JdbcDaoException {

    private static final String LOG_ERROR_MSG = "Unable update field";

    public UnableUpdateFieldException() {

        super(LOG_ERROR_MSG);

    }
}
