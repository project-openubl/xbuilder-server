package org.openublpe.xmlbuilder.rules.datetime;

import javax.enterprise.context.ApplicationScoped;
import java.util.Calendar;

@ApplicationScoped
public class DateTimeFactory {

    public Calendar getCurrent() {
        return Calendar.getInstance();
    }

}
