package org.openublpe.xmlbuilder.apisigner.models;

import java.util.List;
import java.util.Optional;

public interface OrganizationProvider {

    /**
     * Used for create master organization
     */
    OrganizationModel addOrganization(String id, String name, OrganizationType type);

    OrganizationModel addOrganization(String name, OrganizationType type);

    Optional<OrganizationModel> getOrganizationById(String id);

    Optional<OrganizationModel> getOrganizationByName(String name);

    List<OrganizationModel> getOrganizations(int offset, int limit);

    List<OrganizationModel> getOrganizations(String filterText, int offset, int limit);

    SearchResultsModel<OrganizationModel> searchOrganizations(int page, int pageSize);

    SearchResultsModel<OrganizationModel> searchOrganizations(String filterText, int page, int pageSize);
}
