/**
 * Copyright 2019 Project OpenUBL, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Eclipse Public License - v 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.project.openubl.xmlbuilder.config;

import io.github.project.openubl.xmlbuilder.config.qualifiers.CDIProvider;
import io.github.project.openubl.xmlbuilderlib.clock.SystemClock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class DefaultSystemClockTest {

    @Inject
    @CDIProvider
    SystemClock systemClock;

    @Test
    public void test_getTimeZone() {
        TimeZone timeZone = systemClock.getTimeZone();

        assertNotNull(timeZone);
        assertEquals(TimeZone.getTimeZone("America/Lima"), timeZone);
    }

    @Test
    public void test_getCalendarInstance() {
        assertNotNull(systemClock.getCalendarInstance());
    }

}
