package io.github.project.openubl.xmlbuilder.config;

import io.github.project.openubl.xmlbuilderlib.utils.SystemClock;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import java.util.Calendar;
import java.util.TimeZone;

@ApplicationScoped
public class DefaultSystemClock implements SystemClock {

    @ConfigProperty(name = "openubl.timeZone")
    String timeZone;

    @Override
    public TimeZone getTimeZone() {
        return TimeZone.getTimeZone(timeZone);
    }

    @Override
    public Calendar getCalendarInstance() {
        return Calendar.getInstance();
    }

}
