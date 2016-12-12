package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.ConnectionPoolException;
import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.exception.ValidatorException;
import com.epam.as.bookhotel.model.User;
import com.epam.as.bookhotel.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(LoginAction.class);
    private static final String LOGIN_FORM = "login";
    private static final String REDIRECT = "redirect:/do/?action=show-login-success";
    private static final String ERROR_MESSAGE_SUFFIX = "ErrorMessages";
    private static final String LOGIN_PARAMETER = "login";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String USER_SESSION_ATTRIBUTE_NAME = "user";


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws PropertyManagerException, ValidatorException, ConnectionPoolException, JdbcDaoException {

        String login = req.getParameter(LOGIN_PARAMETER);
        String password = req.getParameter(PASSWORD_PARAMETER);

        User user = new User(login, password);
        UserService userService = new UserService();
        try {
            user = userService.login(user);
            logger.debug("User with id=\"{}\", login=\"{}\", password=\"{}\", role=\"{}\" found in database.", user.getId(), user.getLogin(), user.getPassword(), user.getRole().toString());
        } catch (JdbcDaoException e) {
            req.setAttribute(LOGIN_FORM + ERROR_MESSAGE_SUFFIX, e.getMessage());
            return LOGIN_FORM;
        }

        req.getSession().setAttribute(USER_SESSION_ATTRIBUTE_NAME, user);

        logger.debug("{} authorized.", user);
        return REDIRECT;
    }
}
