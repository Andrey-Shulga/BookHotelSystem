package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.ActionException;
import com.epam.as.bookhotel.exception.ServiceException;
import com.epam.as.bookhotel.exception.ValidatorException;
import com.epam.as.bookhotel.model.User;
import com.epam.as.bookhotel.model.UserLocale;
import com.epam.as.bookhotel.model.UserRole;
import com.epam.as.bookhotel.model.UserType;
import com.epam.as.bookhotel.service.UserService;
import com.epam.as.bookhotel.validator.FormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Action for registration user in application
 * Save user in database.
 */

public class RegisterAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(RegisterAction.class);
    private static final String REDIRECT_REGISTER_FORM = "redirect:/do/?action=show-register-form";
    private static final String REGISTER_FORM = "register";
    private static final String REDIRECT = "redirect:/do/?action=show-register-success";
    private static final String ERROR_MESSAGE_SUFFIX = "ErrorMessages";
    private static final String LOGIN_PARAMETER = "login";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String CONFIRM_PASSWORD_PARAMETER = "confirm_password";
    private static final String LOCALE_SESSION_ATTR_NAME = "locale";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        saveInputField(req);
        //validate form's fields by rules
        try {
            FormValidator validator = new FormValidator();
            Map<String, List<String>> fieldErrors = validator.validate(REGISTER_FORM, req);
            validator.checkFieldsOnEquals(PASSWORD_PARAMETER, CONFIRM_PASSWORD_PARAMETER, req);
            if (validator.hasFieldsErrors(req, fieldErrors)) return REDIRECT_REGISTER_FORM;
        } catch (ValidatorException e) {
            throw new ActionException(e);
        }
        logger.debug("Form's parameters are valid.");

        String login = req.getParameter(LOGIN_PARAMETER);
        String password = req.getParameter(PASSWORD_PARAMETER);
        UserRole userRole = new UserRole(UserType.USER);
        String locale = (String) req.getSession().getAttribute(LOCALE_SESSION_ATTR_NAME);
        UserLocale userLocale = new UserLocale(locale);
        final User user = new User(login, password, userRole, userLocale);
        UserService userService = new UserService();
        try {
            userService.register(user);
            logger.debug("{} inserted into database.", user);
        } catch (ServiceException e) {
            req.getSession().setAttribute(REGISTER_FORM + ERROR_MESSAGE_SUFFIX, e.getMessage());
            return REDIRECT_REGISTER_FORM;
        }
        logger.debug("Register action success.");
        return REDIRECT;
    }

    private void saveInputField(HttpServletRequest req) {

        String login = req.getParameter(LOGIN_PARAMETER);
        req.getSession().setAttribute(LOGIN_PARAMETER, login);
    }
}

