package org.openublpe.xmlbuilder.signer.representations.idm;

public class OrganizationRepresentation {

    private String id;
    private String name;
    private String description;

    private String type;
    private Boolean useCustomCertificates;

    public OrganizationRepresentation() {

    }

    public OrganizationRepresentation(OrganizationRepresentation rep) {
        this.id = rep.getId();
        this.name = rep.getName();
        this.description = rep.getDescription();
        this.type = rep.getType();
        this.useCustomCertificates = rep.getUseCustomCertificates();
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getUseCustomCertificates() {
        return useCustomCertificates;
    }

    public void setUseCustomCertificates(Boolean useCustomCertificates) {
        this.useCustomCertificates = useCustomCertificates;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
