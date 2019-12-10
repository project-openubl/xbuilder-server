package org.openublpe.xmlbuilder.resources;

import org.openublpe.xmlbuilder.inputData.AbstractDocumentsResourceTest;
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

public abstract class AbstractDocumentsCertResourceTest extends AbstractDocumentsResourceTest {

    static String SIGN_REFERENCE_ID = "SIGN-ID";
    static String KEYSTORE = "LLAMA-PE-CERTIFICADO-DEMO-10467793549.pfx";
    static String KEYSTORE_PASSWORD = "password";
    static CertificateDetails CERTIFICATE;

    public static void loadCertificate() throws NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException, KeyStoreException, IOException {
        InputStream ksInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(KEYSTORE);
        CERTIFICATE = CertificateDetailsFactory.create(ksInputStream, KEYSTORE_PASSWORD);
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

}
