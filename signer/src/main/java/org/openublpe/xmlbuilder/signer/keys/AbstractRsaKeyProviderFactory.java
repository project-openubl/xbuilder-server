package org.openublpe.xmlbuilder.signer.keys;

import org.openublpe.xmlbuilder.signer.keys.component.ComponentModel;
import org.openublpe.xmlbuilder.signer.keys.component.ComponentValidationException;
import org.openublpe.xmlbuilder.signer.keys.provider.ConfigurationValidationHelper;
import org.openublpe.xmlbuilder.signer.keys.provider.ProviderConfigurationBuilder;
import org.openublpe.xmlbuilder.signer.models.OrganizationModel;

public abstract class AbstractRsaKeyProviderFactory {

    public final static ProviderConfigurationBuilder configurationBuilder() {
        return ProviderConfigurationBuilder.create()
                .property(Attributes.PRIORITY_PROPERTY)
                .property(Attributes.ENABLED_PROPERTY)
                .property(Attributes.ACTIVE_PROPERTY);
    }

    public void validateConfiguration(OrganizationModel organization, ComponentModel model) throws ComponentValidationException {
        ConfigurationValidationHelper.check(model)
                .checkLong(Attributes.PRIORITY_PROPERTY, false)
                .checkBoolean(Attributes.ENABLED_PROPERTY, false)
                .checkBoolean(Attributes.ACTIVE_PROPERTY, false);
    }
}
