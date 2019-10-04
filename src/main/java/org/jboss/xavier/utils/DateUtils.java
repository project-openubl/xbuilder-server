package org.jboss.xavier.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public Date getUnixEpochDate() {
        return new Date(0L);
    }

    public Date getLastMonday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // Get last MONDAY
        int dayOfTheWeek = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DAY_OF_WEEK, Calendar.MONDAY - dayOfTheWeek);
        return calendar.getTime();
    }

    public Date getLastMondayMinus7days() {
        Date lastMonday = getLastMonday();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lastMonday);
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        return calendar.getTime();
    }
}
