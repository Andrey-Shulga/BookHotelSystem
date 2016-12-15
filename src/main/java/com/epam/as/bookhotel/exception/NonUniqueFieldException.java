package com.epam.as.bookhotel.exception;

public class NonUniqueFieldException extends JdbcDaoException {


    public NonUniqueFieldException(Exception e) {
        super(e.getMessage(), e);

    }
}
