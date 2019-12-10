package org.openublpe.xmlbuilder.signer.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.custommonkey.xmlunit.XMLUnit;
import org.custommonkey.xmlunit.exceptions.XpathException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openublpe.xmlbuilder.models.input.standard.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.models.input.standard.note.creditNote.CreditNoteInputModel;
import org.openublpe.xmlbuilder.models.input.standard.note.debitNote.DebitNoteInputModel;
import org.openublpe.xmlbuilder.models.input.sunat.SummaryDocumentInputModel;
import org.openublpe.xmlbuilder.models.input.sunat.VoidedDocumentInputModel;
import org.xml.sax.SAXException;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.custommonkey.xmlunit.XMLAssert.assertXpathExists;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
class OrganizationDocumentsResourceTest {

    static final String SIGNATURE_XPATH = "//ext:UBLExtensions/ext:UBLExtension/ext:ExtensionContent/ds:Signature";

    @BeforeAll
    public static void beforeAll() {
        XMLUnit.setXpathNamespaceContext(new UBLNamespaces());
    }

    @Test
    void createInvoiceXml() throws IOException, SAXException, XpathException {
        // GIVEN
        String organizationId = "master";

        InvoiceInputModel input = InputGenerator.buildInvoiceInputModel();
        String body = new ObjectMapper().writeValueAsString(input);

        // WHEN
        Response response = given()
                .body(body)
                .header("Content-Type", "application/json")
                .when()
                .post("/organizations/" + organizationId + "/documents/invoice/create")
                .thenReturn();

        // THEN
        assertEquals(200, response.getStatusCode());
        ResponseBody responseBody = response.getBody();

        String xml = responseBody.asString();
        assertXpathExists(SIGNATURE_XPATH, xml);
    }

    @Test
    void createCreditNoteXml() throws IOException, SAXException, XpathException {
        // GIVEN
        String organizationId = "master";

        CreditNoteInputModel input = InputGenerator.buildCreditNoteInputModel();
        String body = new ObjectMapper().writeValueAsString(input);

        // WHEN
        Response response = given()
                .body(body)
                .header("Content-Type", "application/json")
                .when()
                .post("/organizations/" + organizationId + "/documents/credit-note/create")
                .thenReturn();

        // THEN
        assertEquals(200, response.getStatusCode());
        ResponseBody responseBody = response.getBody();

        String xml = responseBody.asString();
        assertXpathExists(SIGNATURE_XPATH, xml);
    }

    @Test
    void createDebitNoteXml() throws IOException, SAXException, XpathException {
        // GIVEN
        String organizationId = "master";

        DebitNoteInputModel input = InputGenerator.buildDebitNoteInputModel();
        String body = new ObjectMapper().writeValueAsString(input);

        // WHEN
        Response response = given()
                .body(body)
                .header("Content-Type", "application/json")
                .when()
                .post("/organizations/" + organizationId + "/documents/debit-note/create")
                .thenReturn();

        // THEN
        assertEquals(200, response.getStatusCode());
        ResponseBody responseBody = response.getBody();

        String xml = responseBody.asString();
        assertXpathExists(SIGNATURE_XPATH, xml);
    }

    @Test
    void createVoidedDocumentXml() throws IOException, SAXException, XpathException {
        // GIVEN
        String organizationId = "master";

        VoidedDocumentInputModel input = InputGenerator.buildVoidedDocumentInputModel();
        String body = new ObjectMapper().writeValueAsString(input);

        // WHEN
        Response response = given()
                .body(body)
                .header("Content-Type", "application/json")
                .when()
                .post("/organizations/" + organizationId + "/documents/voided-document/create")
                .thenReturn();

        // THEN
        assertEquals(200, response.getStatusCode());
        ResponseBody responseBody = response.getBody();

        String xml = responseBody.asString();
        assertXpathExists(SIGNATURE_XPATH, xml);
    }

    @Test
    void createSummaryDocumentXml() throws IOException, SAXException, XpathException {
        // GIVEN
        String organizationId = "master";

        SummaryDocumentInputModel input = InputGenerator.buildSummaryDocumentInputModel();
        String body = new ObjectMapper().writeValueAsString(input);

        // WHEN
        Response response = given()
                .body(body)
                .header("Content-Type", "application/json")
                .when()
                .post("/organizations/" + organizationId + "/documents/summary-document/create")
                .thenReturn();

        // THEN
        assertEquals(200, response.getStatusCode());
        ResponseBody responseBody = response.getBody();

        String xml = responseBody.asString();
        assertXpathExists(SIGNATURE_XPATH, xml);
    }
}
