package org.openublpe.xmlbuilder.signer.keys;

import org.keycloak.jose.jws.AlgorithmType;

import java.util.List;

public interface KeyProvider<T extends KeyMetadata> {

    /**
     * Returns the algorithm type the keys can be used for
     *
     * @return
     */
    AlgorithmType getType();

    /**
     * Return the KID for the active keypair, or <code>null</code> if no active key is available.
     *
     * @return
     */
    String getKid();

    /**
     * Return metadata about all keypairs held by the provider
     *
     * @return
     */
    List<T> getKeyMetadata();

}
