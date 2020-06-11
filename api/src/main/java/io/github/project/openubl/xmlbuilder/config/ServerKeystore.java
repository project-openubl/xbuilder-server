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
package io.github.project.openubl.xmlbuilder.config;

import io.github.project.openubl.xmlbuilder.utils.CertificateDetails;
import io.github.project.openubl.xmlbuilder.utils.CertificateDetailsFactory;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.*;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.Optional;

@ApplicationScoped
public class ServerKeystore {

    @Inject
    @ConfigProperty(name = "openubl.server.keystore.location")
    Optional<String> keystoreLocation;

    @Inject
    @ConfigProperty(name = "openubl.server.keystore.password")
    Optional<String> keystorePassword;

    private CertificateDetails certificate;

    public CertificateDetails getCertificate() throws IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException, KeyStoreException {
        if (!hasKeys()) {
            throw new IllegalStateException("Server has not a keystore configured");
        }

        InputStream ksInputStream = new FileInputStream(new File(getKeystoreLocation()));
        if (certificate == null) {
            certificate = CertificateDetailsFactory.create(ksInputStream, getKeystorePassword());
        }
        return certificate;
    }

    public boolean hasKeys() {
        return keystoreLocation.isPresent() && keystorePassword.isPresent();
    }

    public String getKeystoreLocation(){
        return keystoreLocation.orElseThrow(() -> new IllegalStateException("Keystore location not configured"));
    }

    public String getKeystorePassword() {
        return keystorePassword.orElseThrow(() -> new IllegalStateException("Keystore password not configured"));
    }

}
