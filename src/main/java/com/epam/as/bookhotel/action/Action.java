package com.epam.as.bookhotel.action;


import com.epam.as.bookhotel.exception.ConnectionPoolException;
import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.exception.ValidatorException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Action {
    String execute(HttpServletRequest req, HttpServletResponse res) throws PropertyManagerException, ValidatorException, ConnectionPoolException, JdbcDaoException, ServletException, IOException;

}
