package org.jboss.xavier.analytics.pojo.output;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Component
public class AnalysisModel
{
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO, generator = "AnalysisModel_ID_GENERATOR")
    @GenericGenerator(
        name = "AnalysisModel_ID_GENERATOR",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
                @Parameter(name = "sequence_name", value = "AnalysisModel_SEQUENCE")
        }
    )
    private Long id;

    @OneToOne(mappedBy = "analysis", cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
    @JsonIgnore
    private InitialSavingsEstimationReportModel initialSavingsEstimationReportModel;

    private String reportName;
    private String reportDescription;
    private String payloadName;
    private String status;

    public AnalysisModel() {}

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
}
