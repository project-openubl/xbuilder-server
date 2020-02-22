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
import org.openublpe.xmlbuilder.apicore.resources.ApiApplication;
import org.openublpe.xmlbuilder.core.models.input.standard.despatchadvice.DespatchAdviceInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.note.creditNote.CreditNoteInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.note.debitNote.DebitNoteInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.PerceptionInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.RetentionInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.SummaryDocumentInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.VoidedDocumentInputModel;
import org.openublpe.xmlbuilder.inputdata.AbstractInputDataTest;
import org.openublpe.xmlbuilder.inputdata.generator.InputGenerator;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

import static io.restassured.RestAssured.given;
import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
                    messageInputDataError(input, responseBody.asString(), "XML Snapshot does not match"),
                    detailedDiff,
                    true
            );
        }
    }

    @Test
    void testCreateInvoice() throws Exception {
        assertEquals(InputGenerator.NUMBER_TEST_INVOICES, INVOICES.size(), "The number of test cases is not the expected one");

        for (InvoiceInputModel input : INVOICES) {
            // GIVEN
            String body = new ObjectMapper().writeValueAsString(input);

            // WHEN
            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post(ApiApplication.API_BASE + "/documents/invoice/create")
                    .thenReturn();

            // THEN
            assertEquals(200, response.getStatusCode(), messageInputDataError(input, response.getBody().asString()));
            ResponseBody responseBody = response.getBody();

            // snapshot
            assertSnapshot(input, responseBody);
        }
    }

    @Test
    void testCreateCreditNote() throws Exception {
        assertEquals(InputGenerator.NUMBER_TEST_CREDIT_NOTES, CREDIT_NOTES.size(), "The number of test cases is not the expected one");

        for (CreditNoteInputModel input : CREDIT_NOTES) {
            // Given
            String body = new ObjectMapper().writeValueAsString(input);

            // When
            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post(ApiApplication.API_BASE + "/documents/credit-note/create")
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
        assertEquals(InputGenerator.NUMBER_TEST_DEBIT_NOTES, DEBIT_NOTES.size(), "The number of test cases is not the expected one");

        for (DebitNoteInputModel input : DEBIT_NOTES) {
            // Given
            String body = new ObjectMapper().writeValueAsString(input);

            // Then
            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post(ApiApplication.API_BASE + "/documents/debit-note/create")
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
        assertEquals(InputGenerator.NUMBER_TEST_VOIDED_DOCUMENTS, VOIDED_DOCUMENTS.size(), "The number of test cases is not the expected one");

        for (VoidedDocumentInputModel input : VOIDED_DOCUMENTS) {
            // GIVEN
            String body = new ObjectMapper().writeValueAsString(input);

            // THEN
            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post(ApiApplication.API_BASE + "/documents/voided-document/create")
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
        assertEquals(InputGenerator.NUMBER_TEST_SUMMARY_DOCUMENTS, SUMMARY_DOCUMENTS.size(), "The number of test cases is not the expected one");

        for (SummaryDocumentInputModel input : SUMMARY_DOCUMENTS) {
            // GIVEN
            String body = new ObjectMapper().writeValueAsString(input);

            // THEN
            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post(ApiApplication.API_BASE + "/documents/summary-document/create")
                    .thenReturn();

            // THEN
            assertEquals(200, response.getStatusCode(), response.getBody().asString());
            ResponseBody responseBody = response.getBody();

            // snapshot
            assertSnapshot(input, responseBody);
        }
    }

    @Test
    void testPerception() throws Exception {
        assertEquals(InputGenerator.NUMBER_TEST_PERCEPTIONS, PERCEPTION_DOCUMENTS.size(), "The number of test cases is not the expected one");

        for (PerceptionInputModel input : PERCEPTION_DOCUMENTS) {
            // GIVEN
            String body = new ObjectMapper().writeValueAsString(input);

            // THEN
            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post(ApiApplication.API_BASE + "/documents/perception/create")
                    .thenReturn();

            // THEN
            assertEquals(200, response.getStatusCode(), response.getBody().asString());
            ResponseBody responseBody = response.getBody();

            // snapshot
            assertSnapshot(input, responseBody);
        }
    }

    @Test
    void testRetention() throws Exception {
        assertEquals(InputGenerator.NUMBER_TEST_RETENTIONS, RETENTION_DOCUMENTS.size(), "The number of test cases is not the expected one");

        for (RetentionInputModel input : RETENTION_DOCUMENTS) {
            // GIVEN
            String body = new ObjectMapper().writeValueAsString(input);

            // THEN
            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post(ApiApplication.API_BASE + "/documents/retention/create")
                    .thenReturn();

            // THEN
            assertEquals(200, response.getStatusCode(), response.getBody().asString());
            ResponseBody responseBody = response.getBody();

            // snapshot
            assertSnapshot(input, responseBody);
        }
    }

    @Test
    void testDespatchAdvice() throws Exception {
        assertEquals(InputGenerator.NUMBER_TEST_DESPATCH_ADVICES, DESPATCH_ADVICE_DOCUMENTS.size(), "The number of test cases is not the expected one");

        for (DespatchAdviceInputModel input : DESPATCH_ADVICE_DOCUMENTS) {
            // GIVEN
            String body = new ObjectMapper().writeValueAsString(input);

            // THEN
            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post(ApiApplication.API_BASE + "/documents/despatch-advice/create")
                    .thenReturn();

            // THEN
            assertEquals(200, response.getStatusCode(), response.getBody().asString());
            ResponseBody responseBody = response.getBody();

            // snapshot
            assertSnapshot(input, responseBody);
        }
    }
}
