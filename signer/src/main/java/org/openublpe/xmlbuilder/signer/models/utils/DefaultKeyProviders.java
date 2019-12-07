package org.openublpe.xmlbuilder.signer.models.utils;

import org.keycloak.common.util.MultivaluedHashMap;
import org.openublpe.xmlbuilder.signer.keys.KeyProvider;
import org.openublpe.xmlbuilder.signer.keys.component.ComponentModel;
import org.openublpe.xmlbuilder.signer.models.ComponentProvider;
import org.openublpe.xmlbuilder.signer.models.ModelException;
import org.openublpe.xmlbuilder.signer.models.OrganizationModel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Transactional
@ApplicationScoped
public class DefaultKeyProviders {

    @Inject
    ComponentProvider componentProvider;

    public void createProviders(OrganizationModel organization) throws ModelException {
        ComponentModel generated = new ComponentModel();
        generated.setName("rsa-generated");
        generated.setParentId(organization.getId());
        generated.setProviderId("rsa-generated");
        generated.setProviderType(KeyProvider.class.getName());

        MultivaluedHashMap<String, String> config = new MultivaluedHashMap<>();
        config.putSingle("priority", "100");
        generated.setConfig(config);

        componentProvider.addComponentModel(organization, generated);
    }

    public void createProviders(OrganizationModel organization, String privateKeyPem, String certificatePem) throws ModelException {
        ComponentModel rsa = new ComponentModel();
        rsa.setName("rsa");
        rsa.setParentId(organization.getId());
        rsa.setProviderId("rsa");
        rsa.setProviderType(KeyProvider.class.getName());

        MultivaluedHashMap<String, String> config = new MultivaluedHashMap<>();
        config.putSingle("priority", "100");
        config.putSingle("privateKey", privateKeyPem);
        if (certificatePem != null) {
            config.putSingle("certificate", certificatePem);
        }
        rsa.setConfig(config);

        componentProvider.addComponentModel(organization, rsa);
    }

}
