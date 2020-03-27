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

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class CatalogValidatorTest {

    @Inject
    Validator validator;

    @Test
    void nullValue_isValid() {
        MyClass myClass = new MyClass();

        Set<ConstraintViolation<MyClass>> violations = validator.validate(myClass);
        assertTrue(violations.isEmpty());
    }

    @Test
    void usingEnumValue_isValid() {
        MyClass myClass = new MyClass();
        myClass.setMyField(CatalogTest.ONE.toString());

        Set<ConstraintViolation<MyClass>> violations = validator.validate(myClass);
        assertTrue(violations.isEmpty());
    }

    @Test
    void usingEnumCode_isValid() {
        MyClass myClass = new MyClass();
        myClass.setMyField(CatalogTest.ONE.getCode());

        Set<ConstraintViolation<MyClass>> violations = validator.validate(myClass);
        assertTrue(violations.isEmpty());
    }

    @Test
    void usingNoEnumValueNorCode_isInvalid() {
        MyClass myClass = new MyClass();
        myClass.setMyField("piet");

        Set<ConstraintViolation<MyClass>> violations = validator.validate(myClass);
        assertFalse(violations.isEmpty());
    }

    enum CatalogTest implements Catalog {
        ONE("uno"),
        TWO("dos"),
        THREE("tres");

        private String code;

        CatalogTest(String code) {
            this.code = code;
        }

        @Override
        public String getCode() {
            return code;
        }
    }

    public static class MyClass {
        @CatalogConstraint(value = CatalogTest.class)
        private String myField;

        public String getMyField() {
            return myField;
        }

        public void setMyField(String myField) {
            this.myField = myField;
        }
    }
}
