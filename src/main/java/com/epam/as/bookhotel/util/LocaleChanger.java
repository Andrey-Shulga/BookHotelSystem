package com.epam.as.bookhotel.util;

import com.epam.as.bookhotel.exception.ActionException;
import com.epam.as.bookhotel.exception.ServiceException;
import com.epam.as.bookhotel.model.User;
import com.epam.as.bookhotel.model.UserLocale;
import com.epam.as.bookhotel.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class LocaleChanger {

    private static final Logger logger = LoggerFactory.getLogger(LocaleChanger.class);

    private static final String LOCALE_ATTR_NAME = "locale";
    private static final String USER_ATTR_NAME = "user";


    public void changeUserLocale(HttpServletRequest req, String locale) throws ActionException {

        if (req.getSession(false).getAttribute(USER_ATTR_NAME) != null) {
            User user = (User) req.getSession(false).getAttribute(USER_ATTR_NAME);
            user.setLocale(new UserLocale(locale));
            UserService service = new UserService();
            try {
                service.saveUserLocale(user);
            } catch (ServiceException e) {
                throw new ActionException(e);
            }
        }
        req.getSession(false).setAttribute(LOCALE_ATTR_NAME, locale);

        logger.debug("Locale changed on \"{}\"", locale);

    }
}