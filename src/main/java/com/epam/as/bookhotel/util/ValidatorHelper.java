package com.epam.as.bookhotel.util;

import com.epam.as.bookhotel.exception.ActionException;
import com.epam.as.bookhotel.exception.ValidatorException;
import com.epam.as.bookhotel.validator.FormValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class ValidatorHelper {

    public boolean checkForm(HttpServletRequest req, String validateForm) throws ActionException {
        boolean checkResult = false;
        try {
            FormValidator validator = new FormValidator();
            Map<String, List<String>> fieldErrors = validator.validate(validateForm, req);
            if (validator.hasFieldsErrors(req, fieldErrors)) checkResult = true;

        } catch (ValidatorException e) {
            throw new ActionException(e);
        }
        return checkResult;
    }

}
