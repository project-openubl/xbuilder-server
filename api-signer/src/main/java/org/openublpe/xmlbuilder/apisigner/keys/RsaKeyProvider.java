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

import org.keycloak.jose.jws.AlgorithmType;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

public interface RsaKeyProvider extends KeyProvider<RsaKeyMetadata> {

    default AlgorithmType getType() {
        return AlgorithmType.RSA;
    }

    /**
     * Return the private key for the active keypair, or <code>null</code> if no active key is available.
     *
     * @return
     */
    PrivateKey getPrivateKey();

    /**
     * Return the public key for the specified kid, or <code>null</code> if the kid is unknown.
     *
     * @param kid
     * @return
     */
    PublicKey getPublicKey(String kid);

    /**
     * Return the certificate for the specified kid, or <code>null</code> if the kid is unknown.
     *
     * @param kid
     * @return
     */
    X509Certificate getCertificate(String kid);

}
