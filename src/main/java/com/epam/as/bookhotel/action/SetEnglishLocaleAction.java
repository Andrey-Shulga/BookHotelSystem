package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.ActionException;
import com.epam.as.bookhotel.exception.LocaleChangerException;
import com.epam.as.bookhotel.util.LocaleUpdater;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action set english localization for web application interface.
 * Save locale name to user session
 */

public class SetEnglishLocaleAction implements Action {

    private static final String LOCALE_EN = "en";
    private static final String REFERRER = "referer";
    private static final String REDIRECT_PREFIX = "redirect:";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        LocaleUpdater changer = new LocaleUpdater();
        try {
            changer.changeUserLocale(req, LOCALE_EN);
        } catch (LocaleChangerException e) {
            throw new ActionException(e);
        }
        String referrer = req.getHeader(REFERRER);
        return REDIRECT_PREFIX + referrer;
    }


}
