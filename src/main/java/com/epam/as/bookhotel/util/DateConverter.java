package com.epam.as.bookhotel.util;

import com.epam.as.bookhotel.exception.ActionException;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateConverter {

    public String getDateToStr(Date date) {

        final String DATE_PATTERN = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(DATE_PATTERN);
        return df.format(date);
    }

    /**
     * Convert received parameter from string to sql date type
     *
     * @param parameter date in string type
     * @return the date in java.sql.Date type
     * @throws ActionException general exception for throwing exceptions in actions.
     */
    public Date getStrToDate(String parameter) throws ActionException {

        final String DATE_PATTERN = "dd/MM/yyyy";
        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
        Date sqlDate;
        try {
            java.util.Date date = format.parse(parameter);
            sqlDate = new Date(date.getTime());
        } catch (ParseException e) {
            throw new ActionException(e);
        }
        return sqlDate;
    }
}
