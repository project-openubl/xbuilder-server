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
package org.openublpe.xmlbuilder.apisigner.models.jpa.entities;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "component")
@NamedQueries({
        @NamedQuery(name = "getComponents", query = "select attr from ComponentEntity attr where attr.organization = :organization"),
        @NamedQuery(name = "getComponentsByParentAndType", query = "select attr from ComponentEntity attr where attr.organization = :organization and attr.providerType = :providerType and attr.parentId = :parentId"),
        @NamedQuery(name = "getComponentByParent", query = "select attr from ComponentEntity attr where attr.organization = :organization and attr.parentId = :parentId"),
        @NamedQuery(name = "getComponentIdsByParent", query = "select attr.id from ComponentEntity attr where attr.organization = :organization and attr.parentId = :parentId"),
        @NamedQuery(name = "deleteComponentByOrganization", query = "delete from  ComponentEntity c where c.organization = :organization"),
        @NamedQuery(name = "deleteComponentByParent", query = "delete from  ComponentEntity c where c.parentId = :parentId"),
        @NamedQuery(name = "deleteComponent", query = "delete from ComponentEntity c where c=:component")
})
public class ComponentEntity {

    @Id
    @Column(name = "id", length = 36)
    @Access(AccessType.PROPERTY)
    protected String id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey, name = "organization_id")
    protected OrganizationEntity organization;

    @Column(name = "name")
    protected String name;

    @Column(name = "provider_type")
    protected String providerType;

    @Column(name = "provider_id")
    protected String providerId;

    @Column(name = "parent_id")
    protected String parentId;

    @Column(name = "sub_type")
    protected String subType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProviderType() {
        return providerType;
    }

    public void setProviderType(String providerType) {
        this.providerType = providerType;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public OrganizationEntity getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationEntity organization) {
        this.organization = organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof ComponentEntity)) return false;

        ComponentEntity that = (ComponentEntity) o;

        if (!id.equals(that.getId())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
