package com.epam.as.bookhotel.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action show page with form for the order
 */

public class ShowOrderFormAction implements Action {

    private static final String BOOK_ORDER_JSP = "order_form";
    private static final String USER = "user";
    private static final String LOGIN_FORM = "login";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        if (req.getSession(false).getAttribute(USER) == null) return LOGIN_FORM;
        return BOOK_ORDER_JSP;
    }
}
