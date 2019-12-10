package org.openublpe.xmlbuilder.signer.models;

import java.util.List;
import java.util.Optional;

public interface OrganizationProvider {

    /**
     * Used for create master organization
     */
    OrganizationModel addOrganization(String id, String name, OrganizationType type);

    OrganizationModel addOrganization(String name, OrganizationType type);

    Optional<OrganizationModel> getOrganization(String id);

    List<OrganizationModel> getOrganizations(int offset, int limit);

    List<OrganizationModel> getOrganizations(String filterText, int offset, int limit);
}
