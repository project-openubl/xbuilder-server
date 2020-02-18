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
import io.github.carlosthe19916.webservices.managers.BillServiceManager;
import io.github.carlosthe19916.webservices.providers.BillServiceModel;
import io.github.carlosthe19916.webservices.wrappers.ServiceConfig;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.junit.jupiter.api.Test;
import org.openublpe.xmlbuilder.apicore.resources.ApiApplication;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog1;
import org.openublpe.xmlbuilder.core.models.input.standard.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.note.creditNote.CreditNoteInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.note.debitNote.DebitNoteInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.SummaryDocumentInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.VoidedDocumentInputModel;
import org.openublpe.xmlbuilder.inputdata.AbstractInputDataTest;
import org.openublpe.xmlbuilder.inputdata.generator.InputGenerator;
import org.openublpe.xmlbuilder.rules.utils.DateUtils;
import org.openublpe.xmlbuilder.rules.utils.UBLUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class OrganizationDocumentsResourceITSunat extends AbstractInputDataTest {

    static final String ORGANIZATIONS_URL = ApiApplication.API_BASE + "/organizations";
    static final String ORGANIZATION_ID = "master";

    static final String SUNAT_BETA_URL = "https://e-beta.sunat.gob.pe/ol-ti-itcpfegem-beta/billService";
    static final String SUNAT_BETA_USERNAME = "MODDATOS";
    static final String SUNAT_BETA_PASSWORD = "MODDATOS";

    static Document inputStreamToDocument(InputStream in) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(in));
    }

    static byte[] documentToBytes(Document document) throws TransformerException {
        TransformerFactory transFactory = TransformerFactory.newInstance();
        Transformer transformer = transFactory.newTransformer();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        StreamResult result = new StreamResult(bos);
        transformer.transform(new DOMSource(document), result);
        return bos.toByteArray();
    }

    static String getInvoiceFileName(String ruc, String serie, Integer numero) {
        Catalog1 catalog1;
        if (UBLUtils.FACTURA_SERIE_REGEX.matcher(serie.toUpperCase()).find()) {
            catalog1 = Catalog1.FACTURA;
        } else if (UBLUtils.BOLETA_SERIE_REGEX.matcher(serie.toUpperCase()).find()) {
            catalog1 = Catalog1.BOLETA;
        } else {
            throw new IllegalStateException("Invalid Serie, can not detect code");
        }

        return new StringBuilder()
                .append(ruc).append("-")
                .append(catalog1.getCode()).append("-")
                .append(serie.toUpperCase()).append("-")
                .append(numero)
                .toString();
    }

    static String getVoidedDocumentFileName(String ruc, Long fechaEmision, Integer numero) {
        return ruc + "-RA-" + DateUtils.toGregorianCalendarDate(fechaEmision).replaceAll("-", "") + "-" + numero;
    }

    static String getSummaryDocumentFileName(String ruc, Long fechaEmision, Integer numero) {
        return ruc + "-RC-" + DateUtils.toGregorianCalendarDate(fechaEmision).replaceAll("-", "") + "-" + numero;
    }

    static String getNotaCredito(String ruc, String serie, Integer numero) {
        Catalog1 catalog1 = Catalog1.NOTA_CREDITO;
        return new StringBuilder()
                .append(ruc).append("-")
                .append(catalog1.getCode()).append("-")
                .append(serie.toUpperCase()).append("-")
                .append(numero)
                .toString();
    }

    static String getNotaDebito(String ruc, String serie, Integer numero) {
        Catalog1 catalog1 = Catalog1.NOTA_DEBITO;
        return new StringBuilder()
                .append(ruc).append("-")
                .append(catalog1.getCode()).append("-")
                .append(serie.toUpperCase()).append("-")
                .append(numero)
                .toString();
    }

    void assertSendBill(Object input, InputStream xmlInputStream, String xmlString) throws IOException, ParserConfigurationException, SAXException, TransformerException {
        Document xmlDocument = inputStreamToDocument(xmlInputStream);

        String proveedorRuc = null;
        String fileName = null;

        if (input instanceof InvoiceInputModel) {
            InvoiceInputModel invoice = (InvoiceInputModel) input;
            proveedorRuc = invoice.getProveedor().getRuc();
            String serie = invoice.getSerie();
            Integer numero = invoice.getNumero();
            fileName = getInvoiceFileName(proveedorRuc, serie, numero);
        } else if (input instanceof CreditNoteInputModel) {
            CreditNoteInputModel creditNote = (CreditNoteInputModel) input;
            proveedorRuc = creditNote.getProveedor().getRuc();
            String serie = creditNote.getSerie();
            Integer numero = creditNote.getNumero();
            fileName = getNotaCredito(proveedorRuc, serie, numero);
        } else if (input instanceof DebitNoteInputModel) {
            DebitNoteInputModel debitNote = (DebitNoteInputModel) input;
            proveedorRuc = debitNote.getProveedor().getRuc();
            String serie = debitNote.getSerie();
            Integer numero = debitNote.getNumero();
            fileName = getNotaDebito(proveedorRuc, serie, numero);
        }

        ServiceConfig config = new ServiceConfig.Builder()
                .url(SUNAT_BETA_URL)
                .username(proveedorRuc + SUNAT_BETA_USERNAME)
                .password(SUNAT_BETA_PASSWORD)
                .build();

        byte[] documentBytes = documentToBytes(xmlDocument);


        BillServiceModel billServiceModel = BillServiceManager.sendBill(fileName + ".xml", documentBytes, config);
        assertEquals(
                BillServiceModel.Status.ACEPTADO,
                billServiceModel.getStatus(),
                messageInputDataError(input, xmlString, "sunat [codigo=" + billServiceModel.getCode() + "], [descripcion=" + billServiceModel.getDescription() + "]")
        );
    }

    void assertSendSummary(Object input, InputStream xmlInputStream, String xmlString) throws IOException, TransformerException, ParserConfigurationException, SAXException {
        Document xmlDocument = inputStreamToDocument(xmlInputStream);

        String proveedorRuc = null;
        String fileName = null;

        if (input instanceof VoidedDocumentInputModel) {
            VoidedDocumentInputModel voidedDocument = (VoidedDocumentInputModel) input;
            proveedorRuc = voidedDocument.getProveedor().getRuc();
            fileName = getVoidedDocumentFileName(proveedorRuc, voidedDocument.getFechaEmision(), voidedDocument.getNumero());
        } else if (input instanceof SummaryDocumentInputModel) {
            SummaryDocumentInputModel summaryDocument = (SummaryDocumentInputModel) input;
            proveedorRuc = summaryDocument.getProveedor().getRuc();
            fileName = getSummaryDocumentFileName(proveedorRuc, summaryDocument.getFechaEmision(), summaryDocument.getNumero());
        }

        ServiceConfig config = new ServiceConfig.Builder()
                .url(SUNAT_BETA_URL)
                .username(proveedorRuc + SUNAT_BETA_USERNAME)
                .password(SUNAT_BETA_PASSWORD)
                .build();

        byte[] documentBytes = documentToBytes(xmlDocument);


        BillServiceModel billServiceModel = BillServiceManager.sendSummary(fileName + ".xml", documentBytes, config);
        assertNotNull(
                billServiceModel.getTicket(),
                messageInputDataError(input, xmlString, "sunat [codigo=" + billServiceModel.getCode() + "], [descripcion=" + billServiceModel.getDescription() + "]")
        );
    }

    @Test
    void testCreateInvoice() throws Exception {
        assertEquals(InputGenerator.NUMBER_TEST_INVOICES, INVOICES.size(), "The number of test cases is not the expected one");

        for (InvoiceInputModel input : INVOICES) {
            // Given
            String body = new ObjectMapper().writeValueAsString(input);

            // When
            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post(ORGANIZATIONS_URL + "/" + ORGANIZATION_ID + "/documents/invoice/create")
                    .thenReturn();

            // Then
            assertEquals(200, response.getStatusCode(), messageInputDataError(input, response.getBody().asString()));
            ResponseBody responseBody = response.getBody();
            assertSendBill(input, responseBody.asInputStream(), responseBody.asString());
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
                    .post(ORGANIZATIONS_URL + "/" + ORGANIZATION_ID + "/documents/credit-note/create")
                    .thenReturn();

            // Then
            assertEquals(200, response.getStatusCode(), messageInputDataError(input, response.getBody().asString()));
            ResponseBody responseBody = response.getBody();
            assertSendBill(input, responseBody.asInputStream(), responseBody.asString());
        }
    }

    @Test
    void testCreateDebitNote() throws Exception {
        assertEquals(InputGenerator.NUMBER_TEST_DEBIT_NOTES, DEBIT_NOTES.size(), "The number of test cases is not the expected one");

        for (DebitNoteInputModel input : DEBIT_NOTES) {
            // Given
            String body = new ObjectMapper().writeValueAsString(input);

            // When
            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post(ORGANIZATIONS_URL + "/" + ORGANIZATION_ID + "/documents/debit-note/create")
                    .thenReturn();

            // Then
            assertEquals(200, response.getStatusCode(), messageInputDataError(input, response.getBody().asString()));
            ResponseBody responseBody = response.getBody();
            assertSendBill(input, responseBody.asInputStream(), responseBody.asString());
        }
    }

    @Test
    void testCreateVoidedDocument() throws Exception {
        assertEquals(InputGenerator.NUMBER_TEST_VOIDED_DOCUMENTS, VOIDED_DOCUMENTS.size(), "The number of test cases is not the expected one");

        for (VoidedDocumentInputModel input : VOIDED_DOCUMENTS) {
            // Given
            String body = new ObjectMapper().writeValueAsString(input);

            // When
            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post(ORGANIZATIONS_URL + "/" + ORGANIZATION_ID + "/documents/voided-document/create")
                    .thenReturn();

            // Then
            assertEquals(200, response.getStatusCode(), messageInputDataError(input, response.getBody().asString()));
            ResponseBody responseBody = response.getBody();
            assertSendSummary(input, responseBody.asInputStream(), responseBody.asString());
        }
    }

    @Test
    void testCreateSummaryDocument() throws Exception {
        assertEquals(InputGenerator.NUMBER_TEST_SUMMARY_DOCUMENTS, SUMMARY_DOCUMENTS.size(), "The number of test cases is not the expected one");

        for (SummaryDocumentInputModel input : SUMMARY_DOCUMENTS) {
            // Given
            String body = new ObjectMapper().writeValueAsString(input);

            // When
            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post(ORGANIZATIONS_URL + "/" + ORGANIZATION_ID + "/documents/summary-document/create")
                    .thenReturn();

            // Then
            String response2 = response.getBody().asString();
            assertEquals(200, response.getStatusCode(), messageInputDataError(input, response.getBody().asString()));
            ResponseBody responseBody = response.getBody();
            assertSendSummary(input, responseBody.asInputStream(), responseBody.asString());
        }
    }
}
