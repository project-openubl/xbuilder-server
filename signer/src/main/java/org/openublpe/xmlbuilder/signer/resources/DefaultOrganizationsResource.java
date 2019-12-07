package org.openublpe.xmlbuilder.signer.resources;

import org.keycloak.common.util.PemUtils;
import org.keycloak.jose.jws.AlgorithmType;
import org.keycloak.representations.idm.KeysMetadataRepresentation;
import org.openublpe.xmlbuilder.signer.keys.RsaKeyMetadata;
import org.openublpe.xmlbuilder.signer.keys.component.ComponentModel;
import org.openublpe.xmlbuilder.signer.keys.component.utils.ComponentUtil;
import org.openublpe.xmlbuilder.signer.managers.OrganizationManager;
import org.openublpe.xmlbuilder.signer.models.ComponentProvider;
import org.openublpe.xmlbuilder.signer.models.KeyManager;
import org.openublpe.xmlbuilder.signer.models.ModelException;
import org.openublpe.xmlbuilder.signer.models.OrganizationModel;
import org.openublpe.xmlbuilder.signer.models.OrganizationProvider;
import org.openublpe.xmlbuilder.signer.models.utils.ModelToRepresentation;
import org.openublpe.xmlbuilder.signer.models.utils.RepresentationToModel;
import org.openublpe.xmlbuilder.signer.representations.idm.ComponentRepresentation;
import org.openublpe.xmlbuilder.signer.representations.idm.OrganizationRepresentation;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional
@ApplicationScoped
public class DefaultOrganizationsResource implements OrganizationsResource {

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

    @Override
    public OrganizationRepresentation createOrganization(@Valid OrganizationRepresentation representation) {
        organizationProvider.getOrganizationByName(representation.getName()).ifPresent(organization -> {
            throw new BadRequestException("Organization Name already registered");
        });

        OrganizationModel organization = organizationManager.createOrganization(representation);
        return ModelToRepresentation.toRepresentation(organization, true);
    }

    @Override
    public List<OrganizationRepresentation> getOrganizations(String organizationId, String filterText, int offset, int limit) {
        if (organizationId != null) {
            return organizationProvider.getOrganization(organizationId)
                    .map(organizationModel -> Collections.singletonList(ModelToRepresentation.toRepresentation(organizationModel, false)))
                    .orElseGet(Collections::emptyList);
        }

        if (filterText != null) {
            return organizationProvider.getOrganizations(filterText, offset, limit)
                    .stream()
                    .map(model -> ModelToRepresentation.toRepresentation(model, false))
                    .collect(Collectors.toList());
        } else {
            return organizationProvider.getOrganizations(offset, limit)
                    .stream()
                    .map(model -> ModelToRepresentation.toRepresentation(model, false))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public OrganizationRepresentation getOrganization(String organizationId) {
        return organizationProvider.getOrganization(organizationId)
                .map(organizationModel -> ModelToRepresentation.toRepresentation(organizationModel, true))
                .orElseThrow(() -> new NotFoundException("Organization not found"));
    }

    @Override
    public OrganizationRepresentation updateOrganization(String organizationId, OrganizationRepresentation rep) {
        OrganizationModel organization = organizationProvider.getOrganization(organizationId).orElseThrow(() -> new NotFoundException("Organization not found"));
        RepresentationToModel.updateOrganization(rep, organization);
        return ModelToRepresentation.toRepresentation(organization, true);
    }

    @Override
    public void deleteOrganization(String organizationId) {
        throw new ForbiddenException();
    }

    @Override
    public KeysMetadataRepresentation getKeyMetadata(String organizationId) {
        OrganizationModel organization = organizationProvider.getOrganization(organizationId).orElseThrow(() -> new NotFoundException("Organization not found"));

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

    @Override
    public List<ComponentRepresentation> getComponents(String organizationId, String parent, String type) {
        OrganizationModel organization = organizationProvider.getOrganization(organizationId).orElseThrow(() -> new NotFoundException("Organization not found"));

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

    @Override
    public Response createComponent(String organizationId, ComponentRepresentation rep) {
        OrganizationModel organization = organizationProvider.getOrganization(organizationId).orElseThrow(() -> new NotFoundException("Organization not found"));

        try {
            ComponentModel model = RepresentationToModel.toModel(rep);
            if (model.getParentId() == null) model.setParentId(organization.getId());

            model = componentProvider.addComponentModel(organization, model);

            return Response.created(uriInfo.getAbsolutePathBuilder().path(model.getId()).build()).build();
        } catch (ModelException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public ComponentRepresentation getComponent(String organizationId, String componentId) {
        OrganizationModel organization = organizationProvider.getOrganization(organizationId).orElseThrow(() -> new NotFoundException("Organization not found"));

        ComponentModel model = componentProvider.getComponent(organization, componentId);
        if (model == null) {
            throw new NotFoundException("Could not find component");
        }

        return ModelToRepresentation.toRepresentation(model, false, componentUtil);
    }

    @Override
    public Response updateComponent(String organizationId, String componentId, ComponentRepresentation rep) {
        OrganizationModel organization = organizationProvider.getOrganization(organizationId).orElseThrow(() -> new NotFoundException("Organization not found"));

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

    @Override
    public void removeComponent(String organizationId, String componentId) {
        OrganizationModel organization = organizationProvider.getOrganization(organizationId).orElseThrow(() -> new NotFoundException("Organization not found"));

        ComponentModel model = componentProvider.getComponent(organization, componentId);
        if (model == null) {
            throw new NotFoundException("Could not find component");
        }

        componentProvider.removeComponent(organization, model);
    }

}
