package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowRegisterSuccessAction implements Action {

    private static final String LOPGIN_JSP = "login";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws PropertyManagerException, ValidatorException, ConnectionPoolException, ServiceException, JdbcDaoException {
        return "register_success";
    }
}
