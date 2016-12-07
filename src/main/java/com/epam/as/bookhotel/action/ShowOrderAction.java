package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.ConnectionPoolException;
import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.exception.ValidatorException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowOrderAction implements Action {

    private static final String BOOK_ORDER_JSP = "order_form";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws PropertyManagerException, ValidatorException, ConnectionPoolException, JdbcDaoException {
        return BOOK_ORDER_JSP;
    }
}
