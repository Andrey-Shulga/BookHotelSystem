package com.epam.as.bookhotel.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class UserExistingException extends Throwable {

    private static final Logger logger = LoggerFactory.getLogger(UserExistingException.class);
    private static final String REG_ERROR_MSG_EXIST = "register.error.message.exist";
    private String message;

    public UserExistingException(SQLException e) {
        this.message = REG_ERROR_MSG_EXIST;
        logger.error("User is exist.", e);
    }

    @Override
    public String getMessage() {
        return message;
    }


}
