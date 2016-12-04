package com.epam.as.bookhotel.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class UserExistingException extends Throwable {

    private static final Logger logger = LoggerFactory.getLogger(UserExistingException.class);
    private String message;


    public UserExistingException(SQLException e) {
        this.message = "User is Exist";
        logger.error("Error. User is exist.", e);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
