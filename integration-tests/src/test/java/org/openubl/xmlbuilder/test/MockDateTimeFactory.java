package org.openubl.xmlbuilder.test;

import io.quarkus.test.Mock;
import org.openublpe.xmlbuilder.rules.datetime.DateTimeFactory;

import javax.enterprise.context.ApplicationScoped;
import java.util.Calendar;

@Mock
@ApplicationScoped
public class MockDateTimeFactory extends DateTimeFactory {

    @Override
    public Calendar getCurrent() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.DECEMBER, 24, 20, 30, 59);

        return calendar;
    }
}
