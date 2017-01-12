package com.epam.bookhotel.filter;

import com.epam.bookhotel.util.CookieHelper;
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

import static com.epam.bookhotel.constant.ConstantsHolder.LOCALE;

/**
 * Change page output locale by user's locale
 */

public class LocaleFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LocaleFilter.class);
    private static final String DEFAULT_LOCALE = "en";

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
        Cookie localCookie = CookieHelper.findParameter(request, LOCALE);
        if (localCookie != null) locale = localCookie.getValue();

        //if locale not found in cookie try to read locale from session and set it in cookie
        if (locale == null) {
            logger.debug("Locale not found in cookie, try to find in session.");
            locale = (String) session.getAttribute(LOCALE);
            CookieHelper.setCookie(response, LOCALE, locale);
        }

        //if locale not found in session and cookie - get default locale and set it to session and cookie
        if (locale == null) {
            locale = DEFAULT_LOCALE;
            CookieHelper.setCookie(response, LOCALE, locale);
            logger.debug("Locale not found in session, set default locale \"{}\"", locale);
        }

        Locale currentLocale = new Locale(locale);
        Config.set(session, Config.FMT_LOCALE, currentLocale);
        session.setAttribute(LOCALE, locale);
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
