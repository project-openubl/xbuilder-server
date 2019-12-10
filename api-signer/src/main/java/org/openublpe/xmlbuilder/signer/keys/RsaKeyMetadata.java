package org.openublpe.xmlbuilder.signer.keys;

import java.security.PublicKey;
import java.security.cert.Certificate;

public class RsaKeyMetadata extends KeyMetadata {

    private PublicKey publicKey;
    private Certificate certificate;

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

}
