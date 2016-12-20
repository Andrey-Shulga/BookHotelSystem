package com.epam.as.bookhotel.action;


import com.epam.as.bookhotel.exception.ActionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * An interface for actions with application
 */

public interface Action {

    /**
     * Base executive method for all action implementations
     *
     * @param req http request to main servlet
     * @param res http response from main servlet
     * @throws ActionException - general exceptions throws on any caught exceptions in actions.
     */
    String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException;

}
