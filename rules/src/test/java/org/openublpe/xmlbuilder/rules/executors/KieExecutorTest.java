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
package org.openublpe.xmlbuilder.rules.executors;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.openublpe.xmlbuilder.core.models.input.standard.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.note.creditNote.CreditNoteInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.note.debitNote.DebitNoteInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.SummaryDocumentInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.VoidedDocumentInputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.invoice.InvoiceOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.note.creditNote.CreditNoteOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.note.debitNote.DebitNoteOutputModel;
import org.openublpe.xmlbuilder.core.models.output.sunat.SummaryDocumentOutputModel;
import org.openublpe.xmlbuilder.core.models.output.sunat.VoidedDocumentOutputModel;
import org.openublpe.xmlbuilder.inputdata.AbstractInputDataTest;
import org.openublpe.xmlbuilder.inputdata.generator.InputGenerator;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class KieExecutorTest extends AbstractInputDataTest {

    @Inject
    Validator validator;

    @Inject
    KieExecutor kieExecutor;

    @Test
    void getInvoiceOutputModel() {
        assertEquals(InputGenerator.NUMBER_TEST_INVOICES, INVOICES.size(), "The number of test cases is not the expected one");

        for (InvoiceInputModel input : INVOICES) {
            // GIVEN

            // WHEN
            InvoiceOutputModel output = kieExecutor.getInvoiceOutputModel(input);

            // THEN
            assertNotNull(output);

            Set<ConstraintViolation<InvoiceOutputModel>> violations = validator.validate(output);
            assertTrue(
                    violations.isEmpty(),
                    messageInputDataError(
                            input,
                            violations.stream()
                                    .map(f -> f.getPropertyPath() + ": " + f.getMessage())
                                    .collect(Collectors.joining(", "))
                    )
            );
        }
    }

    @Test
    void getCreditNoteOutputModel() {
        assertEquals(InputGenerator.NUMBER_TEST_CREDIT_NOTES, CREDIT_NOTES.size(), "The number of test cases is not the expected one");

        for (CreditNoteInputModel input : CREDIT_NOTES) {
            // GIVEN

            // WHEN
            CreditNoteOutputModel output = kieExecutor.getCreditNoteOutputModel(input);

            // THEN
            assertNotNull(output);

            Set<ConstraintViolation<CreditNoteOutputModel>> violations = validator.validate(output);
            assertTrue(
                    violations.isEmpty(),
                    messageInputDataError(
                            input,
                            violations.stream()
                                    .map(f -> f.getPropertyPath() + ": " + f.getMessage())
                                    .collect(Collectors.joining(", "))
                    )
            );
        }
    }

    @Test
    void getDebitNoteOutputModel() {
        assertEquals(InputGenerator.NUMBER_TEST_DEBIT_NOTES, DEBIT_NOTES.size(), "The number of test cases is not the expected one");

        for (DebitNoteInputModel input : DEBIT_NOTES) {
            // GIVEN

            // WHEN
            DebitNoteOutputModel output = kieExecutor.getDebitNoteOutputModel(input);

            // THEN
            assertNotNull(output);

            Set<ConstraintViolation<DebitNoteOutputModel>> violations = validator.validate(output);
            assertTrue(
                    violations.isEmpty(),
                    messageInputDataError(
                            input,
                            violations.stream()
                                    .map(f -> f.getPropertyPath() + ": " + f.getMessage())
                                    .collect(Collectors.joining(", "))
                    )
            );
        }
    }

    @Test
    void getVoidedDocumentOutputModel() {
        assertEquals(InputGenerator.NUMBER_TEST_VOIDED_DOCUMENTS, VOIDED_DOCUMENTS.size(), "The number of test cases is not the expected one");

        for (VoidedDocumentInputModel input : VOIDED_DOCUMENTS) {
            // GIVEN

            // WHEN
            VoidedDocumentOutputModel output = kieExecutor.getVoidedDocumentOutputModel(input);

            // THEN
            assertNotNull(output);

            Set<ConstraintViolation<VoidedDocumentOutputModel>> violations = validator.validate(output);
            assertTrue(
                    violations.isEmpty(),
                    messageInputDataError(
                            input,
                            violations.stream()
                                    .map(f -> f.getPropertyPath() + ": " + f.getMessage())
                                    .collect(Collectors.joining(", "))
                    )
            );
        }
    }

    @Test
    void getSummaryDocumentOutputModel() {
        assertEquals(InputGenerator.NUMBER_TEST_SUMMARY_DOCUMENTS, SUMMARY_DOCUMENTS.size(), "The number of test cases is not the expected one");

        for (SummaryDocumentInputModel input : SUMMARY_DOCUMENTS) {
            // GIVEN

            // WHEN
            SummaryDocumentOutputModel output = kieExecutor.getSummaryDocumentOutputModel(input);

            // THEN
            assertNotNull(output);

            Set<ConstraintViolation<SummaryDocumentOutputModel>> violations = validator.validate(output);
            assertTrue(
                    violations.isEmpty(),
                    messageInputDataError(
                            input,
                            violations.stream()
                                    .map(f -> f.getPropertyPath() + ": " + f.getMessage())
                                    .collect(Collectors.joining(", "))
                    )
            );
        }
    }
}
