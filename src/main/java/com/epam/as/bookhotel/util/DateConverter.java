package com.epam.as.bookhotel.util;

import com.epam.as.bookhotel.exception.ActionException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility for date converter
 */

public class DateConverter {

    /**
     * Convert received parameter from date to string type
     *
     * @param date parameter for converting
     * @return date in String type
     */
    public String getDateToStr(Date date) {

        final String DATE_PATTERN = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(DATE_PATTERN);
        return df.format(date);
    }

    /**
     * Convert received parameter from string to date type
     *
     * @param parameter date in string type
     * @return date in Date type
     * @throws ActionException general exception for throwing exceptions in actions.
     */
    public Date getStrToDate(String parameter) throws ActionException {

        final String DATE_PATTERN = "dd/MM/yyyy";
        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
        Date date;
        try {
            date = format.parse(parameter);
        } catch (ParseException e) {
            throw new ActionException(e);
        }
        return date;
    }
}
