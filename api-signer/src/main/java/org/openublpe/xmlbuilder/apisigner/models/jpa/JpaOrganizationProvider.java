package org.openublpe.xmlbuilder.apisigner.models.jpa;

import org.openublpe.xmlbuilder.apisigner.models.OrganizationModel;
import org.openublpe.xmlbuilder.apisigner.models.OrganizationProvider;
import org.openublpe.xmlbuilder.apisigner.models.OrganizationType;
import org.openublpe.xmlbuilder.apisigner.models.jpa.entities.OrganizationEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@ApplicationScoped
public class JpaOrganizationProvider implements OrganizationProvider {

    @Inject
    EntityManager em;

    @Override
    public OrganizationModel addOrganization(String id, String name, OrganizationType type) {
        OrganizationEntity entity = new OrganizationEntity();
        entity.setId(id);
        entity.setName(name);
        entity.setType(type);
        em.persist(entity);

        return new OrganizationAdapter(entity);
    }

    @Override
    public OrganizationModel addOrganization(String name, OrganizationType type) {
        return addOrganization(UUID.randomUUID().toString(), name, type);
    }

    @Override
    public Optional<OrganizationModel> getOrganization(String id) {
        OrganizationEntity organizationEntity = em.find(OrganizationEntity.class, id);
        if (organizationEntity == null) return Optional.empty();
        return Optional.of(new OrganizationAdapter(organizationEntity));
    }

    @Override
    public List<OrganizationModel> getOrganizations(int offset, int limit) {
        TypedQuery<OrganizationEntity> query = em.createNamedQuery("ListOrganizations", OrganizationEntity.class);
        if (offset != -1) {
            query.setFirstResult(offset);
        }
        if (limit != -1) {
            query.setMaxResults(limit);
        }
        return query.getResultList()
                .stream()
                .map(OrganizationAdapter::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrganizationModel> getOrganizations(String filterText, int offset, int limit) {
        TypedQuery<OrganizationEntity> query = em.createNamedQuery("FilterOrganizations", OrganizationEntity.class);
        query.setParameter("filterText", "%" + filterText.toLowerCase());
        if (offset != -1) {
            query.setFirstResult(offset);
        }
        if (limit != -1) {
            query.setMaxResults(limit);
        }
        return query.getResultList()
                .stream()
                .map(OrganizationAdapter::new)
                .collect(Collectors.toList());
    }

}
