package com.epam.bookhotel.exception;

/**
 * Throws during user registration if user with that login already exists
 */

public class UserExistException extends ServiceException {


    private static final String USER_EXIST_ERROR_MSG = "register.error.message.exist";


    public UserExistException(Exception e) {

        super(USER_EXIST_ERROR_MSG, e);

    }
}
