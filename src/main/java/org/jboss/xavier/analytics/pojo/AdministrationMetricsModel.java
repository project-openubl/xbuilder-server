package org.jboss.xavier.analytics.pojo;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import java.util.Date;

@CsvRecord(separator = ",", crlf = "UNIX", generateHeaderColumns = true)
public class AdministrationMetricsModel {

    @DataField(pos = 1, columnName = "Analysis' owner")
    private String owner;

    @DataField(pos = 2, columnName = "Domain")
    private String ownerDomain;

    @DataField(pos = 3, columnName = "Payload name")
    private String payloadName;

    @DataField(pos = 4, columnName = "Analysis status")
    private String analysisStatus;

    @DataField(pos = 5, columnName = "Creation date (yyyy-MM-dd HH:mm:ss)", pattern = "yyyy-MM-dd HH:mm:ss z")
    private Date analysisInserted;

    @DataField(pos = 6, columnName = "VMs analyzed")
    private Long totalVms;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwnerDomain() {
        return ownerDomain;
    }

    public void setOwnerDomain(String ownerDomain) {
        this.ownerDomain = ownerDomain;
    }

    public String getPayloadName() {
        return payloadName;
    }

    public void setPayloadName(String payloadName) {
        this.payloadName = payloadName;
    }

    public String getAnalysisStatus() {
        return analysisStatus;
    }

    public void setAnalysisStatus(String analysisStatus) {
        this.analysisStatus = analysisStatus;
    }

    public Date getAnalysisInserted() {
        return analysisInserted;
    }

    public void setAnalysisInserted(Date analysisInserted) {
        this.analysisInserted = analysisInserted;
    }

    public Long getTotalVms() {
        return totalVms;
    }

    public void setTotalVms(Long totalVms) {
        this.totalVms = totalVms;
    }

}
