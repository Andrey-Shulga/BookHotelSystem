package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.exception.ValidatorException;
import com.epam.as.bookhotel.validator.FormValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


public class RegisterAction implements Action {

    private static final String FORM_NAME = "register";
    private static final String REDIRECT = "redirect:/do/?action=show-index";


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws PropertyManagerException, ValidatorException {

        FormValidator registerFormValidator = new FormValidator();
        Map<String, String> fieldErrors = registerFormValidator.validate(FORM_NAME, req);
        if (!fieldErrors.isEmpty()) {

            return FORM_NAME;
        }

        return REDIRECT;
    }
}

