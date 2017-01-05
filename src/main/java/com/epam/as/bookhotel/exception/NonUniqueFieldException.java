package com.epam.as.bookhotel.exception;

/**
 * Exception throws if attempt to insert already exists value in column's field with unique constraint.
 */

public class NonUniqueFieldException extends JdbcDaoException {

    public NonUniqueFieldException(Exception e) {

        super(e.getMessage(), e);

    }
}
