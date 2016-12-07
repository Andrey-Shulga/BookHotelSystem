package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.ConnectionPoolException;
import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.exception.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowUserOrderAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(ShowUserOrderAction.class);
    private static final String USER_ORDER_LIST = "user_order_list";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws PropertyManagerException, ValidatorException, ConnectionPoolException, JdbcDaoException {
        return USER_ORDER_LIST;
    }
}
