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
import java.util.Optional;

@Transactional
@ApplicationScoped
public class OrganizationManager {

    @Inject
    private ComponentProvider componentProvider;

    @Inject
    private DefaultKeyProviders defaultKeyProviders;

    @Inject
    private OrganizationProvider organizationProvider;

    public OrganizationModel createOrganization(OrganizationRepresentation representation) {
        OrganizationType organizationType = Optional.ofNullable(representation.getType()).map(f -> OrganizationType.valueOf(f.toLowerCase())).orElse(OrganizationType.common);
        OrganizationModel organization = organizationProvider.addOrganization(representation.getName(), organizationType);
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
