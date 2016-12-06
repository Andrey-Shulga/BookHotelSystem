package com.epam.as.bookhotel.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class UserNotFoundException extends JdbcDaoException {

    private static final Logger logger = LoggerFactory.getLogger(UserExistingException.class);
    private static final String ERROR_MSG = "login.error.notfound";

    public UserNotFoundException(SQLException e) {
        super(ERROR_MSG, e);
        logger.error("User not found.", e);
    }
}
