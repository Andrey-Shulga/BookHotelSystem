package com.epam.as.bookhotel.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class UserExistingException extends JdbcDaoException {

    private static final Logger logger = LoggerFactory.getLogger(UserExistingException.class);
    private static final String REG_ERROR_MSG_EXIST = "register.error.message.exist";


    public UserExistingException(SQLException e) {
        super(REG_ERROR_MSG_EXIST, e);
        logger.error("User is exist.", e);
    }
}
