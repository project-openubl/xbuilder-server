package org.openublpe.xmlbuilder.core.models.input.constraints;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog6;
import org.openublpe.xmlbuilder.core.models.input.common.ClienteInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.note.creditNote.CreditNoteInputModel;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class NoteInputModel_SerieComprobanteAfectadoValidatorTest {

    @Inject
    Validator validator;

    @Test
    void documentoConSerieByComprobanteAfectadoB_isValid() {
        CreditNoteInputModel input = CreditNoteInputModel.Builder.aCreditNoteInputModel()
                .withSerie("BC01")
                .withSerieNumeroComprobanteAfectado("B001-1")
                .build();

        Set<ConstraintViolation<CreditNoteInputModel>> violations = validator.validate(input, NoteInputModel_SerieComprobanteAfectadoGroupValidation.class);
        assertTrue(violations.isEmpty());
    }

    @Test
    void documentoConSerieFyComprobanteAfectadoF_isValid() {
        CreditNoteInputModel input = CreditNoteInputModel.Builder.aCreditNoteInputModel()
                .withSerie("FC01")
                .withSerieNumeroComprobanteAfectado("F001-1")
                .build();

        Set<ConstraintViolation<CreditNoteInputModel>> violations = validator.validate(input, NoteInputModel_SerieComprobanteAfectadoGroupValidation.class);
        assertTrue(violations.isEmpty());
    }

    @Test
    void documentoConSerieFyComprobanteAfectadoB_isInvalid() {
        CreditNoteInputModel input = CreditNoteInputModel.Builder.aCreditNoteInputModel()
                .withSerie("FC01")
                .withSerieNumeroComprobanteAfectado("B001-1")
                .build();

        Set<ConstraintViolation<CreditNoteInputModel>> violations = validator.validate(input, NoteInputModel_SerieComprobanteAfectadoGroupValidation.class);
        assertTrue(
                violations.stream().anyMatch(p -> p.getMessage().equals(NoteInputModel_SerieComprobanteAfectadoValidator.message))
        );
    }

    @Test
    void documentoConSerieByComprobanteAfectadoF_isInvalid() {
        CreditNoteInputModel input = CreditNoteInputModel.Builder.aCreditNoteInputModel()
                .withSerie("BC01")
                .withSerieNumeroComprobanteAfectado("F001-1")
                .build();

        Set<ConstraintViolation<CreditNoteInputModel>> violations = validator.validate(input, NoteInputModel_SerieComprobanteAfectadoGroupValidation.class);
        assertTrue(
                violations.stream().anyMatch(p -> p.getMessage().equals(NoteInputModel_SerieComprobanteAfectadoValidator.message))
        );
    }
}
