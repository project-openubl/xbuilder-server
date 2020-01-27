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
import org.openublpe.xmlbuilder.apisigner.keys.component.ComponentModel;
import org.openublpe.xmlbuilder.apisigner.keys.component.ComponentValidationException;
import org.openublpe.xmlbuilder.apisigner.keys.provider.ConfigurationValidationHelper;
import org.openublpe.xmlbuilder.apisigner.keys.provider.ProviderConfigProperty;
import org.openublpe.xmlbuilder.apisigner.keys.qualifiers.ComponentProviderType;
import org.openublpe.xmlbuilder.apisigner.keys.qualifiers.RsaKeyProviderType;
import org.openublpe.xmlbuilder.apisigner.keys.qualifiers.RsaKeyType;
import org.openublpe.xmlbuilder.apisigner.models.OrganizationModel;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
@ComponentProviderType(providerType = KeyProvider.class)
@RsaKeyProviderType(type = RsaKeyType.JAVA_KEYSTORE)
public class JavaKeystoreKeyProviderFactory extends AbstractRsaKeyProviderFactory implements RsaKeyProviderFactory {

    private static final Logger logger = Logger.getLogger(JavaKeystoreKeyProviderFactory.class);

    public static final String ID = "java-keystore";

    public static final String KEYSTORE_KEY = "keystore";
    public static final ProviderConfigProperty KEYSTORE_PROPERTY = new ProviderConfigProperty(KEYSTORE_KEY, "Keystore", "Path to keys file", ProviderConfigProperty.STRING_TYPE, null);

    public static final String KEYSTORE_PASSWORD_KEY = "keystorePassword";
    public static final ProviderConfigProperty KEYSTORE_PASSWORD_PROPERTY = new ProviderConfigProperty(KEYSTORE_PASSWORD_KEY, "Keystore Password", "Password for the keys", ProviderConfigProperty.STRING_TYPE, null, true);

    public static final String KEY_ALIAS_KEY = "keyAlias";
    public static final ProviderConfigProperty KEY_ALIAS_PROPERTY = new ProviderConfigProperty(KEY_ALIAS_KEY, "Key Alias", "Alias for the private key", ProviderConfigProperty.STRING_TYPE, null);

    public static final String KEY_PASSWORD_KEY = "keyPassword";
    public static final ProviderConfigProperty KEY_PASSWORD_PROPERTY = new ProviderConfigProperty(KEY_PASSWORD_KEY, "Key Password", "Password for the private key", ProviderConfigProperty.STRING_TYPE, null, true);

    private static final String HELP_TEXT = "Loads keys from a Java keys file";

    private static final List<ProviderConfigProperty> CONFIG_PROPERTIES = configurationBuilder()
            .property(KEYSTORE_PROPERTY)
            .property(KEYSTORE_PASSWORD_PROPERTY)
            .property(KEY_ALIAS_PROPERTY)
            .property(KEY_PASSWORD_PROPERTY)
            .build();

    @Override
    public KeyProvider create(OrganizationModel organization, ComponentModel model) {
        return new JavaKeystoreKeyProvider(organization, model);
    }

    @Override
    public void validateConfiguration(OrganizationModel organization, ComponentModel model) throws ComponentValidationException {
        super.validateConfiguration(organization, model);

        ConfigurationValidationHelper.check(model)
                .checkSingle(KEYSTORE_PROPERTY, true)
                .checkSingle(KEYSTORE_PASSWORD_PROPERTY, true)
                .checkSingle(KEY_ALIAS_PROPERTY, true)
                .checkSingle(KEY_PASSWORD_PROPERTY, true);

        try {
            new JavaKeystoreKeyProvider(organization, model).loadKeys(organization, model);
        } catch (Throwable t) {
            logger.error("Failed to load keys.", t);
            throw new ComponentValidationException("Failed to load keys. " + t.getMessage(), t);
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
