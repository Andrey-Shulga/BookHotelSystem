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

    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private static final ThreadLocal<DateFormat> DATE_CACHE_FORMAT = new ThreadLocal<>();

    /**
     * Convert received parameter from date to string type
     *
     * @param date parameter for converting
     * @return date in String type
     */
    public static String getDateToStr(Date date) {

        return getFormat().format(date);
    }

    /**
     * Convert received parameter from string to date type
     *
     * @param parameter date in string type
     * @return date in Date type
     * @throws ActionException general exception for throwing exceptions in actions.
     */
    public static Date getStrToDate(String parameter) throws ActionException {

        Date date;
        try {
            date = getFormat().parse(parameter);
        } catch (ParseException e) {
            throw new ActionException(e);
        }
        return date;
    }

    /**
     * Return date format from cache or set new if return is null
     *
     * @return format for date
     */
    private static DateFormat getFormat() {

        DateFormat format = DATE_CACHE_FORMAT.get();
        if (format == null) {
            format = new SimpleDateFormat(DATE_PATTERN);
            DATE_CACHE_FORMAT.set(format);
        }
        return format;
    }
}
