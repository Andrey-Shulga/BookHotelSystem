package com.epam.bookhotel.util;

import com.epam.bookhotel.exception.ValidatorException;
import com.epam.bookhotel.validator.FormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import static com.epam.bookhotel.constant.ConstantsHolder.ERROR_MESSAGES_POSTFIX;

/**
 * Utility for serves from validation
 */

public class ValidatorHelper {

    private static final Logger logger = LoggerFactory.getLogger(ValidatorHelper.class);

    private ValidatorHelper() {
    }

    /**
     * Validate form by rules
     *
     * @param req          request with values for validation
     * @param validateForm from which necessary validate
     * @return result of validation
     * @throws ValidatorException wrap for any exceptions in ValidatorHelper
     */
    public static boolean checkForm(HttpServletRequest req, String validateForm) throws ValidatorException {

        boolean checkResult = false;
        FormValidator validator = new FormValidator();
        Map<String, List<String>> fieldErrors = validator.validate(validateForm, req);
        if (validator.hasFieldsErrors(req, fieldErrors)) checkResult = true;
        return checkResult;
    }

    /**
     * Delete  validators' errors from session
     *
     * @param request http request for getting session
     */
    public static void deleteErrorsFromSession(HttpServletRequest request) {

        Enumeration<String> attributeNames = request.getSession().getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String sessionAttribute = attributeNames.nextElement();
            if (sessionAttribute.endsWith(ERROR_MESSAGES_POSTFIX))
                request.getSession().removeAttribute(sessionAttribute);
        }
    }

    /**
     * Set error from validators to session for output on jsp.
     *
     * @param req         http request for getting session
     * @param fieldErrors collection with messages about found errors
     */
    public static void setErrorsToSession(HttpServletRequest req, Map<String, List<String>> fieldErrors) {

        for (Map.Entry<String, List<String>> entry : fieldErrors.entrySet()) {
            req.getSession().setAttribute(entry.getKey() + ERROR_MESSAGES_POSTFIX, entry.getValue());
            for (String errorMessage : entry.getValue()) {
                logger.debug("In filed \"{}\" found error message \"{}\"", entry.getKey(), errorMessage);
            }
        }
    }

}
