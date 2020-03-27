package org.openublpe.xmlbuilder.core.models.input.constraints;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog1;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog6;
import org.openublpe.xmlbuilder.core.models.input.standard.note.creditNote.CreditNoteInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.SummaryDocumentComprobanteAfectadoInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.SummaryDocumentComprobanteInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.SummaryDocumentLineInputModel;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class SummaryDocumentLineInputModel_ComprobanteAfectadoRequeridoValidatorTest {

    @Inject
    Validator validator;

    @Test
    void comprobanteEsBoleta_comprobanteAfectadoNull_isValid() {
        SummaryDocumentLineInputModel input = SummaryDocumentLineInputModel.Builder.aSummaryDocumentLineInputModel()
                .withComprobante(SummaryDocumentComprobanteInputModel.Builder.aSummaryDocumentComprobanteInputModel()
                        .withTipo(Catalog1.BOLETA.toString())
                        .build()
                )
                .build();

        Set<ConstraintViolation<SummaryDocumentLineInputModel>> violations = validator.validate(input, SummaryDocumentLineInputModel_ComprobanteAfectadoRequeridoGroupValidation.class);
        assertTrue(violations.isEmpty());
    }

    @Test
    void comprobanteEsNotaCredito_comprobanteAfectadoNotnull_isValid() {
        SummaryDocumentLineInputModel input = SummaryDocumentLineInputModel.Builder.aSummaryDocumentLineInputModel()
                .withComprobante(SummaryDocumentComprobanteInputModel.Builder.aSummaryDocumentComprobanteInputModel()
                        .withTipo(Catalog1.NOTA_CREDITO.toString())
                        .build()
                )
                .withComprobanteAfectado(SummaryDocumentComprobanteAfectadoInputModel.Builder.aSummaryDocumentComprobanteAfectadoInputModel()
                        .build()
                )
                .build();

        Set<ConstraintViolation<SummaryDocumentLineInputModel>> violations = validator.validate(input, SummaryDocumentLineInputModel_ComprobanteAfectadoRequeridoGroupValidation.class);
        assertTrue(violations.isEmpty());
    }

    @Test
    void comprobanteEsNotaDebito_comprobanteAfectadoNotnull_isValid() {
        SummaryDocumentLineInputModel input = SummaryDocumentLineInputModel.Builder.aSummaryDocumentLineInputModel()
                .withComprobante(SummaryDocumentComprobanteInputModel.Builder.aSummaryDocumentComprobanteInputModel()
                        .withTipo(Catalog1.NOTA_DEBITO.toString())
                        .build()
                )
                .withComprobanteAfectado(SummaryDocumentComprobanteAfectadoInputModel.Builder.aSummaryDocumentComprobanteAfectadoInputModel()
                        .build()
                )
                .build();

        Set<ConstraintViolation<SummaryDocumentLineInputModel>> violations = validator.validate(input, SummaryDocumentLineInputModel_ComprobanteAfectadoRequeridoGroupValidation.class);
        assertTrue(violations.isEmpty());
    }

    @Test
    void comprobanteEsNotaCredito_comprobanteAfectadoNull_isInvalid() {
        SummaryDocumentLineInputModel input = SummaryDocumentLineInputModel.Builder.aSummaryDocumentLineInputModel()
                .withComprobante(SummaryDocumentComprobanteInputModel.Builder.aSummaryDocumentComprobanteInputModel()
                        .withTipo(Catalog1.NOTA_CREDITO.toString())
                        .build()
                )
                .build();

        Set<ConstraintViolation<SummaryDocumentLineInputModel>> violations = validator.validate(input, SummaryDocumentLineInputModel_ComprobanteAfectadoRequeridoGroupValidation.class);
        assertTrue(
                violations.stream().anyMatch(p -> p.getMessage().equals(SummaryDocumentLineInputModel_ComprobanteAfectadoRequeridoValidator.message))
        );
    }

    @Test
    void comprobanteEsNotaDebito_comprobanteAfectadoNull_isInvalid() {
        SummaryDocumentLineInputModel input = SummaryDocumentLineInputModel.Builder.aSummaryDocumentLineInputModel()
                .withComprobante(SummaryDocumentComprobanteInputModel.Builder.aSummaryDocumentComprobanteInputModel()
                        .withTipo(Catalog1.NOTA_DEBITO.toString())
                        .build()
                )
                .build();

        Set<ConstraintViolation<SummaryDocumentLineInputModel>> violations = validator.validate(input, SummaryDocumentLineInputModel_ComprobanteAfectadoRequeridoGroupValidation.class);
        assertTrue(
                violations.stream().anyMatch(p -> p.getMessage().equals(SummaryDocumentLineInputModel_ComprobanteAfectadoRequeridoValidator.message))
        );
    }
}
