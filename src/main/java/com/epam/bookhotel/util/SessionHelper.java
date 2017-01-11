package com.epam.bookhotel.util;

import javax.servlet.http.HttpServletRequest;

public class SessionHelper {

    public static void saveParamToSession(HttpServletRequest request, String attrName, String value) {

        request.getSession().setAttribute(attrName, value);

    }
}
