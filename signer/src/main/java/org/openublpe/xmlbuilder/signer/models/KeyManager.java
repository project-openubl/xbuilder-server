package org.openublpe.xmlbuilder.signer.models;

import org.openublpe.xmlbuilder.signer.keys.RsaKeyMetadata;

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
