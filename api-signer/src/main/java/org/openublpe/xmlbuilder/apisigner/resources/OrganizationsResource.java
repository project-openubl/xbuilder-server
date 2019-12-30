package org.openublpe.xmlbuilder.apisigner.resources;

import org.keycloak.common.util.PemUtils;
import org.keycloak.jose.jws.AlgorithmType;
import org.keycloak.representations.idm.KeysMetadataRepresentation;
import org.openublpe.xmlbuilder.apisigner.keys.RsaKeyMetadata;
import org.openublpe.xmlbuilder.apisigner.keys.component.ComponentModel;
import org.openublpe.xmlbuilder.apisigner.keys.component.utils.ComponentUtil;
import org.openublpe.xmlbuilder.apisigner.managers.OrganizationManager;
import org.openublpe.xmlbuilder.apisigner.models.ComponentProvider;
import org.openublpe.xmlbuilder.apisigner.models.KeyManager;
import org.openublpe.xmlbuilder.apisigner.models.ModelException;
import org.openublpe.xmlbuilder.apisigner.models.OrganizationModel;
import org.openublpe.xmlbuilder.apisigner.models.OrganizationProvider;
import org.openublpe.xmlbuilder.apisigner.models.SearchResultsModel;
import org.openublpe.xmlbuilder.apisigner.models.utils.ModelToRepresentation;
import org.openublpe.xmlbuilder.apisigner.models.utils.RepresentationToModel;
import org.openublpe.xmlbuilder.apisigner.representations.idm.ComponentRepresentation;
import org.openublpe.xmlbuilder.apisigner.representations.idm.OrganizationRepresentation;
import org.openublpe.xmlbuilder.apisigner.representations.idm.SearchResultsRepresentation;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@ApplicationScoped
@Path("organizations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrganizationsResource {

    @Context
    UriInfo uriInfo;

    @Inject
    KeyManager keystore;

    @Inject
    ComponentUtil componentUtil;

    @Inject
    ComponentProvider componentProvider;

    @Inject
    OrganizationManager organizationManager;

    @Inject
    OrganizationProvider organizationProvider;

    @POST
    @Path("/")
    public OrganizationRepresentation createOrganization(@Valid OrganizationRepresentation representation) {
        if (organizationProvider.getOrganizationByName(representation.getName()).isPresent()) {
            throw new BadRequestException("Organization with name=" + representation.getName() + " already exists");
        }
        OrganizationModel organization = organizationManager.createOrganization(representation);
        return ModelToRepresentation.toRepresentation(organization, true);
    }

    @GET
    @Path("/")
    public List<OrganizationRepresentation> getOrganizations(
            @QueryParam("organizationId") String organizationId,
            @QueryParam("filterText") String filterText,
            @QueryParam("offset") @DefaultValue("0") int offset,
            @QueryParam("limit") @DefaultValue("10") int limit
    ) {
        if (organizationId != null) {
            return organizationProvider.getOrganizationById(organizationId)
                    .map(organizationModel -> Collections.singletonList(ModelToRepresentation.toRepresentation(organizationModel, true)))
                    .orElseGet(Collections::emptyList);
        }

        if (filterText != null) {
            return organizationProvider.getOrganizations(filterText, offset, limit)
                    .stream()
                    .map(model -> ModelToRepresentation.toRepresentation(model, true))
                    .collect(Collectors.toList());
        } else {
            return organizationProvider.getOrganizations(offset, limit)
                    .stream()
                    .map(model -> ModelToRepresentation.toRepresentation(model, true))
                    .collect(Collectors.toList());
        }
    }

    @GET
    @Path("/search")
    public SearchResultsRepresentation<OrganizationRepresentation> searchOrganizations(
            @QueryParam("filterText") String filterText,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize
    ) {
        SearchResultsModel<OrganizationModel> results;
        if (filterText != null && !filterText.trim().isEmpty()) {
            results = organizationProvider.searchOrganizations(filterText, page, pageSize);
        } else {
            results = organizationProvider.searchOrganizations(page, pageSize);
        }

        return new SearchResultsRepresentation<>(
                results.getTotalSize(),
                results.getModels().stream()
                        .map(model -> ModelToRepresentation.toRepresentation(model, true))
                        .collect(Collectors.toList())
        );
    }

    @GET
    @Path("/all")
    public List<OrganizationRepresentation> getAllOrganizations() {
        return organizationProvider.getOrganizations(-1, -1)
                .stream()
                .map(model -> ModelToRepresentation.toRepresentation(model, true))
                .collect(Collectors.toList());
    }

    @GET
    @Path("/id-by-name/{organizationName}")
    public String getOrganizationIdByName(
            @PathParam("organizationName") String organizationName
    ) {
        return organizationProvider.getOrganizationByName(organizationName)
                .map(OrganizationModel::getId)
                .orElse(null);
    }

    @GET
    @Path("/{organizationId}")
    public OrganizationRepresentation getOrganization(
            @PathParam("organizationId") String organizationId
    ) {
        return organizationProvider.getOrganizationById(organizationId)
                .map(organizationModel -> ModelToRepresentation.toRepresentation(organizationModel, true))
                .orElseThrow(() -> new NotFoundException("Organization not found"));
    }

    @PUT
    @Path("/{organizationId}")
    public OrganizationRepresentation updateOrganization(
            @PathParam("organizationId") String organizationId,
            OrganizationRepresentation rep
    ) {
        OrganizationModel organization = organizationProvider.getOrganizationById(organizationId).orElseThrow(() -> new NotFoundException("Organization not found"));
        RepresentationToModel.updateOrganization(rep, organization);
        return ModelToRepresentation.toRepresentation(organization, true);
    }

    @DELETE
    @Path("/{organizationId}")
    public void deleteOrganization(
            @PathParam("organizationId") String organizationId
    ) {
        throw new ForbiddenException();
    }

    @GET
    @Path("/{organizationId}/keys")
    @Produces(MediaType.APPLICATION_JSON)
    public KeysMetadataRepresentation getKeyMetadata(
            @PathParam("organizationId") final String organizationId
    ) {
        OrganizationModel organization = organizationProvider.getOrganizationById(organizationId).orElseThrow(() -> new NotFoundException("Organization not found"));

        KeysMetadataRepresentation keys = new KeysMetadataRepresentation();

        Map<String, String> active = new HashMap<>();
        active.put(AlgorithmType.RSA.name(), keystore.getActiveRsaKey(organization).getKid());
        keys.setActive(active);

        List<KeysMetadataRepresentation.KeyMetadataRepresentation> l = new LinkedList<>();
        for (RsaKeyMetadata m : keystore.getRsaKeys(organization, true)) {
            KeysMetadataRepresentation.KeyMetadataRepresentation r = new KeysMetadataRepresentation.KeyMetadataRepresentation();
            r.setProviderId(m.getProviderId());
            r.setProviderPriority(m.getProviderPriority());
            r.setKid(m.getKid());
            r.setStatus(m.getStatus() != null ? m.getStatus().name() : null);
            r.setType(AlgorithmType.RSA.name());
            r.setPublicKey(PemUtils.encodeKey(m.getPublicKey()));
            r.setCertificate(PemUtils.encodeCertificate(m.getCertificate()));
            l.add(r);
        }

        keys.setKeys(l);

        return keys;
    }

    @GET
    @Path("/{organizationId}/components")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ComponentRepresentation> getComponents(
            @PathParam("organizationId") final String organizationId,
            @QueryParam("parent") String parent,
            @QueryParam("type") String type
    ) {
        OrganizationModel organization = organizationProvider.getOrganizationById(organizationId).orElseThrow(() -> new NotFoundException("Organization not found"));

        List<ComponentModel> components;
        if (parent == null && type == null) {
            components = componentProvider.getComponents(organization);
        } else if (type == null) {
            components = componentProvider.getComponents(organization, parent);
        } else if (parent == null) {
            components = componentProvider.getComponents(organization, organization.getId(), type);
        } else {
            components = componentProvider.getComponents(organization, parent, type);
        }
        List<ComponentRepresentation> reps = new LinkedList<>();
        for (ComponentModel component : components) {
            ComponentRepresentation rep = ModelToRepresentation.toRepresentation(component, false, componentUtil);
            reps.add(rep);
        }
        return reps;
    }

    @POST
    @Path("/{organizationId}/components")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createComponent(
            @PathParam("organizationId") final String organizationId, ComponentRepresentation rep
    ) {
        OrganizationModel organization = organizationProvider.getOrganizationById(organizationId).orElseThrow(() -> new NotFoundException("Organization not found"));

        try {
            ComponentModel model = RepresentationToModel.toModel(rep);
            if (model.getParentId() == null) model.setParentId(organization.getId());

            model = componentProvider.addComponentModel(organization, model);

            return Response.created(uriInfo.getAbsolutePathBuilder().path(model.getId()).build()).build();
        } catch (ModelException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @GET
    @Path("/{organizationId}/components/{componentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ComponentRepresentation getComponent(
            @PathParam("organizationId") final String organizationId,
            @PathParam("componentId") String componentId
    ) {
        OrganizationModel organization = organizationProvider.getOrganizationById(organizationId).orElseThrow(() -> new NotFoundException("Organization not found"));

        ComponentModel model = componentProvider.getComponent(organization, componentId);
        if (model == null) {
            throw new NotFoundException("Could not find component");
        }

        return ModelToRepresentation.toRepresentation(model, false, componentUtil);
    }

    @PUT
    @Path("/{organizationId}/components/{componentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateComponent(
            @PathParam("organizationId") final String organizationId,
            @PathParam("componentId") String componentId, ComponentRepresentation rep
    ) {
        OrganizationModel organization = organizationProvider.getOrganizationById(organizationId).orElseThrow(() -> new NotFoundException("Organization not found"));

        try {
            ComponentModel model = componentProvider.getComponent(organization, componentId);
            if (model == null) {
                throw new NotFoundException("Could not find component");
            }
            RepresentationToModel.updateComponent(rep, model, false, componentUtil);

            componentProvider.updateComponent(organization, model);
            return Response.noContent().build();
        } catch (ModelException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @DELETE
    @Path("/{organizationId}/components/{componentId}")
    public void removeComponent(
            @PathParam("organizationId") final String organizationId,
            @PathParam("componentId") String componentId
    ) {
        OrganizationModel organization = organizationProvider.getOrganizationById(organizationId).orElseThrow(() -> new NotFoundException("Organization not found"));

        ComponentModel model = componentProvider.getComponent(organization, componentId);
        if (model == null) {
            throw new NotFoundException("Could not find component");
        }

        componentProvider.removeComponent(organization, model);
    }

}
