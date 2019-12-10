package org.openublpe.xmlbuilder.signer.keys;

import org.jboss.logging.Logger;
import org.keycloak.common.util.CertificateUtils;
import org.keycloak.common.util.KeyUtils;
import org.openublpe.xmlbuilder.signer.keys.component.ComponentModel;
import org.openublpe.xmlbuilder.signer.models.OrganizationModel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class JavaKeystoreKeyProvider extends AbstractRsaKeyProvider {

    private static final Logger logger = Logger.getLogger(JavaKeystoreKeyProvider.class);

    public JavaKeystoreKeyProvider(OrganizationModel organization, ComponentModel model) {
        super(organization, model);
    }

    @Override
    protected Keys loadKeys(OrganizationModel organization, ComponentModel model) {
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream(model.get(JavaKeystoreKeyProviderFactory.KEYSTORE_KEY)), model.get(JavaKeystoreKeyProviderFactory.KEYSTORE_PASSWORD_KEY).toCharArray());

            PrivateKey privateKey = (PrivateKey) keyStore.getKey(model.get(JavaKeystoreKeyProviderFactory.KEY_ALIAS_KEY), model.get(JavaKeystoreKeyProviderFactory.KEY_PASSWORD_KEY).toCharArray());
            PublicKey publicKey = KeyUtils.extractPublicKey(privateKey);

            KeyPair keyPair = new KeyPair(publicKey, privateKey);

            X509Certificate certificate = (X509Certificate) keyStore.getCertificate(model.get(JavaKeystoreKeyProviderFactory.KEY_ALIAS_KEY));
            if (certificate == null) {
                certificate = CertificateUtils.generateV1SelfSignedCertificate(keyPair, organization.getName());
            }

            String kid = KeyUtils.createKeyId(keyPair.getPublic());

            return new Keys(kid, keyPair, certificate);
        } catch (KeyStoreException kse) {
            throw new RuntimeException("KeyStore error on server. " + kse.getMessage(), kse);
        } catch (FileNotFoundException fnfe) {
            throw new RuntimeException("File not found on server. " + fnfe.getMessage(), fnfe);
        } catch (IOException ioe) {
            throw new RuntimeException("IO error on server. " + ioe.getMessage(), ioe);
        } catch (NoSuchAlgorithmException nsae) {
            throw new RuntimeException("Algorithm not available on server. " + nsae.getMessage(), nsae);
        } catch (CertificateException ce) {
            throw new RuntimeException("Certificate error on server. " + ce.getMessage(), ce);
        } catch (UnrecoverableKeyException uke) {
            throw new RuntimeException("Keystore on server can not be recovered. " + uke.getMessage(), uke);
        }
    }

}
