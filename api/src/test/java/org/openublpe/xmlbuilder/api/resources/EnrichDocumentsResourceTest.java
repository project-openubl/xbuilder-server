package org.openublpe.xmlbuilder.api.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
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

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class EnrichDocumentsResourceTest extends AbstractInputDataTest {

    @Inject
    Validator validator;

    @Test
    void testEnrichInvoice() throws Exception {
        assertFalse(INVOICES.isEmpty(), "No inputs to test");

        for (InvoiceInputModel input : INVOICES) {
            // GIVEN
            String body = new ObjectMapper().writeValueAsString(input);

            // WHEN
            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post("/documents/invoice/enrich")
                    .thenReturn();

            // THEN
            assertEquals(200, response.getStatusCode(), messageInputDataError(input, response.getBody().asString()));
            ResponseBody responseBody = response.getBody();

            InvoiceOutputModel output = new ObjectMapper().readValue(responseBody.asInputStream(), InvoiceOutputModel.class);
            assertNotNull(output, messageInputDataError(input, "Invalid output"));

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
    void testEnrichCreditNote() throws Exception {
        assertFalse(CREDIT_NOTES.isEmpty(), "No inputs to test");

        for (CreditNoteInputModel input : CREDIT_NOTES) {
            // GIVEN
            String body = new ObjectMapper().writeValueAsString(input);

            // WHEN
            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post("/documents/credit-note/enrich")
                    .thenReturn();

            // THEN
            assertEquals(200, response.getStatusCode(), messageInputDataError(input, response.getBody().asString()));
            ResponseBody responseBody = response.getBody();

            CreditNoteOutputModel output = new ObjectMapper().readValue(responseBody.asInputStream(), CreditNoteOutputModel.class);
            assertNotNull(output, messageInputDataError(input, "Invalid output"));

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
    void testEnrichDebitNote() throws Exception {
        assertFalse(DEBIT_NOTES.isEmpty(), "No inputs to test");

        for (DebitNoteInputModel input : DEBIT_NOTES) {
            // GIVEN
            String body = new ObjectMapper().writeValueAsString(input);

            // WHEN
            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post("/documents/debit-note/enrich")
                    .thenReturn();

            // THEN
            assertEquals(200, response.getStatusCode(), messageInputDataError(input, response.getBody().asString()));
            ResponseBody responseBody = response.getBody();

            DebitNoteOutputModel output = new ObjectMapper().readValue(responseBody.asInputStream(), DebitNoteOutputModel.class);
            assertNotNull(output, messageInputDataError(input, "Invalid output"));

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
    void testEnrichVoidedDocument() throws Exception {
        assertFalse(VOIDED_DOCUMENTS.isEmpty(), "No inputs to test");

        for (VoidedDocumentInputModel input : VOIDED_DOCUMENTS) {
            // GIVEN
            String body = new ObjectMapper().writeValueAsString(input);

            // WHEN
            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post("/documents/voided-document/enrich")
                    .thenReturn();

            // THEN
            assertEquals(200, response.getStatusCode(), messageInputDataError(input, response.getBody().asString()));
            ResponseBody responseBody = response.getBody();

            VoidedDocumentOutputModel output = new ObjectMapper().readValue(responseBody.asInputStream(), VoidedDocumentOutputModel.class);
            assertNotNull(output, messageInputDataError(input, "Invalid output"));

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
    void testEnrichSummaryDocument() throws Exception {
        assertFalse(SUMMARY_DOCUMENTS.isEmpty(), "No inputs to test");

        for (SummaryDocumentInputModel input : SUMMARY_DOCUMENTS) {
            // GIVEN
            String body = new ObjectMapper().writeValueAsString(input);

            // WHEN
            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post("/documents/summary-document/enrich")
                    .thenReturn();

            // THEN
            assertEquals(200, response.getStatusCode(), messageInputDataError(input, response.getBody().asString()));
            ResponseBody responseBody = response.getBody();

            SummaryDocumentOutputModel output = new ObjectMapper().readValue(responseBody.asInputStream(), SummaryDocumentOutputModel.class);
            assertNotNull(output, messageInputDataError(input, "Invalid output"));

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
