package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(LogoutAction.class);
    private static final String INDEX_JSP = "index";
    private static final String USER_SESSION_ATTRIBUTE_NAME = "user";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        if (req.getSession().getAttribute(USER_SESSION_ATTRIBUTE_NAME) != null) {
            User user = (User) req.getSession().getAttribute(USER_SESSION_ATTRIBUTE_NAME);
            logger.debug("User with id=\"{}\" and login=\"{}\" logout now.", user.getId(), user.getLogin());
            req.getSession().invalidate();
        }
        return INDEX_JSP;
    }
}
