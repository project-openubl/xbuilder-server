package org.openublpe.xmlbuilder.apisigner.keys;

import org.jboss.logging.Logger;
import org.keycloak.common.util.KeyUtils;
import org.keycloak.common.util.Time;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;

public class FailsafeRsaKeyProvider implements RsaKeyProvider {

    private static final Logger logger = Logger.getLogger(FailsafeRsaKeyProvider.class);

    private static String KID;

    private static KeyPair KEY_PAIR;

    private static long EXPIRES;

    private KeyPair keyPair;

    private String kid;

    public FailsafeRsaKeyProvider() {
        logger.errorv("No active keys found, using failsafe provider, please login to admin console to add keys. Clustering is not supported.");

        synchronized (FailsafeRsaKeyProvider.class) {
            if (EXPIRES < Time.currentTime()) {
                KEY_PAIR = KeyUtils.generateRsaKeyPair(2048);
                KID = KeyUtils.createKeyId(KEY_PAIR.getPublic());
                EXPIRES = Time.currentTime() + 60 * 10;

                if (EXPIRES > 0) {
                    logger.warnv("Keys expired, re-generated kid={0}", KID);
                }
            }

            kid = KID;
            keyPair = KEY_PAIR;
        }
    }

    @Override
    public String getKid() {
        return kid;
    }

    @Override
    public PrivateKey getPrivateKey() {
        return keyPair.getPrivate();
    }

    @Override
    public PublicKey getPublicKey(String kid) {
        return kid.equals(this.kid) ? keyPair.getPublic() : null;
    }

    @Override
    public X509Certificate getCertificate(String kid) {
        return null;
    }

    @Override
    public List<RsaKeyMetadata> getKeyMetadata() {
        return Collections.emptyList();
    }

}
