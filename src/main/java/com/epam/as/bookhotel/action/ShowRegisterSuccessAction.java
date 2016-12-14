package com.epam.as.bookhotel.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowRegisterSuccessAction implements Action {

    private static final String REGISTER_SUCCESS_JSP = "register_success";


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {

        return REGISTER_SUCCESS_JSP;
    }
}
