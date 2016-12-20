package com.epam.as.bookhotel.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.util.Locale;

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

        String locale = (String) request.getSession(false).getAttribute(LOCALE_ATTR_NAME);
        HttpSession session = request.getSession();
        if (locale == null) {
            locale = DEFAULT_LOCALE;
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
