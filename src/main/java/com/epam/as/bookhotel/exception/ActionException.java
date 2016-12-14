package com.epam.as.bookhotel.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionException extends Exception {

    private static final Logger logger = LoggerFactory.getLogger(ActionException.class);

    public ActionException(Exception e) {
        super(e);
    }


}
