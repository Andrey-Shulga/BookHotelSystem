package com.epam.as.bookhotel.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action show page with register form.
 */

public class ShowRegisterFormAction implements Action {

    private static final String REGISTER_JSP = "register";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        return REGISTER_JSP;
    }
}
