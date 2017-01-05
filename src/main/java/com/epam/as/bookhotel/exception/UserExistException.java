package com.epam.as.bookhotel.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Throws during user registration if user with that login already exists
 */

public class UserExistException extends ServiceException {

    private static final Logger logger = LoggerFactory.getLogger(UserExistException.class);
    private static final String USER_EXIST_ERROR_MSG = "register.error.message.exist";


    public UserExistException(Exception e) {

        super(USER_EXIST_ERROR_MSG, e);
        logger.error("User is already exist.", e);
    }
}
