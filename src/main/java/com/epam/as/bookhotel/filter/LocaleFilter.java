package com.epam.as.bookhotel.filter;

import com.epam.as.bookhotel.util.CookieHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.util.Locale;

/**
 * Change page output locale by user's locale
 */

public class LocaleFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LocaleFilter.class);
    private static final String DEFAULT_LOCALE = "en";
    private static final String LOCALE_ATTR_NAME = "locale";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();

        String locale = null;
        //try to find locale in cookie and set it to session
        CookieHelper cookieHelper = new CookieHelper();
        Cookie localCookie = cookieHelper.findParameter(request, LOCALE_ATTR_NAME);
        if (localCookie != null) {
            locale = localCookie.getValue();
            session.setAttribute(LOCALE_ATTR_NAME, locale);
        }

        //if locale not found in cookie try to read locale from session and set it in cookie
        if (locale == null) {
            logger.debug("Locale not found in cookie, try to find in session.");
            locale = (String) request.getSession(false).getAttribute(LOCALE_ATTR_NAME);
            cookieHelper.setCookie(response, LOCALE_ATTR_NAME, locale);
        }

        //if locale not found in session and cookie - get default locale and set it to session and cookie
        if (locale == null) {
            locale = DEFAULT_LOCALE;
            cookieHelper.setCookie(response, LOCALE_ATTR_NAME, locale);
            session.setAttribute(LOCALE_ATTR_NAME, locale);
            logger.debug("Locale not found in session, set default locale \"{}\"", locale);
        }

        Locale currentLocale = new Locale(locale);
        Config.set(session, Config.FMT_LOCALE, currentLocale);
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
