package org.openublpe.xmlbuilder.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

    private DateUtils() {
        //TODO just static methods
    }

    public static String toGregorianCalendarDate(long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String toGregorianCalendarDate(long time, TimeZone timeZone) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(timeZone);
        return sdf.format(date);
    }

    public static String toGregorianCalendarTime(long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(date);
    }

    public static String toGregorianCalendarTime(long time, TimeZone timeZone) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        sdf.setTimeZone(timeZone);
        return sdf.format(date);
    }

}
