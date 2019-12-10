package org.openublpe.xmlbuilder.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helger.ubl21.UBL21Reader;
import com.helger.ublpe.UBLPEReader;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import oasis.names.specification.ubl.schema.xsd.creditnote_21.CreditNoteType;
import oasis.names.specification.ubl.schema.xsd.debitnote_21.DebitNoteType;
import oasis.names.specification.ubl.schema.xsd.invoice_21.InvoiceType;
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
import org.openublpe.xmlbuilder.rules.utils.XMLSigner;
import org.openublpe.xmlbuilder.rules.utils.XMLUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import sunat.names.specification.ubl.peru.schema.xsd.voideddocuments_1.VoidedDocumentsType;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import static io.restassured.RestAssured.given;
import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class DocumentsResourceTest extends AbstractDocumentsCertResourceTest {

    @BeforeAll
    public static void beforeAll() throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException {
        AbstractDocumentsCertResourceTest.loadCertificate();
        AbstractDocumentsCertResourceTest.loadInputGenerators();

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
                    assertMessageError("XML Snapshot does not match", input, responseBody.asString()),
                    detailedDiff,
                    true
            );
        }
    }

    @Test
    public void testCreateInvoice() throws Exception {
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

            // read document
            Document xmlDocument = XMLUtils.inputStreamToDocument(responseBody.asInputStream());
            assertNotNull(xmlDocument, assertMessageError(input, "Response.body to Document should not be null"));

            // Sign document
            Document xmlSignedDocument = XMLSigner.firmarXML(xmlDocument, SIGN_REFERENCE_ID, CERTIFICATE.getX509Certificate(), CERTIFICATE.getPrivateKey());

            // Validate valid XML
            InvoiceType invoiceType = UBL21Reader.invoice().read(xmlSignedDocument);
            assertNotNull(invoiceType, assertMessageError(input, "InvoiceType is no valid", xmlSignedDocument));
        }
    }

    @Test
    public void testCreateCreditNote() throws Exception {
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

            // read document
            Document xmlDocument = XMLUtils.inputStreamToDocument(response.getBody().asInputStream());
            assertNotNull(xmlDocument, assertMessageError(input, "Response.body to Document should not be null"));

            // Sign document
            Document xmlSignedDocument = XMLSigner.firmarXML(xmlDocument, SIGN_REFERENCE_ID, CERTIFICATE.getX509Certificate(), CERTIFICATE.getPrivateKey());

            // Validate valid XML
            CreditNoteType creditNoteType = UBL21Reader.creditNote().read(xmlSignedDocument);
            assertNotNull(creditNoteType, assertMessageError(input, "CreditNoteType is no valid", xmlSignedDocument));
        }
    }

    @Test
    public void testCreateDebitNote() throws Exception {
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

            // read document
            Document xmlDocument = XMLUtils.inputStreamToDocument(responseBody.asInputStream());
            assertNotNull(xmlDocument, assertMessageError(input, "Response.body to Document should not be null"));

            // Sign document
            Document xmlSignedDocument = XMLSigner.firmarXML(xmlDocument, SIGN_REFERENCE_ID, CERTIFICATE.getX509Certificate(), CERTIFICATE.getPrivateKey());

            // Validate valid XML
            DebitNoteType debitNoteType = UBL21Reader.debitNote().read(xmlSignedDocument);
            assertNotNull(debitNoteType, assertMessageError(input, "DebitNoteType is no valid", xmlSignedDocument));
        }
    }

    @Test
    public void testCreateVoidedDocument() throws Exception {
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

            // read document
            Document xmlDocument = XMLUtils.inputStreamToDocument(responseBody.asInputStream());
            assertNotNull(xmlDocument, assertMessageError(input, "Response.body to Document should not be null"));

            // Sign document
            Document xmlSignedDocument = XMLSigner.firmarXML(xmlDocument, SIGN_REFERENCE_ID, CERTIFICATE.getX509Certificate(), CERTIFICATE.getPrivateKey());

            // Validate valid XML
            VoidedDocumentsType voidedDocumentsType = UBLPEReader.voidedDocuments().read(xmlSignedDocument);
            assertNotNull(voidedDocumentsType, assertMessageError(input, "VoidedDocumentsType is no valid", xmlSignedDocument));
        }
    }

    @Test
    public void testCreateSummaryDocument() throws Exception {
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

            // read document
            Document xmlDocument = XMLUtils.inputStreamToDocument(responseBody.asInputStream());
            assertNotNull(xmlDocument, assertMessageError(input, "Response.body to Document should not be null"));

            // Sign document
            Document xmlSignedDocument = XMLSigner.firmarXML(xmlDocument, SIGN_REFERENCE_ID, CERTIFICATE.getX509Certificate(), CERTIFICATE.getPrivateKey());

            // Validate valid XML
//            SummaryDocumentsType summaryDocumentsType = UBLPEReader.summaryDocuments().read(xmlSignedDocument);
//            assertNotNull(summaryDocumentsType, assertMessageError(input, "SummaryDocumentsType is no valid", xmlSignedDocument));
        }
    }
}
