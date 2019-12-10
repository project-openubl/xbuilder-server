package org.openublpe.xmlbuilder.signer.keys;

public class PublicKeyStorageUtils {

    public static String getClientModelCacheKey(String realmId, String clientUuid) {
        return realmId + "::client::" + clientUuid;
    }

    public static String getIdpModelCacheKey(String realmId, String idpInternalId) {
        return realmId + "::idp::" + idpInternalId;
    }

}
