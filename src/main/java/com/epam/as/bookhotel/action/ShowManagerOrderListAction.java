package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.ConnectionPoolException;
import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.exception.ValidatorException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowManagerOrderListAction implements Action {

    private static final String ORDER_LIST_JSP = "show_orders";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws PropertyManagerException, ValidatorException, ConnectionPoolException, JdbcDaoException {
        return ORDER_LIST_JSP;
    }
}
