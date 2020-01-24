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
package org.openublpe.xmlbuilder.api.resources.utils;

import org.openublpe.xmlbuilder.inputdata.AbstractInputDataTest;
import org.w3c.dom.Document;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

public abstract class AbstractCertTest extends AbstractInputDataTest {

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
