package com.epam.as.bookhotel.exception;

public class JdbcDaoException extends DaoException {

    private static final String DB_CONNECT_ERROR_MSG = "database.connection.failure.msg";

    public JdbcDaoException(Exception e) {
        super(DB_CONNECT_ERROR_MSG, e);
    }

    JdbcDaoException(String message, Exception e) {
        super(message, e);
    }


}
