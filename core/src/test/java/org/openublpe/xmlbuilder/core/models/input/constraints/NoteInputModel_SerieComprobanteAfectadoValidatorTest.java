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
package org.openublpe.xmlbuilder.core.models.input.constraints;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.openublpe.xmlbuilder.core.models.input.standard.note.creditNote.CreditNoteInputModel;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
