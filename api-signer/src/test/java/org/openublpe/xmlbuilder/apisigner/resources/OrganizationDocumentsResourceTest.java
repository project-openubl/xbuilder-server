package org.openublpe.xmlbuilder.apisigner.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helger.ubl21.UBL21Reader;
import com.helger.ublpe.UBLPEReader;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import oasis.names.specification.ubl.schema.xsd.creditnote_21.CreditNoteType;
import oasis.names.specification.ubl.schema.xsd.debitnote_21.DebitNoteType;
import oasis.names.specification.ubl.schema.xsd.invoice_21.InvoiceType;
import org.custommonkey.xmlunit.XMLUnit;
import org.custommonkey.xmlunit.exceptions.XpathException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openublpe.xmlbuilder.core.models.input.standard.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.note.creditNote.CreditNoteInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.note.debitNote.DebitNoteInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.SummaryDocumentInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.VoidedDocumentInputModel;
import org.openublpe.xmlbuilder.inputdata.AbstractInputDataTest;
import org.xml.sax.SAXException;
import sunat.names.specification.ubl.peru.schema.xsd.voideddocuments_1.VoidedDocumentsType;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.custommonkey.xmlunit.XMLAssert.assertXpathExists;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
class OrganizationDocumentsResourceTest extends AbstractInputDataTest {

    static final String SIGNATURE_XPATH = "//ext:UBLExtensions/ext:UBLExtension/ext:ExtensionContent/ds:Signature";
    static final String ORGANIZATION_ID = "master";

    @BeforeAll
    public static void beforeAll() {
        XMLUnit.setXpathNamespaceContext(new UBLNamespaces());
    }

    void assertSignatureExists(String xml) throws SAXException, IOException, XpathException {
        assertXpathExists(SIGNATURE_XPATH, xml);
    }

    @Test
    void createInvoiceXml() throws IOException, SAXException, XpathException {
        assertFalse(INVOICES.isEmpty(), "no inputs to test");

        for (InvoiceInputModel input : INVOICES) {
            // GIVEN
            String body = new ObjectMapper().writeValueAsString(input);

            // WHEN
            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post("/organizations/" + ORGANIZATION_ID + "/documents/invoice/create")
                    .thenReturn();

            // THEN
            assertEquals(200, response.getStatusCode(), assertMessageError(input, response.getBody().asString()));
            ResponseBody responseBody = response.getBody();

            assertSignatureExists(responseBody.asString());

            // Validate valid XML
            InvoiceType invoiceType = UBL21Reader.invoice().read(responseBody.asInputStream());
            assertNotNull(invoiceType, assertMessageError(input, responseBody.asString(), "InvoiceType is no valid"));
        }
    }

    @Test
    void createCreditNoteXml() throws IOException, SAXException, XpathException {
        assertFalse(CREDIT_NOTES.isEmpty(), "no inputs to test");

        for (CreditNoteInputModel input : CREDIT_NOTES) {
            // GIVEN
            String body = new ObjectMapper().writeValueAsString(input);

            // WHEN
            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post("/organizations/" + ORGANIZATION_ID + "/documents/credit-note/create")
                    .thenReturn();

            // THEN
            assertEquals(200, response.getStatusCode(), assertMessageError(input, response.getBody().asString()));
            ResponseBody responseBody = response.getBody();

            assertSignatureExists(responseBody.asString());

            // Validate valid XML
            CreditNoteType creditNoteType = UBL21Reader.creditNote().read(responseBody.asInputStream());
            assertNotNull(creditNoteType, assertMessageError(input, responseBody.asString(), "CreditNoteType is no valid"));
        }
    }

    @Test
    void createDebitNoteXml() throws IOException, SAXException, XpathException {
        assertFalse(DEBIT_NOTES.isEmpty(), "no inputs to test");

        for (DebitNoteInputModel input : DEBIT_NOTES) {
            // GIVEN
            String body = new ObjectMapper().writeValueAsString(input);

            // WHEN
            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post("/organizations/" + ORGANIZATION_ID + "/documents/debit-note/create")
                    .thenReturn();

            // THEN
            assertEquals(200, response.getStatusCode(), assertMessageError(input, response.getBody().asString()));
            ResponseBody responseBody = response.getBody();

            assertSignatureExists(responseBody.asString());

            DebitNoteType debitNoteType = UBL21Reader.debitNote().read(responseBody.asInputStream());
            assertNotNull(debitNoteType, assertMessageError(input, responseBody.asString(), "DebitNoteType is no valid"));
        }
    }

    @Test
    void createVoidedDocumentXml() throws IOException, SAXException, XpathException {
        assertFalse(VOIDED_DOCUMENTS.isEmpty(), "no inputs to test");

        for (VoidedDocumentInputModel input : VOIDED_DOCUMENTS) {
            // GIVEN
            String body = new ObjectMapper().writeValueAsString(input);

            // WHEN
            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post("/organizations/" + ORGANIZATION_ID + "/documents/voided-document/create")
                    .thenReturn();

            // THEN
            assertEquals(200, response.getStatusCode(), assertMessageError(input, response.getBody().asString()));
            ResponseBody responseBody = response.getBody();

            assertSignatureExists(responseBody.asString());

            // Validate valid XML
            VoidedDocumentsType voidedDocumentsType = UBLPEReader.voidedDocuments().read(responseBody.asInputStream());
            assertNotNull(voidedDocumentsType, assertMessageError(input, responseBody.asString(), "VoidedDocumentsType is no valid"));
        }
    }

    @Test
    void createSummaryDocumentXml() throws IOException, SAXException, XpathException {
        assertFalse(SUMMARY_DOCUMENTS.isEmpty(), "no inputs to test");

        for (SummaryDocumentInputModel input : SUMMARY_DOCUMENTS) {
            // GIVEN
            String body = new ObjectMapper().writeValueAsString(input);

            // WHEN
            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post("/organizations/" + ORGANIZATION_ID + "/documents/summary-document/create")
                    .thenReturn();

            // THEN
            assertEquals(200, response.getStatusCode(), assertMessageError(input, response.getBody().asString()));
            ResponseBody responseBody = response.getBody();

            assertSignatureExists(responseBody.asString());

            // Validate valid XML
//            SummaryDocumentsType summaryDocumentsType = UBLPEReader.summaryDocuments().read(xmlSignedDocument);
//            assertNotNull(summaryDocumentsType, assertMessageError(input, "SummaryDocumentsType is no valid", xmlSignedDocument));
        }
    }
}
