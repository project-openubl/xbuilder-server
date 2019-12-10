package org.openublpe.xmlbuilder.signer.keys;

import org.openublpe.xmlbuilder.signer.keys.component.ComponentFactory;
import org.openublpe.xmlbuilder.signer.keys.component.ComponentModel;
import org.openublpe.xmlbuilder.signer.models.OrganizationModel;

public interface KeyProviderFactory<T extends KeyProvider> extends ComponentFactory<T, KeyProvider> {

    T create(OrganizationModel organization, ComponentModel model);

}
