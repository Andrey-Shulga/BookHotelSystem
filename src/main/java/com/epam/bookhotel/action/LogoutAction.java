package com.epam.bookhotel.action;

import com.epam.bookhotel.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.bookhotel.constant.Constants.USER;

/**
 * This action logout user by invalidate user's session and return it on main page.
 */

public class LogoutAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(LogoutAction.class);
    private static final String REDIRECT_LOGIN_FORM = "redirect:/do/?action=show-login-form";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {


        if (req.getSession().getAttribute(USER) != null) {
            final User user = (User) req.getSession().getAttribute(USER);
            logger.debug("User with id=\"{}\" and login=\"{}\" is logout now.", user.getId(), user.getLogin());
            req.getSession().invalidate();
        }
        return REDIRECT_LOGIN_FORM;
    }
}
