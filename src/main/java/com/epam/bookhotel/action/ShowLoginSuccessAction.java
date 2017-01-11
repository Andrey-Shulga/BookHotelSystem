package com.epam.bookhotel.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action show page with message if login was successful.
 */

public class ShowLoginSuccessAction implements Action {

    private static final String LOGIN_SUCCESS_JSP = "login_success";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {

        return LOGIN_SUCCESS_JSP;
    }
}
