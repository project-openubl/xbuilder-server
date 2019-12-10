package org.openublpe.xmlbuilder.apisigner.managers;

import org.openublpe.xmlbuilder.apisigner.keys.KeyProvider;
import org.openublpe.xmlbuilder.apisigner.models.ComponentProvider;
import org.openublpe.xmlbuilder.apisigner.models.ModelException;
import org.openublpe.xmlbuilder.apisigner.models.ModelRuntimeException;
import org.openublpe.xmlbuilder.apisigner.models.OrganizationModel;
import org.openublpe.xmlbuilder.apisigner.models.OrganizationProvider;
import org.openublpe.xmlbuilder.apisigner.models.OrganizationType;
import org.openublpe.xmlbuilder.apisigner.models.utils.DefaultKeyProviders;
import org.openublpe.xmlbuilder.apisigner.models.utils.RepresentationToModel;
import org.openublpe.xmlbuilder.apisigner.representations.idm.OrganizationRepresentation;

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
