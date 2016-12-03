package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.exception.ValidatorException;
import com.epam.as.bookhotel.model.User;
import com.epam.as.bookhotel.validator.FormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


public class RegisterAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(RegisterAction.class);
    private static final String FORM_NAME = "register";
    private static final String REDIRECT = "redirect:/do/?action=show-index";
    private static final String ERROR_MESSAGE_SUFFIX = "ErrorMessages";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws PropertyManagerException, ValidatorException {

        FormValidator registerFormValidator = new FormValidator();
        Map<String, List<String>> fieldErrors = registerFormValidator.validate(FORM_NAME, req);
        if (!fieldErrors.isEmpty()) {
            for (Map.Entry<String, List<String>> entry : fieldErrors.entrySet()) {
                req.setAttribute(entry.getKey() + ERROR_MESSAGE_SUFFIX, entry.getValue());
                for (String errorMessage : entry.getValue()) {
                    logger.debug("In filed \"{}\" found error message \"{}\"", entry.getKey(), errorMessage);
                }
            }
            return FORM_NAME;
        }
        logger.debug("Form's parameters are valid.");

        String login = req.getParameter(LOGIN);
        String password = req.getParameter(PASSWORD);

        User user = new User(login, password);

        return REDIRECT;
    }
}

