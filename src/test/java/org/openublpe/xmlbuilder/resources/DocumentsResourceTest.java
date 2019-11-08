package org.openublpe.xmlbuilder.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helger.ubl21.UBL21Reader;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import oasis.names.specification.ubl.schema.xsd.creditnote_21.CreditNoteType;
import oasis.names.specification.ubl.schema.xsd.debitnote_21.DebitNoteType;
import oasis.names.specification.ubl.schema.xsd.invoice_21.InvoiceType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openublpe.xmlbuilder.data.CreditNoteInputGenerator;
import org.openublpe.xmlbuilder.data.DebitNoteInputGenerator;
import org.openublpe.xmlbuilder.data.InvoiceInputGenerator;
import org.openublpe.xmlbuilder.models.input.general.note.creditNote.CreditNoteInputModel;
import org.openublpe.xmlbuilder.models.input.general.note.debitNote.DebitNoteInputModel;
import org.openublpe.xmlbuilder.models.input.general.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.utils.CertificateDetails;
import org.openublpe.xmlbuilder.utils.CertificateDetailsFactory;
import org.openublpe.xmlbuilder.utils.XMLSigner;
import org.openublpe.xmlbuilder.utils.XMLUtils;
import org.w3c.dom.Document;

import java.io.*;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class DocumentsResourceTest {

    static String SIGN_REFERENCE_ID = "TestID";

    static String KEYSTORE = "keystore.jks";
    static String KEYSTORE_PASSWORD = "password";
    static CertificateDetails CERTIFICATE;

    static List<InvoiceInputModel> invoiceInputs = new ArrayList<>();
    static List<CreditNoteInputModel> creditNoteInputs = new ArrayList<>();
    static List<DebitNoteInputModel> debitNoteInputs = new ArrayList<>();

    @BeforeAll
    public static void beforeAll() throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException {
        InputStream ksInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(KEYSTORE);
        CERTIFICATE = CertificateDetailsFactory.create(ksInputStream, KEYSTORE_PASSWORD);

        ServiceLoader<InvoiceInputGenerator> serviceLoader1 = ServiceLoader.load(InvoiceInputGenerator.class);
        for (InvoiceInputGenerator generator : serviceLoader1) {
            invoiceInputs.add(generator.getInvoice());
        }

        ServiceLoader<CreditNoteInputGenerator> serviceLoader2 = ServiceLoader.load(CreditNoteInputGenerator.class);
        for (CreditNoteInputGenerator generator : serviceLoader2) {
            creditNoteInputs.add(generator.getCreditNote());
        }

        ServiceLoader<DebitNoteInputGenerator> serviceLoader3 = ServiceLoader.load(DebitNoteInputGenerator.class);
        for (DebitNoteInputGenerator generator : serviceLoader3) {
            debitNoteInputs.add(generator.getDebitNote());
        }
    }

    @Test
    public void testCreateInvoice() throws Exception {
        for (InvoiceInputModel input : invoiceInputs) {
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
            assertEquals(200, response.getStatusCode());
            InputStream bodyInputStream = response.getBody().asInputStream();

            // read document
            Document xmlDocument = XMLUtils.inputStreamToDocument(bodyInputStream);
            assertNotNull(xmlDocument);

            // Sign document
            XMLSigner.firmarXML(xmlDocument, SIGN_REFERENCE_ID, CERTIFICATE.getX509Certificate(), CERTIFICATE.getPrivateKey());

            // Validate valid XML
            InvoiceType invoiceType = UBL21Reader.invoice().read(xmlDocument);
            assertNotNull(invoiceType);

            // Send to test
//            final ServiceConfig config = new ServiceConfig.Builder()
//                    .url("https://e-beta.sunat.gob.pe/ol-ti-itcpfegem-beta/billService")
//                    .username(input.getProveedor().getRuc() + "MODDATOS")
//                    .password("MODDATOS")
//                    .build();
//            String invoiceFileNameWithoutExtension = XMLReaderUtils.getInvoiceFileName(input.getProveedor().getRuc(), input.getSerie(), input.getNumero());
//            BillServiceModel billServiceModel = BillServiceManager.sendBill(invoiceFileNameWithoutExtension + ".xml", XMLReaderUtils.documentToBytes(xmlDocument), config);
//            System.out.println(billServiceModel);
        }
    }

    @Test
    public void testCreateCreditNote() throws Exception {
        for (CreditNoteInputModel input : creditNoteInputs) {
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
            assertEquals(200, response.getStatusCode());
            InputStream bodyInputStream = response.getBody().asInputStream();

            // read document
            Document xmlDocument = XMLUtils.inputStreamToDocument(bodyInputStream);
            assertNotNull(xmlDocument);

            // Sign document
            XMLSigner.firmarXML(xmlDocument, SIGN_REFERENCE_ID, CERTIFICATE.getX509Certificate(), CERTIFICATE.getPrivateKey());

            // Validate valid XML
            CreditNoteType creditNoteType = UBL21Reader.creditNote().read(xmlDocument);
            assertNotNull(creditNoteType);
        }
    }

    @Test
    public void testCreateDebitNote() throws Exception {
        for (DebitNoteInputModel input : debitNoteInputs) {
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
            assertEquals(200, response.getStatusCode());
            InputStream bodyInputStream = response.getBody().asInputStream();

            // read document
            Document xmlDocument = XMLUtils.inputStreamToDocument(bodyInputStream);
            assertNotNull(xmlDocument);

            // Sign document
            XMLSigner.firmarXML(xmlDocument, SIGN_REFERENCE_ID, CERTIFICATE.getX509Certificate(), CERTIFICATE.getPrivateKey());

            // Validate valid XML
            DebitNoteType debitNoteType = UBL21Reader.debitNote().read(xmlDocument);
            assertNotNull(debitNoteType);
        }
    }

}