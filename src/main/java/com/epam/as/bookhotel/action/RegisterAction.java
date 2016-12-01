package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.exception.ValidatorException;
import com.epam.as.bookhotel.validator.FormValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RegisterAction implements Action {

    private static final String formName = "register";


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws PropertyManagerException, ValidatorException {

        FormValidator registerFormValidator = new FormValidator();
        String message = registerFormValidator.validate(formName, req);
        return null;
    }
}
