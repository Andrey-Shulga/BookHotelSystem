package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.*;
import com.epam.as.bookhotel.model.User;
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
    private static final String FORM_NAME = "register";
    private static final String REDIRECT = "redirect:/do/?action=show-register-success";
    private static final String ERROR_MESSAGE_SUFFIX = "ErrorMessages";
    private static final String LOGIN_PARAMETER = "login";
    private static final String PASSWORD_PARAMETER = "password";


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws PropertyManagerException, ValidatorException, ConnectionPoolException, ServiceException, JdbcDaoException {

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

        String login = req.getParameter(LOGIN_PARAMETER);
        String password = req.getParameter(PASSWORD_PARAMETER);

        User user = new User(login, password);
        UserService userService = new UserService();
        try {
            user = userService.register(user, req);
        } catch (UserExistingException | DatabaseConnectionException e) {
            req.setAttribute(FORM_NAME + ERROR_MESSAGE_SUFFIX, e.getMessage());
        }
        if (user.getId() == null) {
            return FORM_NAME;
        }
        return REDIRECT;
    }
}

