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
