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
import org.openublpe.xmlbuilder.apicore.resources.ApiApplication;
import org.openublpe.xmlbuilder.core.models.input.standard.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.note.creditNote.CreditNoteInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.note.debitNote.DebitNoteInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.PerceptionInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.RetentionInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.SummaryDocumentInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.VoidedDocumentInputModel;
import org.openublpe.xmlbuilder.core.models.output.sunat.RetentionOutputModel;
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

    static final String ORGANIZATIONS_URL = ApiApplication.API_BASE + "/organizations";
    static final String ORGANIZATION_ID = "master";

    static final String SIGNATURE_XPATH = "//ext:UBLExtensions/ext:UBLExtension/ext:ExtensionContent/ds:Signature";

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
                    .post(ORGANIZATIONS_URL + "/" + ORGANIZATION_ID + "/documents/invoice/create")
                    .thenReturn();

            // THEN
            assertEquals(200, response.getStatusCode(), messageInputDataError(input, response.getBody().asString()));
            ResponseBody responseBody = response.getBody();

            assertSignatureExists(responseBody.asString());

            // Validate valid XML
            InvoiceType invoiceType = UBL21Reader.invoice().read(responseBody.asInputStream());
            assertNotNull(invoiceType, messageInputDataError(input, responseBody.asString(), "InvoiceType is no valid"));
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
                    .post(ORGANIZATIONS_URL + "/" + ORGANIZATION_ID + "/documents/credit-note/create")
                    .thenReturn();

            // THEN
            assertEquals(200, response.getStatusCode(), messageInputDataError(input, response.getBody().asString()));
            ResponseBody responseBody = response.getBody();

            assertSignatureExists(responseBody.asString());

            // Validate valid XML
            CreditNoteType creditNoteType = UBL21Reader.creditNote().read(responseBody.asInputStream());
            assertNotNull(creditNoteType, messageInputDataError(input, responseBody.asString(), "CreditNoteType is no valid"));
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
                    .post(ORGANIZATIONS_URL + "/" + ORGANIZATION_ID + "/documents/debit-note/create")
                    .thenReturn();

            // THEN
            assertEquals(200, response.getStatusCode(), messageInputDataError(input, response.getBody().asString()));
            ResponseBody responseBody = response.getBody();

            assertSignatureExists(responseBody.asString());

            DebitNoteType debitNoteType = UBL21Reader.debitNote().read(responseBody.asInputStream());
            assertNotNull(debitNoteType, messageInputDataError(input, responseBody.asString(), "DebitNoteType is no valid"));
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
                    .post(ORGANIZATIONS_URL + "/" + ORGANIZATION_ID + "/documents/voided-document/create")
                    .thenReturn();

            // THEN
            assertEquals(200, response.getStatusCode(), messageInputDataError(input, response.getBody().asString()));
            ResponseBody responseBody = response.getBody();

            assertSignatureExists(responseBody.asString());

            // Validate valid XML
            VoidedDocumentsType voidedDocumentsType = UBLPEReader.voidedDocuments().read(responseBody.asInputStream());
            assertNotNull(voidedDocumentsType, messageInputDataError(input, responseBody.asString(), "VoidedDocumentsType is no valid"));
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
                    .post(ORGANIZATIONS_URL + "/" + ORGANIZATION_ID + "/documents/summary-document/create")
                    .thenReturn();

            // THEN
            assertEquals(200, response.getStatusCode(), messageInputDataError(input, response.getBody().asString()));
            ResponseBody responseBody = response.getBody();

            assertSignatureExists(responseBody.asString());

            // Validate valid XML
//            SummaryDocumentsType summaryDocumentsType = UBLPEReader.summaryDocuments().read(xmlSignedDocument);
//            assertNotNull(summaryDocumentsType, assertMessageError(input, "SummaryDocumentsType is no valid", xmlSignedDocument));
        }
    }

    @Test
    void createPerceptionXml() throws IOException, SAXException, XpathException {
        assertFalse(PERCEPTION_DOCUMENTS.isEmpty(), "no inputs to test");

        for (PerceptionInputModel input : PERCEPTION_DOCUMENTS) {
            // GIVEN
            String body = new ObjectMapper().writeValueAsString(input);

            // WHEN
            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post(ORGANIZATIONS_URL + "/" + ORGANIZATION_ID + "/documents/perception/create")
                    .thenReturn();

            // THEN
            assertEquals(200, response.getStatusCode(), messageInputDataError(input, response.getBody().asString()));
            ResponseBody responseBody = response.getBody();

            assertSignatureExists(responseBody.asString());

            // Validate valid XML
//            SummaryDocumentsType summaryDocumentsType = UBLPEReader.summaryDocuments().read(xmlSignedDocument);
//            assertNotNull(summaryDocumentsType, assertMessageError(input, "SummaryDocumentsType is no valid", xmlSignedDocument));
        }
    }

    @Test
    void createRetentionXml() throws IOException, SAXException, XpathException {
        assertFalse(RETENTION_DOCUMENTS.isEmpty(), "no inputs to test");

        for (RetentionInputModel input : RETENTION_DOCUMENTS) {
            // GIVEN
            String body = new ObjectMapper().writeValueAsString(input);

            // WHEN
            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post(ORGANIZATIONS_URL + "/" + ORGANIZATION_ID + "/documents/retention/create")
                    .thenReturn();

            // THEN
            assertEquals(200, response.getStatusCode(), messageInputDataError(input, response.getBody().asString()));
            ResponseBody responseBody = response.getBody();

            assertSignatureExists(responseBody.asString());

            // Validate valid XML
//            SummaryDocumentsType summaryDocumentsType = UBLPEReader.summaryDocuments().read(xmlSignedDocument);
//            assertNotNull(summaryDocumentsType, assertMessageError(input, "SummaryDocumentsType is no valid", xmlSignedDocument));
        }
    }
}
