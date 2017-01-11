package com.epam.bookhotel.action;

import com.epam.bookhotel.entity.User;
import com.epam.bookhotel.entity.UserLocale;
import com.epam.bookhotel.exception.ActionException;
import com.epam.bookhotel.exception.ServiceException;
import com.epam.bookhotel.service.UserService;
import com.epam.bookhotel.util.SessionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.bookhotel.constant.Constants.*;

/**
 * Action for login and authorization user in application
 */

public class LoginAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(LoginAction.class);
    private static final String REDIRECT_LOGIN_FORM = "redirect:/do/?action=show-login-form";
    private static final String REDIRECT_LOGIN_SUCCESS = "redirect:/do/?action=show-login-success";
    private static final String LOGIN_BUTTON = "loginButton";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        String login = req.getParameter(LOGIN);
        SessionHelper.saveParamToSession(req, LOGIN, login);
        String password = req.getParameter(PASSWORD);
        String locale = (String) req.getSession().getAttribute(LOCALE);

        final User user = new User(login, password, new UserLocale(locale));
        UserService userService = new UserService();
        User foundUser;
        try {
            foundUser = userService.login(user);
            foundUser.setLocale(new UserLocale(locale));
        } catch (ServiceException e) {
            req.getSession().setAttribute(LOGIN_BUTTON + ERROR_MESSAGES_POSTFIX, e.getMessage());
            return REDIRECT_LOGIN_FORM;
        }

        //save user to session
        req.getSession().setAttribute(USER, foundUser);
        logger.debug("User \"{}\" authorized.", foundUser.getLogin());
        return REDIRECT_LOGIN_SUCCESS;
    }

}
