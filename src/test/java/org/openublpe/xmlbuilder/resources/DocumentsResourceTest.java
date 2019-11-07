package org.openublpe.xmlbuilder.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.helger.ubl21.UBL21Reader;
import io.github.carlosthe19916.webservices.managers.BillServiceManager;
import io.github.carlosthe19916.webservices.providers.BillServiceModel;
import io.github.carlosthe19916.webservices.wrappers.ServiceConfig;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import oasis.names.specification.ubl.schema.xsd.invoice_21.InvoiceType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openublpe.xmlbuilder.CreditNoteInputGenerator;
import org.openublpe.xmlbuilder.DebitNoteInputGenerator;
import org.openublpe.xmlbuilder.InvoiceInputGenerator;
import org.openublpe.xmlbuilder.models.input.general.note.creditNote.CreditNoteInputModel;
import org.openublpe.xmlbuilder.models.input.general.note.debitNote.DebitNoteInputModel;
import org.openublpe.xmlbuilder.models.input.general.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.utils.CertificateDetails;
import org.openublpe.xmlbuilder.utils.CertificateUtil;
import org.openublpe.xmlbuilder.utils.SignXMLDocumentUtils;
import org.openublpe.xmlbuilder.utils.XMLReaderUtils;
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

    static CertificateDetails certDetails;

    static List<InvoiceInputModel> invoiceInputs = new ArrayList<>();
    static List<CreditNoteInputModel> creditNoteInputs = new ArrayList<>();
    static List<DebitNoteInputModel> debitNoteInputs = new ArrayList<>();

    @BeforeAll
    public static void beforeAll() throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException {
        InputStream ksInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("keystore.jks");
        certDetails = CertificateUtil.getCertificateDetails(ksInputStream, "password");

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
            String body = new ObjectMapper().writeValueAsString(input);

            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post("/documents/invoice/create")
                    .thenReturn();

            assertEquals(200, response.getStatusCode());

            InputStream bodyInputStream = response.getBody().asInputStream();

            // read document
            Document xmlDocument = XMLReaderUtils.inputStreamToDocument(bodyInputStream);

            // Sign document
            xmlDocument = SignXMLDocumentUtils.firmarXML(xmlDocument, "TestSignID", certDetails.getX509Certificate(), certDetails.getPrivateKey());

            // Validate valid XML
            InvoiceType invoiceType = UBL21Reader.invoice().read(xmlDocument);
            assertNotNull(invoiceType);

            // Send to test
            final ServiceConfig config = new ServiceConfig.Builder()
                    .url("https://e-beta.sunat.gob.pe/ol-ti-itcpfegem-beta/billService")
                    .username(input.getProveedor().getRuc() + "MODDATOS")
                    .password("MODDATOS")
                    .build();
            String invoiceFileNameWithoutExtension = XMLReaderUtils.getInvoiceFileName(input.getProveedor().getRuc(), input.getSerie(), input.getNumero());
            BillServiceModel billServiceModel = BillServiceManager.sendBill(invoiceFileNameWithoutExtension + ".xml", XMLReaderUtils.documentToBytes(xmlDocument), config);
            System.out.println(billServiceModel);
        }
    }

    @Test
    public void testCreateCreditNote() throws JsonProcessingException {
        for (CreditNoteInputModel input : creditNoteInputs) {
            String body = new ObjectMapper().writeValueAsString(input);

            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post("/documents/credit-note/create")
                    .thenReturn();

            assertEquals(200, response.getStatusCode());
        }
    }

    @Test
    public void testCreateDebitNote() throws JsonProcessingException {
        for (DebitNoteInputModel input : debitNoteInputs) {
            String body = new ObjectMapper().writeValueAsString(input);

            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post("/documents/debit-note/create")
                    .thenReturn();

            assertEquals(200, response.getStatusCode());
        }
    }

}