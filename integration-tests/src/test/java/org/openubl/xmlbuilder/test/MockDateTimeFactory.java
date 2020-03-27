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
