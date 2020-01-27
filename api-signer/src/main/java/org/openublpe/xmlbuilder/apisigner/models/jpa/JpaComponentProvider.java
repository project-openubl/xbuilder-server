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

import org.keycloak.common.util.MultivaluedHashMap;
import org.openublpe.xmlbuilder.apisigner.keys.component.ComponentFactory;
import org.openublpe.xmlbuilder.apisigner.keys.component.ComponentModel;
import org.openublpe.xmlbuilder.apisigner.keys.component.utils.ComponentUtil;
import org.openublpe.xmlbuilder.apisigner.models.ComponentProvider;
import org.openublpe.xmlbuilder.apisigner.models.ModelException;
import org.openublpe.xmlbuilder.apisigner.models.OrganizationModel;
import org.openublpe.xmlbuilder.apisigner.models.jpa.entities.ComponentConfigEntity;
import org.openublpe.xmlbuilder.apisigner.models.jpa.entities.ComponentEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Transactional
@ApplicationScoped
public class JpaComponentProvider implements ComponentProvider {

    /**
     * Components
     */
    public static final String COMPONENT_PROVIDER_EXISTS_DISABLED = "component.provider.exists.disabled";

    @Inject
    EntityManager em;

    @Inject
    ComponentUtil componentUtil;

    @Override
    public ComponentModel addComponentModel(OrganizationModel organization, ComponentModel model) throws ModelException {
        model = importComponentModel(organization, model);
        componentUtil.notifyCreated(organization, model);
        return model;
    }

    @Override
    public ComponentModel importComponentModel(OrganizationModel organization, ComponentModel model) throws ModelException {
        ComponentFactory componentFactory = null;
        try {
            componentFactory = componentUtil.getComponentFactory(model);
            if (componentFactory == null && System.getProperty(COMPONENT_PROVIDER_EXISTS_DISABLED) == null) {
                throw new IllegalArgumentException("Invalid component type");
            }
            if (componentFactory != null) {
                componentFactory.validateConfiguration(organization, model);
            }
        } catch (Exception e) {
            if (System.getProperty(COMPONENT_PROVIDER_EXISTS_DISABLED) == null) {
                throw e;
            }
        }

        ComponentEntity c = new ComponentEntity();
        if (model.getId() == null) {
            c.setId(UUID.randomUUID().toString());
        } else {
            c.setId(model.getId());
        }
        c.setName(model.getName());
        c.setParentId(model.getParentId());
        if (model.getParentId() == null) {
            c.setParentId(organization.getId());
            model.setParentId(organization.getId());
        }
        c.setProviderType(model.getProviderType());
        c.setProviderId(model.getProviderId());
        c.setSubType(model.getSubType());
        c.setOrganization(OrganizationAdapter.toEntity(organization, em));
        em.persist(c);
        setConfig(model, c);
        model.setId(c.getId());
        return model;
    }

    protected void setConfig(ComponentModel model, ComponentEntity c) {
        for (String key : model.getConfig().keySet()) {
            List<String> vals = model.getConfig().get(key);
            if (vals == null) {
                continue;
            }
            for (String val : vals) {
                ComponentConfigEntity config = new ComponentConfigEntity();
                config.setId(UUID.randomUUID().toString());
                config.setName(key);
                config.setValue(val);
                config.setComponent(c);
                em.persist(config);
            }
        }
    }

    @Override
    public void updateComponent(OrganizationModel organization, ComponentModel component) throws ModelException {
        componentUtil.getComponentFactory(component).validateConfiguration(organization, component);

        ComponentEntity c = em.find(ComponentEntity.class, component.getId());
        if (c == null) return;
        c.setName(component.getName());
        c.setProviderId(component.getProviderId());
        c.setProviderType(component.getProviderType());
        c.setParentId(component.getParentId());
        c.setSubType(component.getSubType());
        em.createNamedQuery("deleteComponentConfigByComponent").setParameter("component", c).executeUpdate();
        em.flush();
        setConfig(component, c);
        componentUtil.notifyUpdated(organization, component);
    }

    @Override
    public void removeComponent(OrganizationModel organization, ComponentModel component) {
        ComponentEntity c = em.find(ComponentEntity.class, component.getId());
        if (c == null) return;
        removeComponents(organization, component.getId());
        em.createNamedQuery("deleteComponentConfigByComponent").setParameter("component", c).executeUpdate();
        em.createNamedQuery("deleteComponent").setParameter("component", c).executeUpdate();
    }

    @Override
    public void removeComponents(OrganizationModel organization, String parentId) {
        TypedQuery<String> query = em.createNamedQuery("getComponentIdsByParent", String.class)
                .setParameter("organization", OrganizationAdapter.toEntity(organization, em))
                .setParameter("parentId", parentId);

        List<String> results = query.getResultList();
        if (results.isEmpty()) return;
        em.createNamedQuery("deleteComponentConfigByParent").setParameter("parentId", parentId).executeUpdate();
        em.createNamedQuery("deleteComponentByParent").setParameter("parentId", parentId).executeUpdate();
    }

    @Override
    public List<ComponentModel> getComponents(OrganizationModel organization, String parentId, String providerType) {
        if (parentId == null) parentId = organization.getId();

        TypedQuery<ComponentEntity> query = em.createNamedQuery("getComponentsByParentAndType", ComponentEntity.class)
                .setParameter("organization", OrganizationAdapter.toEntity(organization, em))
                .setParameter("parentId", parentId)
                .setParameter("providerType", providerType);

        List<ComponentEntity> results = query.getResultList();
        List<ComponentModel> rtn = new LinkedList<>();
        for (ComponentEntity c : results) {
            ComponentModel model = entityToModel(c);
            rtn.add(model);

        }
        return rtn;
    }

    @Override
    public List<ComponentModel> getComponents(OrganizationModel organization, String parentId) {
        TypedQuery<ComponentEntity> query = em.createNamedQuery("getComponentByParent", ComponentEntity.class)
                .setParameter("organization", OrganizationAdapter.toEntity(organization, em))
                .setParameter("parentId", parentId);

        List<ComponentEntity> results = query.getResultList();
        List<ComponentModel> rtn = new LinkedList<>();
        for (ComponentEntity c : results) {
            ComponentModel model = entityToModel(c);
            rtn.add(model);

        }
        return rtn;
    }

    protected ComponentModel entityToModel(ComponentEntity c) {
        ComponentModel model = new ComponentModel();
        model.setId(c.getId());
        model.setName(c.getName());
        model.setProviderType(c.getProviderType());
        model.setProviderId(c.getProviderId());
        model.setSubType(c.getSubType());
        model.setParentId(c.getParentId());
        MultivaluedHashMap<String, String> config = new MultivaluedHashMap<>();

        TypedQuery<ComponentConfigEntity> configQuery = em.createNamedQuery("getComponentConfig", ComponentConfigEntity.class)
                .setParameter("component", c);

        List<ComponentConfigEntity> configResults = configQuery.getResultList();
        for (ComponentConfigEntity configEntity : configResults) {
            config.add(configEntity.getName(), configEntity.getValue());
        }
        model.setConfig(config);
        return model;
    }

    @Override
    public List<ComponentModel> getComponents(OrganizationModel organization) {
        TypedQuery<ComponentEntity> query = em.createNamedQuery("getComponents", ComponentEntity.class)
                .setParameter("organization", OrganizationAdapter.toEntity(organization, em));

        List<ComponentEntity> results = query.getResultList();
        List<ComponentModel> rtn = new LinkedList<>();
        for (ComponentEntity c : results) {
            ComponentModel model = entityToModel(c);
            rtn.add(model);
        }
        return rtn;
    }

    @Override
    public ComponentModel getComponent(OrganizationModel organization, String id) {
        ComponentEntity c = em.find(ComponentEntity.class, id);
        if (c == null) return null;
        return entityToModel(c);
    }

}
