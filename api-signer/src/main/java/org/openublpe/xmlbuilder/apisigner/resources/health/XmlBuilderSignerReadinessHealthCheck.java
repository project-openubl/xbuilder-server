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
package org.openublpe.xmlbuilder.apisigner.resources.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.openublpe.xmlbuilder.apisigner.models.OrganizationModel;
import org.openublpe.xmlbuilder.apisigner.models.OrganizationProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

@Readiness
@ApplicationScoped
public class XmlBuilderSignerReadinessHealthCheck implements HealthCheck {

    @Inject
    OrganizationProvider organizationProvider;

    @Override
    public HealthCheckResponse call() {
        Optional<OrganizationModel> organization = organizationProvider.getOrganizationById(OrganizationModel.MASTER_ID);
        HealthCheckResponse response;
        if (organization.isPresent()) {
            response = HealthCheckResponse.up("Server readiness running");
        } else {
            response = HealthCheckResponse.down("Server readiness failed");
        }
        return response;
    }

}
