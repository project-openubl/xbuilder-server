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


import org.openublpe.xmlbuilder.core.models.catalogs.Catalog;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class CatalogValidator implements ConstraintValidator<CatalogConstraint, String> {

    protected Class<? extends Enum<? extends Catalog>> catalog;

    @Override
    public void initialize(CatalogConstraint catalogConstraint) {
        catalog = catalogConstraint.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        // null values are valid
        if (value == null) {
            return true;
        }

        Enum<? extends Catalog>[] enumConstants = catalog.getEnumConstants();
        boolean matchEnumValues = Arrays.stream(enumConstants).map(Enum::toString).anyMatch(p -> p.equals(value));
        if (matchEnumValues) {
            return true;
        }

        return Arrays.stream(enumConstants).map(f -> (Catalog) f).map(Catalog::getCode).anyMatch(p -> p.equals(value));
    }
}
