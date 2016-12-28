package com.epam.as.bookhotel.util;

import com.epam.as.bookhotel.exception.ActionException;
import com.epam.as.bookhotel.exception.ValidatorException;
import com.epam.as.bookhotel.validator.FormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

public class ValidatorHelper {

    private static final Logger logger = LoggerFactory.getLogger(ValidatorHelper.class);
    private static final String ERROR_MESSAGE_SUFFIX = "ErrorMessages";

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

    public void deleteValidatorsErrorsFromSession(HttpServletRequest request) {

        Enumeration<String> attributeNames = request.getSession().getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String sessionAttribute = attributeNames.nextElement();
            if (sessionAttribute.endsWith(ERROR_MESSAGE_SUFFIX)) request.getSession().removeAttribute(sessionAttribute);
        }
    }

    public void setErrorsToSession(HttpServletRequest req, Map<String, List<String>> fieldErrors) {

        for (Map.Entry<String, List<String>> entry : fieldErrors.entrySet()) {
            req.getSession().setAttribute(entry.getKey() + ERROR_MESSAGE_SUFFIX, entry.getValue());
            for (String errorMessage : entry.getValue()) {
                logger.debug("In filed \"{}\" found error message \"{}\"", entry.getKey(), errorMessage);
            }
        }
    }

}
