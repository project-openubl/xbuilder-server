package org.openublpe.xmlbuilder.apisigner.keys;

import java.security.PublicKey;

public interface PublicKeyStorageProvider {


    /**
     * Get public key to verify messages signed by particular client. Used for example during JWT client authentication
     *
     * @param modelKey
     * @param kid
     * @param loader
     * @return
     */
    PublicKey getPublicKey(String modelKey, String kid, PublicKeyLoader loader);

    /**
     * Clears all the cached public keys, so they need to be loaded again
     */
    void clearCache();

}
