package com.epam.as.bookhotel.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserNotFoundException extends JdbcDaoException {

    private static final Logger logger = LoggerFactory.getLogger(UserNotFoundException.class);

    public UserNotFoundException(String message) {
        super(message);
        logger.error("User not found.");
    }
}
