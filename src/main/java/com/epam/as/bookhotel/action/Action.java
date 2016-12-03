package com.epam.as.bookhotel.action;


import com.epam.as.bookhotel.exception.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
    String execute(HttpServletRequest req, HttpServletResponse res) throws PropertyManagerException, ValidatorException, ConnectionPoolException, ServiceException, JdbcDaoException;

}
