package com.epam.bookhotel.action;

import com.epam.bookhotel.exception.ActionException;
import com.epam.bookhotel.exception.LocaleChangerException;
import com.epam.bookhotel.util.CookieHelper;
import com.epam.bookhotel.util.LocaleUpdater;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.bookhotel.constant.ConstantsHolder.LOCALE;
import static com.epam.bookhotel.constant.ConstantsHolder.REDIRECT_PREFIX;

public class ChangeLocaleAction implements Action {

    private static final String REFERRER = "referer";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        String userLocale = req.getParameter(LOCALE);
        try {
            LocaleUpdater.changeUserLocale(req, userLocale);
        } catch (LocaleChangerException e) {
            throw new ActionException(e);
        }
        CookieHelper.setCookie(res, LOCALE, userLocale);
        String referrer = req.getHeader(REFERRER);

        return REDIRECT_PREFIX + referrer;
    }
}
