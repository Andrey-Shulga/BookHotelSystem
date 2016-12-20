package com.epam.as.bookhotel.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action show page with message if login was successful.
 */

public class ShowLoginSuccessAction implements Action {

    private static final String LOGIN_SUCCESS_JSP = "login_success";
    private static final String USER = "user";
    private static final String LOGIN_FORM = "login";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        if (req.getSession(false).getAttribute(USER) == null) return LOGIN_FORM;
        return LOGIN_SUCCESS_JSP;
    }
}
