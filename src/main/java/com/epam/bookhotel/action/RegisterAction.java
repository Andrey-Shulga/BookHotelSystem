package com.epam.bookhotel.action;

import com.epam.bookhotel.entity.User;
import com.epam.bookhotel.entity.UserLocale;
import com.epam.bookhotel.entity.UserRole;
import com.epam.bookhotel.entity.UserType;
import com.epam.bookhotel.exception.ActionException;
import com.epam.bookhotel.exception.ServiceException;
import com.epam.bookhotel.exception.ValidatorException;
import com.epam.bookhotel.service.UserService;
import com.epam.bookhotel.validator.FormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static com.epam.bookhotel.constant.Constants.*;

/**
 * Action for registration user in application
 * Save user in database.
 */

public class RegisterAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(RegisterAction.class);
    private static final String REDIRECT_REGISTER_FORM = "redirect:/do/?action=show-register-form";
    private static final String REGISTER_FORM = "register";
    private static final String REDIRECT_REGISTER_SUCCESS = "redirect:/do/?action=show-register-success";
    private static final String CONFIRM_PASSWORD = "confirm_password";


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        saveInputField(req);
        //validate form's fields by rules
        try {
            FormValidator validator = new FormValidator();
            Map<String, List<String>> fieldErrors = validator.validate(REGISTER_FORM, req);
            validator.checkFieldsOnEquals(PASSWORD, CONFIRM_PASSWORD, req);
            if (validator.hasFieldsErrors(req, fieldErrors)) return REDIRECT_REGISTER_FORM;
        } catch (ValidatorException e) {
            throw new ActionException(e);
        }
        logger.debug("Form's parameters are valid.");

        final String login = req.getParameter(LOGIN);
        final String password = req.getParameter(PASSWORD);
        final UserRole userRole = new UserRole(UserType.USER);
        final String locale = (String) req.getSession().getAttribute(LOCALE);
        final UserLocale userLocale = new UserLocale(locale);
        final User user = new User(login, password, userRole, userLocale);
        UserService userService = new UserService();
        try {
            userService.register(user);
            logger.debug("{} has been inserted into database.", user);
        } catch (ServiceException e) {
            req.getSession().setAttribute(REGISTER_FORM + ERROR_MESSAGES_POSTFIX, e.getMessage());
            return REDIRECT_REGISTER_FORM;
        }
        logger.debug("Register action success.");
        return REDIRECT_REGISTER_SUCCESS;
    }

    private void saveInputField(HttpServletRequest req) {

        String login = req.getParameter(LOGIN);
        req.getSession().setAttribute(LOGIN, login);
    }
}

