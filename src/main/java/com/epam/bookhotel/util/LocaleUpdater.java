package com.epam.bookhotel.util;

import com.epam.bookhotel.entity.User;
import com.epam.bookhotel.entity.UserLocale;
import com.epam.bookhotel.exception.LocaleChangerException;
import com.epam.bookhotel.exception.ServiceException;
import com.epam.bookhotel.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Utility for change and update user locale if user change interface language
 */

public class LocaleUpdater {

    private static final Logger logger = LoggerFactory.getLogger(LocaleUpdater.class);

    private static final String LOCALE_ATTR_NAME = "locale";
    private static final String USER_ATTR_NAME = "user";

    /**
     * Save user locale to database and session.
     *
     * @param req    http request
     * @param locale locale from user
     * @throws LocaleChangerException if any exception when locale is changing occurred
     */
    public static void changeUserLocale(HttpServletRequest req, String locale) throws LocaleChangerException {

        if (req.getSession(false).getAttribute(USER_ATTR_NAME) != null) {
            final User user = (User) req.getSession(false).getAttribute(USER_ATTR_NAME);
            user.setLocale(new UserLocale(locale));
            UserService service = new UserService();
            try {
                service.saveUserLocale(user);
            } catch (ServiceException e) {
                throw new LocaleChangerException(e);
            }
        }
        req.getSession(false).setAttribute(LOCALE_ATTR_NAME, locale);

        logger.debug("Locale changed on \"{}\"", locale);

    }
}