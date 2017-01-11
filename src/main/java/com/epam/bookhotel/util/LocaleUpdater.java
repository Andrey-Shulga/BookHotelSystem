package com.epam.bookhotel.util;

import com.epam.bookhotel.entity.User;
import com.epam.bookhotel.entity.UserLocale;
import com.epam.bookhotel.exception.LocaleChangerException;
import com.epam.bookhotel.exception.ServiceException;
import com.epam.bookhotel.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

import static com.epam.bookhotel.constant.Constants.LOCALE;
import static com.epam.bookhotel.constant.Constants.USER;

/**
 * Utility for change and update user locale if user change interface language
 */

public class LocaleUpdater {

    private static final Logger logger = LoggerFactory.getLogger(LocaleUpdater.class);

    /**
     * Save user locale to database and session.
     *
     * @param req    http request
     * @param locale new locale for change
     * @throws LocaleChangerException if any exception when locale is changing occurred
     */
    public static void changeUserLocale(HttpServletRequest req, String locale) throws LocaleChangerException {

        if (req.getSession().getAttribute(USER) != null) {
            final User user = (User) req.getSession().getAttribute(USER);
            user.setLocale(new UserLocale(locale));
            UserService service = new UserService();
            try {
                service.saveUserLocale(user);
            } catch (ServiceException e) {
                throw new LocaleChangerException(e);
            }
        }

        req.getSession().setAttribute(LOCALE, locale);
        logger.debug("Locale changed on \"{}\"", locale);

    }
}