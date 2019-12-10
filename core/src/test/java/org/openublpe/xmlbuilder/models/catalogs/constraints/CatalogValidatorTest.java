package org.openublpe.xmlbuilder.models.catalogs.constraints;

import org.junit.jupiter.api.Test;
import org.openublpe.xmlbuilder.models.catalogs.Catalog;

import static org.junit.jupiter.api.Assertions.*;

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
