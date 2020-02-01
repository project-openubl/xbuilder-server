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
