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
package io.github.project.openubl.xmlbuilder.utils;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import static org.junit.jupiter.api.Assertions.*;

class CertificateDetailsFactoryTest {

    final static String KEYSTORE = "LLAMA-PE-CERTIFICADO-DEMO-10467793549.pfx";
    final static String KEYSTORE_PASSWORD = "password";

    @Test
    void create() throws NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException, KeyStoreException, IOException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(KEYSTORE);
        CertificateDetails certificateDetails = CertificateDetailsFactory.create(is, KEYSTORE_PASSWORD);

        assertNotNull(certificateDetails);
        assertNotNull(certificateDetails.getPrivateKey());
        assertNotNull(certificateDetails.getX509Certificate());
    }
    
}
