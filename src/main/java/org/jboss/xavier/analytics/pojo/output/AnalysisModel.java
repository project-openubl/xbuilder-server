package org.jboss.xavier.analytics.pojo.output;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.jboss.xavier.analytics.pojo.output.workload.inventory.WorkloadInventoryReportModel;
import org.jboss.xavier.analytics.pojo.output.workload.summary.WorkloadSummaryReportModel;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class AnalysisModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "AnalysisModel_ID_GENERATOR")
    @GenericGenerator(
        name = "AnalysisModel_ID_GENERATOR",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
                @Parameter(name = "sequence_name", value = "AnalysisModel_SEQUENCE")
        }
    )
    private Long id;

    @OneToOne(mappedBy = "analysis", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private InitialSavingsEstimationReportModel initialSavingsEstimationReportModel;

    @OneToMany(mappedBy = "analysis", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<WorkloadInventoryReportModel> workloadInventoryReportModels;

    @OneToOne(mappedBy = "analysis", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private WorkloadSummaryReportModel workloadSummaryReportModels;

    private String reportName;
    private String reportDescription;
    private String payloadName;
    private String status;
    private Date inserted;
    private Date lastUpdate;
    private String owner;
    private String payloadStorageId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InitialSavingsEstimationReportModel getInitialSavingsEstimationReportModel() {
        return initialSavingsEstimationReportModel;
    }

    public void setInitialSavingsEstimationReportModel(InitialSavingsEstimationReportModel initialSavingsEstimationReportModel) {
        this.initialSavingsEstimationReportModel = initialSavingsEstimationReportModel;
        initialSavingsEstimationReportModel.setAnalysis(this);
    }

    public List<WorkloadInventoryReportModel> getWorkloadInventoryReportModels() {
        return workloadInventoryReportModels;
    }

    public void setWorkloadInventoryReportModels(List<WorkloadInventoryReportModel> workloadInventoryReportModels) {
        this.workloadInventoryReportModels = workloadInventoryReportModels;
        workloadInventoryReportModels.forEach(workloadInventoryReportModel -> workloadInventoryReportModel.setAnalysis(this));
    }

    public void addWorkloadInventoryReportModel(WorkloadInventoryReportModel workloadInventoryReportModel)
    {
        if (this.workloadInventoryReportModels == null) this.workloadInventoryReportModels = new ArrayList<>();
        this.workloadInventoryReportModels.add(workloadInventoryReportModel);
        workloadInventoryReportModel.setAnalysis(this);
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportDescription() {
        return reportDescription;
    }

    public void setReportDescription(String reportDescription) {
        this.reportDescription = reportDescription;
    }

    public String getPayloadName() {
        return payloadName;
    }

    public void setPayloadName(String payloadName) {
        this.payloadName = payloadName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getInserted() {
        return inserted;
    }

    public void setInserted(Date inserted) {
        this.inserted = inserted;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public WorkloadSummaryReportModel getWorkloadSummaryReportModels() {
        return workloadSummaryReportModels;
    }

    public void setWorkloadSummaryReportModels(WorkloadSummaryReportModel workloadSummaryReportModels) {
        this.workloadSummaryReportModels = workloadSummaryReportModels;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setPayloadStorageId(String payloadStorageId) {
        this.payloadStorageId = payloadStorageId;
    }

    public String getPayloadStorageId() {
        return payloadStorageId;
    }
}
