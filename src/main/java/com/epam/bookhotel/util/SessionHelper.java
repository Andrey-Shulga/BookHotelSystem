package com.epam.bookhotel.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Work with session
 */
public class SessionHelper {

    /**
     * Save field's value to session
     *
     * @param request  http request
     * @param attrName the name of attribute for saving
     * @param value    the value for saving attribute
     */
    public static void saveParamToSession(HttpServletRequest request, String attrName, String value) {

        request.getSession().setAttribute(attrName, value);

    }
}
