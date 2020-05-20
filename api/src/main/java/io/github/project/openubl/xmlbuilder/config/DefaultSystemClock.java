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

import io.github.project.openubl.xmlbuilderlib.clock.SystemClock;
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
