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
