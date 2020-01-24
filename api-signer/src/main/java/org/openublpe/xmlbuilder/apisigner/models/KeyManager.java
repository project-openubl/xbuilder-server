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
package org.openublpe.xmlbuilder.apisigner.models;

import org.openublpe.xmlbuilder.apisigner.keys.RsaKeyMetadata;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;

public interface KeyManager {

    ActiveRsaKey getActiveRsaKey(OrganizationModel organization);

    PublicKey getRsaPublicKey(OrganizationModel organization, String kid);

    Certificate getRsaCertificate(OrganizationModel organization, String kid);

    List<RsaKeyMetadata> getRsaKeys(OrganizationModel organization, boolean includeDisabled);

    class ActiveRsaKey {
        private final String kid;
        private final PrivateKey privateKey;
        private final PublicKey publicKey;
        private final X509Certificate certificate;

        public ActiveRsaKey(String kid, PrivateKey privateKey, PublicKey publicKey, X509Certificate certificate) {
            this.kid = kid;
            this.privateKey = privateKey;
            this.publicKey = publicKey;
            this.certificate = certificate;
        }

        public String getKid() {
            return kid;
        }

        public PrivateKey getPrivateKey() {
            return privateKey;
        }

        public PublicKey getPublicKey() {
            return publicKey;
        }

        public X509Certificate getCertificate() {
            return certificate;
        }
    }

}
