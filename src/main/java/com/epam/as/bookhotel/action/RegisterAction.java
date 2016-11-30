package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.validator.FormValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RegisterAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws PropertyManagerException {

        FormValidator registerFormValidator = FormValidator.getFormValidator("register");
        return null;
    }
}
