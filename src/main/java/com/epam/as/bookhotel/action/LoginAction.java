package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.ActionException;
import com.epam.as.bookhotel.exception.ServiceException;
import com.epam.as.bookhotel.model.User;
import com.epam.as.bookhotel.model.UserLocale;
import com.epam.as.bookhotel.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action for login and authorization user in application
 */

public class LoginAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(LoginAction.class);
    private static final String LOGIN_FORM = "login";
    private static final String REDIRECT_LOGIN_FORM = "redirect:/do/?action=show-login-form";
    private static final String REDIRECT = "redirect:/do/?action=show-login-success";
    private static final String ERROR_MESSAGE_SUFFIX = "ErrorMessages";
    private static final String LOGIN_PARAMETER = "login";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String USER_SESSION_ATTRIBUTE_NAME = "user";
    private static final String LOCALE_SESSION_ATTRIBUTE_NAME = "locale";


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        String login = req.getParameter(LOGIN_PARAMETER);
        String password = req.getParameter(PASSWORD_PARAMETER);
        String locale = req.getParameter(LOCALE_SESSION_ATTRIBUTE_NAME);

        User user = new User(login, password, new UserLocale(locale));
        UserService userService = new UserService();
        try {
            userService.login(user);
        } catch (ServiceException e) {
            req.getSession().setAttribute(LOGIN_FORM + ERROR_MESSAGE_SUFFIX, e.getMessage());
            return REDIRECT_LOGIN_FORM;
        }

        //save user and his locale in session
        req.getSession().setAttribute(USER_SESSION_ATTRIBUTE_NAME, user);
        req.getSession().setAttribute(LOCALE_SESSION_ATTRIBUTE_NAME, user.getLocale().getLocaleName());
        logger.debug("User \"{}\" authorized.", user.getLogin());
        return REDIRECT;
    }
}
