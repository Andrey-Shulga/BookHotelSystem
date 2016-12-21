package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.ActionException;
import com.epam.as.bookhotel.util.LocaleChanger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action set russian localization for web application interface.
 * Save locale name to user session.
 */

public class SetRussianLocaleAction implements Action {

    private static final String LOCALE_RU = "ru";
    private static final String REFERRER = "referer";
    private static final String REDIRECT_PREFIX = "redirect:";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        LocaleChanger changer = new LocaleChanger();
        changer.changeUserLocale(req, LOCALE_RU);
        String referrer = req.getHeader(REFERRER);
        return REDIRECT_PREFIX + referrer;
    }
}
