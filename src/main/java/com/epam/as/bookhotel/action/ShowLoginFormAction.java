package com.epam.as.bookhotel.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ShowLoginFormAction implements Action {

    private static final String LOGIN_JSP = "login";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        return LOGIN_JSP;
    }
}
