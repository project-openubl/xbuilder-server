package io.github.project.openubl.xmlbuilder.config;

import io.github.project.openubl.xmlbuilderlib.utils.SystemClock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class DefaultSystemClockTest {

    @Inject
    SystemClock systemClock;

    @Test
    void test_getTimeZone() {
        TimeZone timeZone = systemClock.getTimeZone();

        assertNotNull(timeZone);
        assertEquals(TimeZone.getTimeZone("America/Lima"), timeZone);
    }

    @Test
    void test_getCalendarInstance() {
        assertNotNull(systemClock.getCalendarInstance());
    }

}
