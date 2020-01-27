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

import org.openublpe.xmlbuilder.apisigner.models.ModelType;
import org.openublpe.xmlbuilder.apisigner.models.OrganizationModel;
import org.openublpe.xmlbuilder.apisigner.models.OrganizationType;
import org.openublpe.xmlbuilder.apisigner.models.jpa.entities.OrganizationEntity;

import javax.persistence.EntityManager;

public class OrganizationAdapter implements OrganizationModel, JpaModel<OrganizationEntity> {

    private final OrganizationEntity organization;

    public OrganizationAdapter(OrganizationEntity organization) {
        this.organization = organization;
    }

    public static OrganizationEntity toEntity(OrganizationModel model, EntityManager em) {
        if (model instanceof OrganizationAdapter) {
            return ((OrganizationAdapter) model).getEntity();
        }
        return em.getReference(OrganizationEntity.class, model.getId());
    }

    @Override
    public OrganizationEntity getEntity() {
        return organization;
    }

    @Override
    public String getId() {
        return organization.getId();
    }

    @Override
    public ModelType getModelType() {
        return ModelType.ORGANIZATION;
    }

    @Override
    public OrganizationType getType() {
        return organization.getType();
    }

    @Override
    public String getName() {
        return organization.getName();
    }

    @Override
    public void setName(String name) {
        organization.setName(name);
    }

    @Override
    public String getDescription() {
        return organization.getDescription();
    }

    @Override
    public void setDescription(String description) {
        organization.setDescription(description);
    }

    @Override
    public boolean getUseCustomCertificates() {
        return organization.isUseCustomCertificates();
    }

    @Override
    public void setUseCustomCertificates(boolean useCustomCertificates) {
        organization.setUseCustomCertificates(useCustomCertificates);
    }
}
