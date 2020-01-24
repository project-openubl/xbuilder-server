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
