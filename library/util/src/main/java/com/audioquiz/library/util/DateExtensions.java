package com.audioquiz.library.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateExtensions {

    private static final String DEFAULT_FORMAT = "yyyy-MM-dd";

    private DateExtensions() {
        // Private constructor to prevent instantiation
    }

    public static Date getDateValue(Object value) {
        if (value instanceof Date) {
            return (Date) value;
        } else {
            return new Date();
        }
    }


    public static String toString(Date date, String format, Locale locale) {
        if (date == null) {
            return "N/A"; // Or handle null as needed
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format, locale);
        return formatter.format(date);
    }

    public static String toString(Date date) {
        return toString(date, DEFAULT_FORMAT, Locale.getDefault());
    }

    public static Date orNow(Date date) {
        return date != null ? date : new Date();
    }

    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }
}
