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

import org.jboss.logging.Logger;
import org.keycloak.common.util.CertificateUtils;
import org.keycloak.common.util.KeyUtils;
import org.keycloak.common.util.PemUtils;
import org.openublpe.xmlbuilder.apisigner.keys.component.ComponentModel;
import org.openublpe.xmlbuilder.apisigner.keys.component.ComponentValidationException;
import org.openublpe.xmlbuilder.apisigner.keys.provider.ConfigurationValidationHelper;
import org.openublpe.xmlbuilder.apisigner.keys.provider.ProviderConfigProperty;
import org.openublpe.xmlbuilder.apisigner.keys.qualifiers.ComponentProviderType;
import org.openublpe.xmlbuilder.apisigner.keys.qualifiers.RsaKeyProviderType;
import org.openublpe.xmlbuilder.apisigner.keys.qualifiers.RsaKeyType;
import org.openublpe.xmlbuilder.apisigner.models.OrganizationModel;

import javax.enterprise.context.ApplicationScoped;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.util.List;

@ApplicationScoped
@ComponentProviderType(providerType = KeyProvider.class)
@RsaKeyProviderType(type = RsaKeyType.GENERATED)
public class GeneratedRsaKeyProviderFactory extends AbstractRsaKeyProviderFactory implements RsaKeyProviderFactory {

    public static final String ID = "rsa-generated";
    private static final Logger logger = Logger.getLogger(GeneratedRsaKeyProviderFactory.class);
    private static final String HELP_TEXT = "Generates RSA keys and creates a self-signed certificate";

    private static final List<ProviderConfigProperty> CONFIG_PROPERTIES = configurationBuilder()
            .property(Attributes.KEY_SIZE_PROPERTY)
            .build();

    @Override
    public KeyProvider create(OrganizationModel organization, ComponentModel model) {
        return new ImportedRsaKeyProvider(organization, model);
    }

    @Override
    public void validateConfiguration(OrganizationModel organization, ComponentModel model) throws ComponentValidationException {
        super.validateConfiguration(organization, model);

        ConfigurationValidationHelper.check(model).checkList(Attributes.KEY_SIZE_PROPERTY, false);

        int size = model.get(Attributes.KEY_SIZE_KEY, 2048);

        if (!(model.contains(Attributes.PRIVATE_KEY_KEY) && model.contains(Attributes.CERTIFICATE_KEY))) {
            generateKeys(organization, model, size);

            logger.debugv("Generated keys for {0}", organization.getName());
        } else {
            PrivateKey privateKey = PemUtils.decodePrivateKey(model.get(Attributes.PRIVATE_KEY_KEY));
            int currentSize = ((RSAPrivateKey) privateKey).getModulus().bitLength();
            if (currentSize != size) {
                generateKeys(organization, model, size);

                logger.debugv("Key size changed, generating new keys for {0}", organization.getName());
            }
        }
    }

    private void generateKeys(OrganizationModel organization, ComponentModel model, int size) {
        KeyPair keyPair;
        try {
            keyPair = KeyUtils.generateRsaKeyPair(size);
            model.put(Attributes.PRIVATE_KEY_KEY, PemUtils.encodeKey(keyPair.getPrivate()));
        } catch (Throwable t) {
            throw new ComponentValidationException("Failed to generate keys", t);
        }

        generateCertificate(organization, model, keyPair);
    }

    private void generateCertificate(OrganizationModel organization, ComponentModel model, KeyPair keyPair) {
        try {
            Certificate certificate = CertificateUtils.generateV1SelfSignedCertificate(keyPair, organization.getName());
            model.put(Attributes.CERTIFICATE_KEY, PemUtils.encodeCertificate(certificate));
        } catch (Throwable t) {
            throw new ComponentValidationException("Failed to generate certificate", t);
        }
    }

    @Override
    public String getHelpText() {
        return HELP_TEXT;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return CONFIG_PROPERTIES;
    }

    @Override
    public String getId() {
        return ID;
    }
}
