package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.ConnectionPoolException;
import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.exception.ValidatorException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowLoginSuccessAction implements Action {

    private static final String LOGIN_SUCCESS_JSP = "login_success";
    private static final String USER = "user";
    private static final String LOGIN_FORM = "login";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws PropertyManagerException, ValidatorException, ConnectionPoolException, JdbcDaoException {
        if (req.getSession(false).getAttribute(USER) == null) return LOGIN_FORM;
        return LOGIN_SUCCESS_JSP;
    }
}
