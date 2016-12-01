package com.epam.as.bookhotel.action;


import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.exception.ValidatorException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowRegisterFormAction implements Action {

    private static final String REGISTER_JSP = "register";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws PropertyManagerException, ValidatorException {
        return REGISTER_JSP;
    }
}
