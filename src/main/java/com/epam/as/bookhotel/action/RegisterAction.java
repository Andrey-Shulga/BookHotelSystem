package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.validator.FormValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public class RegisterAction implements Action {

    private static final String formName = "register";


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws PropertyManagerException {

        FormValidator registerFormValidator = new FormValidator();
        List<String> message = registerFormValidator.validate(formName, req);
        return null;
    }
}
