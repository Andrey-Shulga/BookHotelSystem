package com.epam.as.bookhotel.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieHelper {

    public Cookie findParameter(HttpServletRequest req, String parameter) {

        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if (parameter.equals(cookie.getName())) return cookie;
        }
        return null;
    }

    public void setCookie(HttpServletResponse resp, String name, String value) {

        resp.addCookie(new Cookie(name, value));
    }
}
