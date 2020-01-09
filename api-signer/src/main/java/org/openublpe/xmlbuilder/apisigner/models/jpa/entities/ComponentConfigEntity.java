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

@Entity
@Table(name = "component_config")
@NamedQueries({
        @NamedQuery(name = "getComponentConfig", query = "select attr from ComponentConfigEntity attr where attr.component = :component"),
        @NamedQuery(name = "deleteComponentConfigByComponent", query = "delete from  ComponentConfigEntity attr where attr.component = :component"),
        @NamedQuery(name = "deleteComponentConfigByOrganization", query = "delete from  ComponentConfigEntity attr where attr.component IN (select u from ComponentEntity u where u.organization=:organization)"),
        @NamedQuery(name = "deleteComponentConfigByParent", query = "delete from  ComponentConfigEntity attr where attr.component IN (select u from ComponentEntity u where u.parentId=:parentId)"),
})
public class ComponentConfigEntity {

    @Id
    @Column(name = "id", length = 36)
    @Access(AccessType.PROPERTY)
    protected String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "component_id", foreignKey = @ForeignKey)
    protected ComponentEntity component;

    @Column(name = "name")
    protected String name;

    @Column(name = "value", length = 4000)
    protected String value;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ComponentEntity getComponent() {
        return component;
    }

    public void setComponent(ComponentEntity component) {
        this.component = component;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof ComponentConfigEntity)) return false;

        ComponentConfigEntity that = (ComponentConfigEntity) o;

        if (!id.equals(that.getId())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
