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
package org.openublpe.xmlbuilder.apisigner.bootstrap;

import io.quarkus.runtime.StartupEvent;
import org.jboss.logging.Logger;
import org.openublpe.xmlbuilder.apisigner.models.ModelException;
import org.openublpe.xmlbuilder.apisigner.models.ModelRuntimeException;
import org.openublpe.xmlbuilder.apisigner.models.OrganizationModel;
import org.openublpe.xmlbuilder.apisigner.models.OrganizationProvider;
import org.openublpe.xmlbuilder.apisigner.models.OrganizationType;
import org.openublpe.xmlbuilder.apisigner.models.utils.DefaultKeyProviders;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Optional;

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
        Optional<OrganizationModel> organization = organizationProvider.getOrganizationById(OrganizationModel.MASTER_ID);
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
