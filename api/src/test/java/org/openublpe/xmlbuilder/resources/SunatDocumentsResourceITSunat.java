package org.openublpe.xmlbuilder.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.carlosthe19916.webservices.managers.BillServiceManager;
import io.github.carlosthe19916.webservices.providers.BillServiceModel;
import io.github.carlosthe19916.webservices.wrappers.ServiceConfig;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
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

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class SunatDocumentsResourceITSunat extends AbstractDocumentsCertResourceTest {

    static final String SUNAT_BETA_URL = "https://e-beta.sunat.gob.pe/ol-ti-itcpfegem-beta/billService";
    static final String SUNAT_BETA_USERNAME = "MODDATOS";
    static final String SUNAT_BETA_PASSWORD = "MODDATOS";

    @BeforeAll
    public static void beforeAll() throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException {
        AbstractDocumentsCertResourceTest.loadCertificate();
        AbstractDocumentsCertResourceTest.loadInputGenerators();
    }

    public void assertSendBill(Object input, Document xmlSignedDocument) throws IOException, TransformerException {
        String proveedorRuc = null;
        String fileName = null;

        if (input instanceof InvoiceInputModel) {
            InvoiceInputModel invoice = (InvoiceInputModel) input;
            proveedorRuc = invoice.getProveedor().getRuc();
            String serie = invoice.getSerie();
            Integer numero = invoice.getNumero();
            fileName = XMLUtils.getInvoiceFileName(proveedorRuc, serie, numero);
        } else if (input instanceof CreditNoteInputModel) {
            CreditNoteInputModel creditNote = (CreditNoteInputModel) input;
            proveedorRuc = creditNote.getProveedor().getRuc();
            String serie = creditNote.getSerie();
            Integer numero = creditNote.getNumero();
            fileName = XMLUtils.getNotaCredito(proveedorRuc, serie, numero);
        } else if (input instanceof DebitNoteInputModel) {
            DebitNoteInputModel debitNote = (DebitNoteInputModel) input;
            proveedorRuc = debitNote.getProveedor().getRuc();
            String serie = debitNote.getSerie();
            Integer numero = debitNote.getNumero();
            fileName = XMLUtils.getNotaDebito(proveedorRuc, serie, numero);
        }

        ServiceConfig config = new ServiceConfig.Builder()
                .url(SUNAT_BETA_URL)
                .username(proveedorRuc + SUNAT_BETA_USERNAME)
                .password(SUNAT_BETA_PASSWORD)
                .build();

        byte[] documentBytes = XMLUtils.documentToBytes(xmlSignedDocument);


        BillServiceModel billServiceModel = BillServiceManager.sendBill(fileName + ".xml", documentBytes, config);
        assertEquals(
                BillServiceModel.Status.ACEPTADO,
                billServiceModel.getStatus(),
                assertMessageError(input, "sunat [codigo=" + billServiceModel.getCode() + "], [descripcion=" + billServiceModel.getDescription() + "]", xmlSignedDocument)
        );
    }

    public void assertSendSummary(Object input, Document xmlSignedDocument) throws IOException, TransformerException {
        String proveedorRuc = null;
        String fileName = null;

        if (input instanceof VoidedDocumentInputModel) {
            VoidedDocumentInputModel voidedDocument = (VoidedDocumentInputModel) input;
            proveedorRuc = voidedDocument.getProveedor().getRuc();
            fileName = XMLUtils.getVoidedDocumentFileName(proveedorRuc, voidedDocument.getFechaEmision(), voidedDocument.getNumero());
        } else if (input instanceof SummaryDocumentInputModel) {
            SummaryDocumentInputModel summaryDocument = (SummaryDocumentInputModel) input;
            proveedorRuc = summaryDocument.getProveedor().getRuc();
            fileName = XMLUtils.getSummaryDocumentFileName(proveedorRuc, summaryDocument.getFechaEmision(), summaryDocument.getNumero());
        }

        ServiceConfig config = new ServiceConfig.Builder()
                .url(SUNAT_BETA_URL)
                .username(proveedorRuc + SUNAT_BETA_USERNAME)
                .password(SUNAT_BETA_PASSWORD)
                .build();

        byte[] documentBytes = XMLUtils.documentToBytes(xmlSignedDocument);


        BillServiceModel billServiceModel = BillServiceManager.sendSummary(fileName + ".xml", documentBytes, config);
        assertNotNull(
                billServiceModel.getTicket(),
                assertMessageError(input, "sunat [codigo=" + billServiceModel.getCode() + "], [descripcion=" + billServiceModel.getDescription() + "]", xmlSignedDocument)
        );
    }

    @Test
    public void testCreateInvoice() throws Exception {
        for (InvoiceInputModel input : INVOICES) {
            // Given
            String body = new ObjectMapper().writeValueAsString(input);

            // When
            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post("/documents/invoice/create")
                    .thenReturn();

            // Then
            Document xmlDocument = XMLUtils.inputStreamToDocument(response.getBody().asInputStream());
            Document xmlSignedDocument = XMLSigner.firmarXML(
                    xmlDocument,
                    SIGN_REFERENCE_ID,
                    CERTIFICATE.getX509Certificate(),
                    CERTIFICATE.getPrivateKey()
            );

            assertSendBill(input, xmlSignedDocument);
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

            // read document
            Document xmlDocument = XMLUtils.inputStreamToDocument(response.getBody().asInputStream());
            Document xmlSignedDocument = XMLSigner.firmarXML(
                    xmlDocument,
                    SIGN_REFERENCE_ID,
                    CERTIFICATE.getX509Certificate(),
                    CERTIFICATE.getPrivateKey()
            );

            assertSendBill(input, xmlSignedDocument);
        }
    }

    @Test
    public void testCreateDebitNote() throws Exception {
        for (DebitNoteInputModel input : DEBIT_NOTES) {
            // Given
            String body = new ObjectMapper().writeValueAsString(input);

            // When
            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post("/documents/debit-note/create")
                    .thenReturn();

            // Then
            Document xmlDocument = XMLUtils.inputStreamToDocument(response.getBody().asInputStream());
            Document xmlSignedDocument = XMLSigner.firmarXML(
                    xmlDocument,
                    SIGN_REFERENCE_ID,
                    CERTIFICATE.getX509Certificate(),
                    CERTIFICATE.getPrivateKey()
            );

            assertSendBill(input, xmlSignedDocument);
        }
    }

    @Test
    public void testCreateVoidedDocument() throws Exception {
        for (VoidedDocumentInputModel input : VOIDED_DOCUMENTS) {
            // Given
            String body = new ObjectMapper().writeValueAsString(input);

            // When
            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post("/documents/voided-document/create")
                    .thenReturn();

            // Then
            Document xmlDocument = XMLUtils.inputStreamToDocument(response.getBody().asInputStream());
            Document xmlSignedDocument = XMLSigner.firmarXML(
                    xmlDocument,
                    SIGN_REFERENCE_ID,
                    CERTIFICATE.getX509Certificate(),
                    CERTIFICATE.getPrivateKey()
            );

            assertSendSummary(input, xmlSignedDocument);
        }
    }

    @Test
    public void testCreateSummaryDocument() throws Exception {
        for (SummaryDocumentInputModel input : SUMMARY_DOCUMENTS) {
            // Given
            String body = new ObjectMapper().writeValueAsString(input);

            // When
            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post("/documents/summary-document/create")
                    .thenReturn();

            // Then
            Document xmlDocument = XMLUtils.inputStreamToDocument(response.getBody().asInputStream());
            Document xmlSignedDocument = XMLSigner.firmarXML(
                    xmlDocument,
                    SIGN_REFERENCE_ID,
                    CERTIFICATE.getX509Certificate(),
                    CERTIFICATE.getPrivateKey()
            );

            assertSendSummary(input, xmlSignedDocument);
        }
    }
}
