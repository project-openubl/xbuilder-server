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
package org.openublpe.xmlbuilder.core.models.catalogs;

import java.util.Optional;
import java.util.stream.Stream;

public interface Catalog {
    String getCode();

    /**
     * @return an instance of Catalog which is equal to ValueOf or contains the same code
     */
    static <T extends Catalog> Optional<T> valueOfCode(Class<T> enumType, String code) {
        return Stream.of(enumType.getEnumConstants())
                .filter(p -> p.toString().equalsIgnoreCase(code) || p.getCode().equals(code))
                .findFirst();
    }
}

