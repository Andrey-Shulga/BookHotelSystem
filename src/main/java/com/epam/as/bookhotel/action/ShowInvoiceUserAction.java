package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.ActionException;
import com.epam.as.bookhotel.model.Order;
import com.epam.as.bookhotel.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowInvoiceUserAction implements Action {

    private static final String USER_INVOICE_LIST = "user_invoice_list";
    private static final String USER = "user";
    private static final String LOGIN_FORM = "login";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        if (req.getSession(false).getAttribute(USER) == null) return LOGIN_FORM;
        User user = (User) req.getSession(false).getAttribute(USER);
        Order order = new Order();
        order.setUser(user);

        return USER_INVOICE_LIST;
    }
}
