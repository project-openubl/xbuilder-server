package org.openublpe.xmlbuilder.resources;

import org.openublpe.xmlbuilder.inputData.CreditNoteInputGenerator;
import org.openublpe.xmlbuilder.inputData.DebitNoteInputGenerator;
import org.openublpe.xmlbuilder.inputData.InputGenerator;
import org.openublpe.xmlbuilder.inputData.InvoiceInputGenerator;
import org.openublpe.xmlbuilder.inputData.SummaryDocumentInputGenerator;
import org.openublpe.xmlbuilder.inputData.VoidedDocumentInputGenerator;
import org.openublpe.xmlbuilder.models.input.standard.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.models.input.standard.note.creditNote.CreditNoteInputModel;
import org.openublpe.xmlbuilder.models.input.standard.note.debitNote.DebitNoteInputModel;
import org.openublpe.xmlbuilder.models.input.sunat.SummaryDocumentInputModel;
import org.openublpe.xmlbuilder.models.input.sunat.VoidedDocumentInputModel;
import org.openublpe.xmlbuilder.utils.CertificateDetails;
import org.openublpe.xmlbuilder.utils.CertificateDetailsFactory;
import org.openublpe.xmlbuilder.utils.XMLUtils;
import org.w3c.dom.Document;

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

public abstract class AbstractDocumentsResourceTest {

    static String SIGN_REFERENCE_ID = "SIGN-ID";
    static String KEYSTORE = "LLAMA-PE-CERTIFICADO-DEMO-10467793549.pfx";
    static String KEYSTORE_PASSWORD = "password";
    static CertificateDetails CERTIFICATE;

    static List<InvoiceInputModel> INVOICES = new ArrayList<>();
    static List<CreditNoteInputModel> CREDIT_NOTES = new ArrayList<>();
    static List<DebitNoteInputModel> DEBIT_NOTES = new ArrayList<>();
    static List<VoidedDocumentInputModel> VOIDED_DOCUMENTS = new ArrayList<>();
    static List<SummaryDocumentInputModel> SUMMARY_DOCUMENTS = new ArrayList<>();

    static Map<Object, Optional<String>> SNAPSHOTS = new HashMap<>();
    static Map<Object, Class> GENERATORS_CLASSES = new HashMap<>();

    private static void loadGenerators(List inputs, Class<? extends InputGenerator> classz) {
        if (inputs.isEmpty()) {
            synchronized (AbstractDocumentsResourceTest.class) {
                if (inputs.isEmpty()) {
                    ServiceLoader<? extends InputGenerator> serviceLoader = ServiceLoader.load(classz);
                    for (InputGenerator generator : serviceLoader) {
                        Object input = generator.getInput();
                        inputs.add(input);
                        SNAPSHOTS.put(input, generator.getSnapshot());
                        GENERATORS_CLASSES.put(input, generator.getClass());
                    }
                }
            }
        }
    }

    public static void loadInputGenerators() {
        loadGenerators(INVOICES, InvoiceInputGenerator.class);
        loadGenerators(CREDIT_NOTES, CreditNoteInputGenerator.class);
        loadGenerators(DEBIT_NOTES, DebitNoteInputGenerator.class);
        loadGenerators(VOIDED_DOCUMENTS, VoidedDocumentInputGenerator.class);
        loadGenerators(SUMMARY_DOCUMENTS, SummaryDocumentInputGenerator.class);
    }

    public static void loadCertificate() throws NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException, KeyStoreException, IOException {
        InputStream ksInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(KEYSTORE);
        CERTIFICATE = CertificateDetailsFactory.create(ksInputStream, KEYSTORE_PASSWORD);
    }

    protected String assertMessageError(Object obj, String error) {
        return "[" + GENERATORS_CLASSES.get(obj).getCanonicalName() + "]\n" + error;
    }

    protected String assertMessageError(Object obj, String error, Document document) throws TransformerException {
        return new StringBuilder()
                .append("\n")
                .append(XMLUtils.documentToString(document)).append("\n")
                .append("CLASS ")
                .append(GENERATORS_CLASSES.get(obj).getCanonicalName()).append("\n")
                .append("MESSAGE ").append(error)
                .toString();
    }

    protected String assertMessageError(String error, Object obj, String documentString) {
        return new StringBuilder()
                .append("\n")
                .append(documentString).append("\n")
                .append("CLASS ")
                .append(GENERATORS_CLASSES.get(obj).getCanonicalName()).append("\n")
                .append("MESSAGE ").append(error)
                .toString();
    }

}
