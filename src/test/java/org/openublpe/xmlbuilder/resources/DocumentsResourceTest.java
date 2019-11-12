package org.openublpe.xmlbuilder.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helger.ubl21.UBL21Reader;
import io.github.carlosthe19916.webservices.managers.BillServiceManager;
import io.github.carlosthe19916.webservices.providers.BillServiceModel;
import io.github.carlosthe19916.webservices.wrappers.ServiceConfig;
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
import org.openublpe.xmlbuilder.data.CreditNoteInputGenerator;
import org.openublpe.xmlbuilder.data.DebitNoteInputGenerator;
import org.openublpe.xmlbuilder.data.InvoiceInputGenerator;
import org.openublpe.xmlbuilder.models.input.general.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.models.input.general.note.creditNote.CreditNoteInputModel;
import org.openublpe.xmlbuilder.models.input.general.note.debitNote.DebitNoteInputModel;
import org.openublpe.xmlbuilder.utils.CertificateDetails;
import org.openublpe.xmlbuilder.utils.CertificateDetailsFactory;
import org.openublpe.xmlbuilder.utils.XMLSigner;
import org.openublpe.xmlbuilder.utils.XMLUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;

import static io.restassured.RestAssured.given;
import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class DocumentsResourceTest {

    static String SIGN_REFERENCE_ID = "SIGN-ID";

    static String KEYSTORE = "LLAMA-PE-CERTIFICADO-DEMO-10467793549.pfx";
    static String KEYSTORE_PASSWORD = "password";
    static CertificateDetails CERTIFICATE;

    static final String SUNAT_BETA_URL = "https://e-beta.sunat.gob.pe/ol-ti-itcpfegem-beta/billService";
    static final String SUNAT_BETA_USERNAME = "MODDATOS";
    static final String SUNAT_BETA_PASSWORD = "MODDATOS";

    static List<InvoiceInputModel> invoiceInputs = new ArrayList<>();
    static List<CreditNoteInputModel> creditNoteInputs = new ArrayList<>();
    static List<DebitNoteInputModel> debitNoteInputs = new ArrayList<>();

    static Map<Object, Optional<String>> SNAPSHOTS = new HashMap<>();
    static Map<Object, Class> generatorMap = new HashMap<>();

    @BeforeAll
    public static void beforeAll() throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException {
        InputStream ksInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(KEYSTORE);
        CERTIFICATE = CertificateDetailsFactory.create(ksInputStream, KEYSTORE_PASSWORD);

        ServiceLoader<InvoiceInputGenerator> serviceLoader1 = ServiceLoader.load(InvoiceInputGenerator.class);
        for (InvoiceInputGenerator generator : serviceLoader1) {
            InvoiceInputModel input = generator.getInput();
            invoiceInputs.add(input);
            SNAPSHOTS.put(input, generator.getSnapshot());
            generatorMap.put(input, generator.getClass());
        }

        ServiceLoader<CreditNoteInputGenerator> serviceLoader2 = ServiceLoader.load(CreditNoteInputGenerator.class);
        for (CreditNoteInputGenerator generator : serviceLoader2) {
            CreditNoteInputModel input = generator.getInput();
            creditNoteInputs.add(input);
            SNAPSHOTS.put(input, generator.getSnapshot());
            generatorMap.put(input, generator.getClass());
        }

        ServiceLoader<DebitNoteInputGenerator> serviceLoader3 = ServiceLoader.load(DebitNoteInputGenerator.class);
        for (DebitNoteInputGenerator generator : serviceLoader3) {
            DebitNoteInputModel input = generator.getInput();
            debitNoteInputs.add(input);
            SNAPSHOTS.put(input, generator.getSnapshot());
            generatorMap.put(input, generator.getClass());
        }

        //ignore while space differances
        XMLUnit.setIgnoreWhitespace(true);

        //ignore attribute order
        XMLUnit.setIgnoreAttributeOrder(true);

        //ignore comment differances
        XMLUnit.setIgnoreComments(true);

        //ignore differance on CData and text
        XMLUnit.setIgnoreDiffBetweenTextAndCDATA(true);
    }

    String assertMessageError(Object obj, String error) {
        return "[" + generatorMap.get(obj).getCanonicalName() + "]\n" + error;
    }

    String assertMessageError(Object obj, String error, Document document) throws TransformerException {
        return new StringBuilder()
                .append("\n")
                .append(XMLUtils.documentToString(document)).append("\n")
                .append("CLASS ")
                .append(generatorMap.get(obj).getCanonicalName()).append("\n")
                .append("MESSAGE ").append(error)
                .toString();
    }

    String assertMessageError(String error, Object obj, String documentString) {
        return new StringBuilder()
                .append("\n")
                .append(documentString).append("\n")
                .append("CLASS ")
                .append(generatorMap.get(obj).getCanonicalName()).append("\n")
                .append("MESSAGE ").append(error)
                .toString();
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

//        //this will print even if order mismatch elements are there. if you want to skip this use assertor
//        Iterator i = detailedDiff.getAllDifferences().iterator();
//        while (i.hasNext()) {
//            System.out.println(i.next().toString());
//        }
//        System.out.println("================== if soarting issues are ignored =============================");

            //this can use ignore soarting issues and assert
            assertXMLEqual(
                    assertMessageError("XML Snapshot does not match", input, responseBody.asString()),
                    detailedDiff,
                    true
            );
        }
    }

    public void assertSend(Object input, Document xmlSignedDocument) throws IOException, TransformerException {
        if (System.getProperty("sunat") == null) {
            return;
        }

        String proveedorRuc = null;
        String serie = null;
        Integer numero = null;
        String fileName = null;

        if (input instanceof InvoiceInputModel) {
            InvoiceInputModel invoice = (InvoiceInputModel) input;
            proveedorRuc = invoice.getProveedor().getRuc();
            serie = invoice.getSerie();
            numero = invoice.getNumero();
            fileName = XMLUtils.getInvoiceFileName(proveedorRuc, serie, numero);
        } else if (input instanceof CreditNoteInputModel) {
            CreditNoteInputModel creditNote = (CreditNoteInputModel) input;
            proveedorRuc = creditNote.getProveedor().getRuc();
            serie = creditNote.getSerie();
            numero = creditNote.getNumero();
            fileName = XMLUtils.getNotaCredito(proveedorRuc, serie, numero);
        } else if (input instanceof DebitNoteInputModel) {
            DebitNoteInputModel debitNote = (DebitNoteInputModel) input;
            proveedorRuc = debitNote.getProveedor().getRuc();
            serie = debitNote.getSerie();
            numero = debitNote.getNumero();
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

    @Test
    public void testCreateInvoice() throws Exception {
        for (InvoiceInputModel input : invoiceInputs) {
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

            // Send to test
            assertSend(input, xmlSignedDocument);
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

            // Send to test
            assertSend(input, xmlSignedDocument);
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

            // Send to test
            assertSend(input, xmlSignedDocument);
        }
    }

}