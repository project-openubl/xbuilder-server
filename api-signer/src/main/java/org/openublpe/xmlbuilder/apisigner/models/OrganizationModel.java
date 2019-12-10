package org.openublpe.xmlbuilder.apisigner.models;

public interface OrganizationModel extends Model {

    String MASTER_ID = "master";

    OrganizationType getType();

    String getName();
    void setName(String name);

    String getDescription();
    void setDescription(String description);

    boolean getUseCustomCertificates();
    void setUseCustomCertificates(boolean useCustomCertificates);
}
