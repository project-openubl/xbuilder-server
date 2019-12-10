package org.openublpe.xmlbuilder.api.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.custommonkey.xmlunit.examples.RecursiveElementNameAndTextQualifier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openublpe.xmlbuilder.core.models.input.standard.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.note.creditNote.CreditNoteInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.note.debitNote.DebitNoteInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.SummaryDocumentInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.VoidedDocumentInputModel;
import org.openublpe.xmlbuilder.inputdata.AbstractInputDataTest;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

import static io.restassured.RestAssured.given;
import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@QuarkusTest
public class DocumentsResourceTest extends AbstractInputDataTest {

    @BeforeAll
    public static void beforeAll() {
        //ignore while space differances
        XMLUnit.setIgnoreWhitespace(true);

        //ignore attribute order
        XMLUnit.setIgnoreAttributeOrder(true);

        //ignore comment differances
        XMLUnit.setIgnoreComments(true);

        //ignore differance on CData and text
        XMLUnit.setIgnoreDiffBetweenTextAndCDATA(true);
    }

    public void assertSnapshot(Object input, ResponseBody responseBody) throws IOException, SAXException {
        if (SNAPSHOTS.get(input).isPresent()) {
            String url = SNAPSHOTS.get(input).get();

            InputStream currentIS = responseBody.asInputStream();
            InputStream expectedIS = Thread.currentThread().getContextClassLoader().getResourceAsStream(url);

            InputSource expected = new InputSource(expectedIS);
            InputSource current = new InputSource(currentIS);

            DetailedDiff detailedDiff = new DetailedDiff(new Diff(expected, current));

            //ignore the sorting mismatch issues
            detailedDiff.overrideElementQualifier(new RecursiveElementNameAndTextQualifier());

            //this can use ignore soarting issues and assert
            assertXMLEqual(
                    assertMessageError(input, responseBody.asString(), "XML Snapshot does not match"),
                    detailedDiff,
                    true
            );
        }
    }

    @Test
    void testCreateInvoice() throws Exception {
        assertFalse(INVOICES.isEmpty(), "No invoices to test");

        for (InvoiceInputModel input : INVOICES) {
            // GIVEN
            String body = new ObjectMapper().writeValueAsString(input);

            // WHEN
            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post("/documents/invoice/create")
                    .thenReturn();

            // THEN
            assertEquals(200, response.getStatusCode(), assertMessageError(input, response.getBody().asString()));
            ResponseBody responseBody = response.getBody();

            // snapshot
            assertSnapshot(input, responseBody);
        }
    }

    @Test
    void testCreateCreditNote() throws Exception {
        assertFalse(CREDIT_NOTES.isEmpty(), "No credit notes to test");

        for (CreditNoteInputModel input : CREDIT_NOTES) {
            // Given
            String body = new ObjectMapper().writeValueAsString(input);

            // When
            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post("/documents/credit-note/create")
                    .thenReturn();

            // Then
            assertEquals(200, response.getStatusCode(), response.getBody().asString());
            ResponseBody responseBody = response.getBody();

            // snapshot
            assertSnapshot(input, responseBody);
        }
    }

    @Test
    void testCreateDebitNote() throws Exception {
        assertFalse(DEBIT_NOTES.isEmpty(), "No debit notes to test");
        for (DebitNoteInputModel input : DEBIT_NOTES) {
            // Given
            String body = new ObjectMapper().writeValueAsString(input);

            // Then
            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post("/documents/debit-note/create")
                    .thenReturn();

            // Then
            assertEquals(200, response.getStatusCode(), response.getBody().asString());
            ResponseBody responseBody = response.getBody();

            // snapshot
            assertSnapshot(input, responseBody);
        }
    }

    @Test
    void testCreateVoidedDocument() throws Exception {
        assertFalse(VOIDED_DOCUMENTS.isEmpty(), "No voided documents to test");

        for (VoidedDocumentInputModel input : VOIDED_DOCUMENTS) {
            // GIVEN
            String body = new ObjectMapper().writeValueAsString(input);

            // THEN
            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post("/documents/voided-document/create")
                    .thenReturn();

            // THEN
            assertEquals(200, response.getStatusCode(), response.getBody().asString());
            ResponseBody responseBody = response.getBody();

            // snapshot
            assertSnapshot(input, responseBody);
        }
    }

    @Test
    void testCreateSummaryDocument() throws Exception {
        assertFalse(SUMMARY_DOCUMENTS.isEmpty(), "No summary documents to test");
        for (SummaryDocumentInputModel input : SUMMARY_DOCUMENTS) {
            // GIVEN
            String body = new ObjectMapper().writeValueAsString(input);

            // THEN
            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post("/documents/summary-document/create")
                    .thenReturn();

            // THEN
            assertEquals(200, response.getStatusCode(), response.getBody().asString());
            ResponseBody responseBody = response.getBody();

            // snapshot
            assertSnapshot(input, responseBody);
        }
    }
}
