package org.openublpe.xmlbuilder.signer.resources;

import org.keycloak.representations.idm.KeysMetadataRepresentation;
import org.openublpe.xmlbuilder.signer.representations.idm.ComponentRepresentation;
import org.openublpe.xmlbuilder.signer.representations.idm.OrganizationRepresentation;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("organizations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface OrganizationsResource {

    @POST
    @Path("/")
    OrganizationRepresentation createOrganization(@Valid OrganizationRepresentation organizationRepresentation);

    @GET
    @Path("/")
    List<OrganizationRepresentation> getOrganizations(
            @QueryParam("organizationId") String organizationId,
            @QueryParam("filterText") String filterText,
            @QueryParam("offset") @DefaultValue("0") int offset,
            @QueryParam("limit") @DefaultValue("10") int limit);

    @GET
    @Path("/{organizationId}")
    OrganizationRepresentation getOrganization(
            @PathParam("organizationId") String organizationId
    );

    @PUT
    @Path("/{organizationId}")
    OrganizationRepresentation updateOrganization(
            @PathParam("organizationId") String organizationId,
            OrganizationRepresentation rep
    );

    @DELETE
    @Path("/{organizationId}")
    void deleteOrganization(
            @PathParam("organizationId") String organizationId
    );

    @GET
    @Path("/{organizationId}/keys")
    @Produces(MediaType.APPLICATION_JSON)
    KeysMetadataRepresentation getKeyMetadata(
            @PathParam("organizationId") final String organizationId
    );

    @GET
    @Path("/{organizationId}/components")
    @Produces(MediaType.APPLICATION_JSON)
    List<ComponentRepresentation> getComponents(
            @PathParam("organizationId") final String organizationId,
            @QueryParam("parent") String parent,
            @QueryParam("type") String type
    );

    @POST
    @Path("/{organizationId}/components")
    @Consumes(MediaType.APPLICATION_JSON)
    Response createComponent(
            @PathParam("organizationId") final String organizationId, ComponentRepresentation rep
    );

    @GET
    @Path("/{organizationId}/components/{componentId}")
    @Produces(MediaType.APPLICATION_JSON)
    ComponentRepresentation getComponent(
            @PathParam("organizationId") final String organizationId,
            @PathParam("componentId") String componentId
    );

    @PUT
    @Path("/{organizationId}/components/{componentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateComponent(
            @PathParam("organizationId") final String organizationId,
            @PathParam("componentId") String componentId, ComponentRepresentation rep
    );

    @DELETE
    @Path("/{organizationId}/components/{componentId}")
    void removeComponent(
            @PathParam("organizationId") final String organizationId,
            @PathParam("componentId") String componentId
    );

}
