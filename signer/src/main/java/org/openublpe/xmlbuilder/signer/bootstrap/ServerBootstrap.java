package org.openublpe.xmlbuilder.signer.bootstrap;

import io.quarkus.runtime.StartupEvent;
import org.jboss.logging.Logger;
import org.openublpe.xmlbuilder.signer.models.ModelException;
import org.openublpe.xmlbuilder.signer.models.ModelRuntimeException;
import org.openublpe.xmlbuilder.signer.models.OrganizationModel;
import org.openublpe.xmlbuilder.signer.models.OrganizationProvider;
import org.openublpe.xmlbuilder.signer.models.OrganizationType;
import org.openublpe.xmlbuilder.signer.models.utils.DefaultKeyProviders;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Checks if MASTER organization already exists
 * (if MASTER organization does not exists, it means is the first time the server is being bootstrapped).
 * If is the first time the server is being bootstrapped, then it will create default roles:
 * 1. ROLE "owner" with PERMISSIONS: "organization_admin, organization_edit, organization_view, component_manage, component_view, etc."
 * 2. ROLE: "collaborator" with PERMISSIONS: "document_manage, document_view, etc."
 */
@ApplicationScoped
public class ServerBootstrap {

    private static final Logger logger = Logger.getLogger(ServerBootstrap.class);

    @Inject
    DefaultKeyProviders defaultKeyProviders;

    @Inject
    OrganizationProvider organizationProvider;

    void onStart(@Observes StartupEvent ev) {
        logger.info("Server Bootstrap...");
        bootstrap();
    }

    private void bootstrap() {
        Optional<OrganizationModel> organization = organizationProvider.getOrganization(OrganizationModel.MASTER_ID);
        if (!organization.isPresent()) {
            createMasterOrganization();
        }
    }

    private void createMasterOrganization() {
        logger.info("Initializing Admin Organization " + OrganizationModel.MASTER_ID);

        try {
            OrganizationModel organization = organizationProvider.addOrganization(OrganizationModel.MASTER_ID, OrganizationModel.MASTER_ID, OrganizationType.master);
            organization.setUseCustomCertificates(true);

            defaultKeyProviders.createProviders(organization);
        } catch (ModelException e) {
            throw new ModelRuntimeException("Could not configure master organization", e);
        }
    }
}
