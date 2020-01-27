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
package org.openublpe.xmlbuilder.core.models.catalogs.constraints;

import org.junit.jupiter.api.Test;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CatalogValidatorTest {

    enum CatalogTest implements Catalog {
        ONE("uno"),
        TWO("dos"),
        THREE("tres");

        private String code;

        CatalogTest(String code){
            this.code = code;
        }

        @Override
        public String getCode() {
            return code;
        }
    }

    @Test
    void isValid() {
        CatalogValidator catalogValidator = new CatalogValidator();
        catalogValidator.catalog = CatalogTest.class;

        // Check enum values
        assertTrue(catalogValidator.isValid(CatalogTest.ONE.toString(), null));
        assertTrue(catalogValidator.isValid(CatalogTest.TWO.toString(), null));
        assertTrue(catalogValidator.isValid(CatalogTest.THREE.toString(), null));

        // Check codes
        assertTrue(catalogValidator.isValid(CatalogTest.ONE.getCode(), null));
        assertTrue(catalogValidator.isValid(CatalogTest.TWO.getCode(), null));
        assertTrue(catalogValidator.isValid(CatalogTest.THREE.getCode(), null));

        // Check white spaces and uppercase variations
        assertFalse(catalogValidator.isValid("ONE ", null));
        assertFalse(catalogValidator.isValid("one", null));
        assertFalse(catalogValidator.isValid("One", null));
    }
}
