package org.jboss.xavier.integrations.migrationanalytics.output;

@javax.persistence.Entity
public class ReportDataModel implements java.io.Serializable {

    static final long serialVersionUID = 1L;

    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO, generator = "INPUTDATAMODEL_ID_GENERATOR")
    @javax.persistence.Id
    @javax.persistence.SequenceGenerator(sequenceName = "INPUTDATAMODEL_ID_SEQ", name = "INPUTDATAMODEL_ID_GENERATOR")
    private java.lang.Long id;

    @org.kie.api.definition.type.Label("Customer ID")
    private java.lang.String customerId;

    @org.kie.api.definition.type.Label("Source payload file name")
    private java.lang.String fileName;

    @org.kie.api.definition.type.Label("Number of hosts found")
    private java.lang.Integer numberOfHosts;

    @org.kie.api.definition.type.Label("Total disk space used found ")
    private java.lang.Long totalDiskSpace;

    @org.kie.api.definition.type.Label("Total price for subscriptions once migrated")
    private java.lang.Integer totalPrice;

    @org.kie.api.definition.type.Label(value = "Date of creation")
    private java.util.Date creationDate;

    public ReportDataModel() {
    }

    public java.lang.Long getId() {
        return this.id;
    }

    public void setId(java.lang.Long id) {
        this.id = id;
    }

    public java.lang.String getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(java.lang.String customerId) {
        this.customerId = customerId;
    }

    public java.lang.String getFileName() {
        return this.fileName;
    }

    public void setFileName(java.lang.String fileName) {
        this.fileName = fileName;
    }

    public java.lang.Integer getNumberOfHosts() {
        return this.numberOfHosts;
    }

    public void setNumberOfHosts(java.lang.Integer numberOfHosts) {
        this.numberOfHosts = numberOfHosts;
    }

    public java.lang.Long getTotalDiskSpace() {
        return this.totalDiskSpace;
    }

    public void setTotalDiskSpace(java.lang.Long totalDiskSpace) {
        this.totalDiskSpace = totalDiskSpace;
    }

    public java.lang.Integer getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(java.lang.Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public java.util.Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(java.util.Date creationDate) {
        this.creationDate = creationDate;
    }

    public ReportDataModel(java.lang.Long id, java.lang.String customerId,
                           java.lang.String fileName, java.lang.Integer numberOfHosts,
                           java.lang.Long totalDiskSpace, java.lang.Integer totalPrice,
                           java.util.Date creationDate) {
        this.id = id;
        this.customerId = customerId;
        this.fileName = fileName;
        this.numberOfHosts = numberOfHosts;
        this.totalDiskSpace = totalDiskSpace;
        this.totalPrice = totalPrice;
        this.creationDate = creationDate;
    }

}