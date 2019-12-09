package org.openublpe.xmlbuilder.signer.managers;

import org.openublpe.xmlbuilder.signer.keys.KeyProvider;
import org.openublpe.xmlbuilder.signer.models.ComponentProvider;
import org.openublpe.xmlbuilder.signer.models.ModelException;
import org.openublpe.xmlbuilder.signer.models.ModelRuntimeException;
import org.openublpe.xmlbuilder.signer.models.OrganizationModel;
import org.openublpe.xmlbuilder.signer.models.OrganizationProvider;
import org.openublpe.xmlbuilder.signer.models.OrganizationType;
import org.openublpe.xmlbuilder.signer.models.utils.DefaultKeyProviders;
import org.openublpe.xmlbuilder.signer.models.utils.RepresentationToModel;
import org.openublpe.xmlbuilder.signer.representations.idm.OrganizationRepresentation;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Transactional
@ApplicationScoped
public class OrganizationManager {

    @Inject
    ComponentProvider componentProvider;

    @Inject
    DefaultKeyProviders defaultKeyProviders;

    @Inject
    OrganizationProvider organizationProvider;

    public OrganizationModel createOrganization(OrganizationRepresentation representation) {
        OrganizationModel organization = organizationProvider.addOrganization(representation.getName(), OrganizationType.common);
        RepresentationToModel.updateOrganization(representation, organization);

        // Certificate
        if (componentProvider.getComponents(organization, organization.getId(), KeyProvider.class.getName()).isEmpty()) {
            try {
                defaultKeyProviders.createProviders(organization);
            } catch (ModelException e) {
                throw new ModelRuntimeException("Could not create certificates", e);
            }
        }

        return organization;
    }
}
