package com.epam.bookhotel.action;

import com.epam.bookhotel.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This action logout user by invalidate user's session and return it on main page.
 */

public class LogoutAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(LogoutAction.class);
    private static final String REDIRECT = "redirect:/do/?action=show-login-form";
    private static final String USER_SESSION_ATTRIBUTE_NAME = "user";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {

        boolean isCreated = false;
        if (req.getSession().getAttribute(USER_SESSION_ATTRIBUTE_NAME) != null) {
            final User user = (User) req.getSession().getAttribute(USER_SESSION_ATTRIBUTE_NAME);
            logger.debug("User with id=\"{}\" and login=\"{}\" is logout now.", user.getId(), user.getLogin());
            req.getSession(isCreated).invalidate();
        }
        return REDIRECT;
    }
}
