package com.epam.as.bookhotel.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class UserExistingException extends JdbcDaoException {

    private static final Logger logger = LoggerFactory.getLogger(UserExistingException.class);
    private static final String ERROR_MSG = "register.error.message.exist";


    public UserExistingException(SQLException e) {
        super(ERROR_MSG, e);
        logger.error("User is exist.", e);
    }
}
