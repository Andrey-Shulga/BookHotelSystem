package com.epam.as.bookhotel.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserNotFoundException extends ServiceException {

    private static final Logger logger = LoggerFactory.getLogger(UserNotFoundException.class);
    private static final String USER_NOT_FOUND_ERROR_MSG = "login.error.notfound";


    public UserNotFoundException() {
        super(USER_NOT_FOUND_ERROR_MSG);
        logger.error("User not found.");
    }
}
