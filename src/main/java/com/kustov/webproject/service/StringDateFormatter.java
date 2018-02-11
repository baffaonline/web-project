package com.kustov.webproject.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


/**
 * The Class StringDateFormatter.
 */
public class StringDateFormatter {

    /**
     * Gets the date from string.
     *
     * @param date the date
     * @return the date from string
     */
    public static LocalDate getDateFromString(String date) {
        Date dateFromString;
        LocalDate localDate = LocalDate.MIN;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateFromString = format.parse(date);
            localDate = dateFromString.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (ParseException ignored) {
        }
        return localDate;
    }
}
