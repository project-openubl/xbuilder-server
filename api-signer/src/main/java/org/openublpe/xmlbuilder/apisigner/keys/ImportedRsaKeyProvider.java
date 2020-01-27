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
package org.openublpe.xmlbuilder.apisigner.keys;

import org.keycloak.common.util.KeyUtils;
import org.keycloak.common.util.PemUtils;
import org.openublpe.xmlbuilder.apisigner.keys.component.ComponentModel;
import org.openublpe.xmlbuilder.apisigner.models.OrganizationModel;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

public class ImportedRsaKeyProvider extends AbstractRsaKeyProvider {

    public ImportedRsaKeyProvider(OrganizationModel organization, ComponentModel model) {
        super(organization, model);
    }

    @Override
    public Keys loadKeys(OrganizationModel organization, ComponentModel model) {
        String privateRsaKeyPem = model.getConfig().getFirst(Attributes.PRIVATE_KEY_KEY);
        String certificatePem = model.getConfig().getFirst(Attributes.CERTIFICATE_KEY);

        PrivateKey privateKey = PemUtils.decodePrivateKey(privateRsaKeyPem);
        PublicKey publicKey = KeyUtils.extractPublicKey(privateKey);

        KeyPair keyPair = new KeyPair(publicKey, privateKey);
        X509Certificate certificate = PemUtils.decodeCertificate(certificatePem);

        String kid = KeyUtils.createKeyId(keyPair.getPublic());

        return new Keys(kid, keyPair, certificate);
    }

}
