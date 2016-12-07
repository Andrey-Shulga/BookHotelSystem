package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.ConnectionPoolException;
import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.exception.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SetRussianLocaleAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(SetRussianLocaleAction.class);
    private static final String LOCALE = "ru";
    private static final String LOCALE_ATTR_NAME = "locale";
    private static final String REDIRECT_PREFIX = "redirect:";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws PropertyManagerException, ValidatorException, ConnectionPoolException, JdbcDaoException {
        req.getSession(false).setAttribute(LOCALE_ATTR_NAME, LOCALE);
        String referrer = req.getHeader("referer");
        logger.debug("Locale changed on \"{}\"", LOCALE);
        return REDIRECT_PREFIX + referrer;
    }
}
