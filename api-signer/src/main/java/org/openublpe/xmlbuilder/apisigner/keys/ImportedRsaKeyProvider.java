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
