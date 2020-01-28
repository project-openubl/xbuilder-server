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
package org.openublpe.xmlbuilder.apisigner.models.jpa;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import org.openublpe.xmlbuilder.apisigner.models.OrganizationModel;
import org.openublpe.xmlbuilder.apisigner.models.OrganizationProvider;
import org.openublpe.xmlbuilder.apisigner.models.OrganizationType;
import org.openublpe.xmlbuilder.apisigner.models.SearchResultsModel;
import org.openublpe.xmlbuilder.apisigner.models.jpa.entities.OrganizationEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public Optional<OrganizationModel> getOrganizationById(String id) {
        OrganizationEntity organizationEntity = em.find(OrganizationEntity.class, id);
        if (organizationEntity == null) return Optional.empty();
        return Optional.of(new OrganizationAdapter(organizationEntity));
    }

    @Override
    public Optional<OrganizationModel> getOrganizationByName(String name) {
        TypedQuery<OrganizationEntity> query = em.createNamedQuery("FindByName", OrganizationEntity.class);
        query.setParameter("name", name);

        List<OrganizationEntity> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return Optional.empty();
        } else if (resultList.size() == 1) {
            return Optional.of(new OrganizationAdapter(resultList.get(0)));
        } else {
            throw new IllegalStateException("More than one Organizacion with name=" + name);
        }
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

    @Override
    public SearchResultsModel<OrganizationModel> searchOrganizations(int page, int pageSize) {
        String query = "from OrganizationEntity o";

        PanacheQuery<OrganizationEntity> panacheQuery = OrganizationEntity.find(query);
        panacheQuery.page(new Page(page, pageSize));

        long count = panacheQuery.count();
        List<OrganizationModel> list = panacheQuery.list()
                .stream()
                .map(OrganizationAdapter::new)
                .collect(Collectors.toList());

        return new SearchResultsModel<>(count, list);
    }

    @Override
    public SearchResultsModel<OrganizationModel> searchOrganizations(String filterText, int page, int pageSize) {
        String query = "from OrganizationEntity o where lower(o.name) like :filterText";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("filterText", "%" + filterText.toLowerCase() + "%");

        PanacheQuery<OrganizationEntity> panacheQuery = OrganizationEntity.find(query, parameters);
        panacheQuery.page(new Page(page, pageSize));

        long count = panacheQuery.count();
        List<OrganizationModel> list = panacheQuery.list()
                .stream()
                .map(OrganizationAdapter::new)
                .collect(Collectors.toList());

        return new SearchResultsModel<>(count, list);
    }

    @Override
    public void deleteOrganization(OrganizationModel model) {
        OrganizationEntity organization = OrganizationEntity.findById(model.getId());

        em.createNamedQuery("deleteComponentConfigByOrganization").setParameter("organization", organization).executeUpdate();
        em.createNamedQuery("deleteComponentByOrganization").setParameter("organization", organization).executeUpdate();
        em.createNamedQuery("DeleteOrganization").setParameter("organization", organization).executeUpdate();
    }

}
