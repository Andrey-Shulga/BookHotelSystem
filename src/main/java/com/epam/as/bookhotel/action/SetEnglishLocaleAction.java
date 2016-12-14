package com.epam.as.bookhotel.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SetEnglishLocaleAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(SetEnglishLocaleAction.class);
    private static final String LOCALE = "en";
    private static final String LOCALE_ATTR_NAME = "locale";
    private static final String REDIRECT_PREFIX = "redirect:";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        req.getSession(false).setAttribute(LOCALE_ATTR_NAME, LOCALE);
        String referrer = req.getHeader("referer");
        logger.debug("Locale changed on \"{}\"", LOCALE);

        return REDIRECT_PREFIX + referrer;
    }
}
