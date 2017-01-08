package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.ActionException;
import com.epam.as.bookhotel.exception.LocaleChangerException;
import com.epam.as.bookhotel.util.CookieHelper;
import com.epam.as.bookhotel.util.LocaleUpdater;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeLocaleAction implements Action {

    private static final String LOCALE_ATTR = "locale";
    private static final String REFERRER = "referer";
    private static final String REDIRECT_PREFIX = "redirect:";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        String userLocale = req.getParameter(LOCALE_ATTR);
        try {
            LocaleUpdater.changeUserLocale(req, userLocale);
        } catch (LocaleChangerException e) {
            throw new ActionException(e);
        }
        CookieHelper.setCookie(res, LOCALE_ATTR, userLocale);
        String referrer = req.getHeader(REFERRER);

        return REDIRECT_PREFIX + referrer;
    }
}
