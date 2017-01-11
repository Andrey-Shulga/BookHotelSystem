package com.epam.bookhotel.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action show main page.
 */

public class ShowIndexAction implements Action {

    private static final String INDEX_JSP = "index";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {

        return INDEX_JSP;
    }
}
