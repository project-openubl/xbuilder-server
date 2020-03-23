package org.openublpe.xmlbuilder.core.models.input.constraints;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog6;
import org.openublpe.xmlbuilder.core.models.input.common.ClienteInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.DocumentLineInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.invoice.InvoiceInputModel;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class DocumentLineInputModel_CantidadValidaICBValidatorTest {

    @Inject
    Validator validator;

    @Test
    void cantidadICBFalse_isValid() {
        DocumentLineInputModel input = DocumentLineInputModel.Builder.aDocumentLineInputModel()
                .withIcb(false)
                .build();

        Set<ConstraintViolation<DocumentLineInputModel>> violations = validator.validate(input, DocumentLineInputModel_CantidadValidaICBGroupValidation.class);
        assertTrue(violations.isEmpty());
    }

    @Test
    void cantidadICBTrueAndCantidadEntera_isValid() {
        DocumentLineInputModel input = DocumentLineInputModel.Builder.aDocumentLineInputModel()
                .withIcb(true)
                .withCantidad(new BigDecimal("10.0"))
                .build();

        Set<ConstraintViolation<DocumentLineInputModel>> violations = validator.validate(input, DocumentLineInputModel_CantidadValidaICBGroupValidation.class);
        assertTrue(violations.isEmpty());
    }

    @Test
    void cantidadICBTrueAndCantidadNoEntera_isValid() {
        DocumentLineInputModel input = DocumentLineInputModel.Builder.aDocumentLineInputModel()
                .withIcb(true)
                .withCantidad(new BigDecimal("10.1"))
                .build();

        Set<ConstraintViolation<DocumentLineInputModel>> violations = validator.validate(input, DocumentLineInputModel_CantidadValidaICBGroupValidation.class);
        assertTrue(
                violations.stream().anyMatch(p -> p.getMessage().equals(DocumentLineInputModel_CantidadValidaICBValidator.message))
        );
    }

}
