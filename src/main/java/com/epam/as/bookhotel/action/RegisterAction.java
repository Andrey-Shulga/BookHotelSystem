package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.ActionException;
import com.epam.as.bookhotel.exception.ServiceException;
import com.epam.as.bookhotel.exception.ValidatorException;
import com.epam.as.bookhotel.model.User;
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


public class RegisterAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(RegisterAction.class);
    private static final String REGISTER_FORM = "register";
    private static final String REDIRECT = "redirect:/do/?action=show-register-success";
    private static final String ERROR_MESSAGE_SUFFIX = "ErrorMessages";
    private static final String LOGIN_PARAMETER = "login";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String CONFIRM_PASSWORD_PARAMETER = "confirm_password";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        if (req.getParameter(LOGIN_PARAMETER) == null) return REGISTER_FORM;

        try {
            FormValidator validator = new FormValidator();
            Map<String, List<String>> fieldErrors = validator.validate(REGISTER_FORM, req);
            validator.checkPasswordsEquals(PASSWORD_PARAMETER, CONFIRM_PASSWORD_PARAMETER, req);
            if (!fieldErrors.isEmpty()) {
                validator.setErrorToRequest(req);
                return REGISTER_FORM;
            }
        } catch (ValidatorException e) {
            throw new ActionException(e);
        }
        logger.debug("Form's parameters are valid.");

        String login = req.getParameter(LOGIN_PARAMETER);
        String password = req.getParameter(PASSWORD_PARAMETER);
        UserRole userRole = new UserRole(UserType.USER);
        User user = new User(login, password, userRole);
        UserService userService = new UserService();
        try {
            user = userService.register(user);
            logger.debug("User with id=\"{}\", login=\"{}\", password=\"{}\", role=\"{}\" inserted into database.",
                    user.getId(), user.getLogin(), user.getPassword(), user.getRole().toString());
        } catch (ServiceException e) {
            req.setAttribute(REGISTER_FORM + ERROR_MESSAGE_SUFFIX, e.getMessage());
            return REGISTER_FORM;
        }

        return REDIRECT;
    }
}

