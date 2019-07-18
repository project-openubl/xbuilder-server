package org.jboss.xavier.analytics.pojo.output;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.kie.api.definition.type.Label;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class EnvironmentModel
{
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JsonBackReference
    private InitialSavingsEstimationReportModel report;

    // Environment
    private Integer sourceProductIndicator;
    @Label("Number of hypervisors found")
    private Integer hypervisors;
    private Integer year1Hypervisor;
    private Integer year2Hypervisor;
    private Integer year3Hypervisor;
    private Double growthRatePercentage;
    private Integer dealIndicator;
    private Boolean openStackIndicator;

    public EnvironmentModel() {}

    public Integer getSourceProductIndicator() {
        return sourceProductIndicator;
    }

    public void setSourceProductIndicator(Integer sourceProductIndicator) {
        this.sourceProductIndicator = sourceProductIndicator;
    }

    public Integer getHypervisors() {
        return hypervisors;
    }

    public void setHypervisors(Integer hypervisors) {
        this.hypervisors = hypervisors;
    }

    public Integer getYear1Hypervisor() {
        return year1Hypervisor;
    }

    public void setYear1Hypervisor(Integer year1Hypervisor) {
        this.year1Hypervisor = year1Hypervisor;
    }

    public Integer getYear2Hypervisor() {
        return year2Hypervisor;
    }

    public void setYear2Hypervisor(Integer year2Hypervisor) {
        this.year2Hypervisor = year2Hypervisor;
    }

    public Integer getYear3Hypervisor() {
        return year3Hypervisor;
    }

    public void setYear3Hypervisor(Integer year3Hypervisor) {
        this.year3Hypervisor = year3Hypervisor;
    }

    public Double getGrowthRatePercentage() {
        return growthRatePercentage;
    }

    public void setGrowthRatePercentage(Double growthRatePercentage) {
        this.growthRatePercentage = growthRatePercentage;
    }

    public Integer getDealIndicator() {
        return dealIndicator;
    }

    public void setDealIndicator(Integer dealIndicator) {
        this.dealIndicator = dealIndicator;
    }

    public Boolean getOpenStackIndicator() {
        return openStackIndicator;
    }

    public void setOpenStackIndicator(Boolean openStackIndicator) {
        this.openStackIndicator = openStackIndicator;
    }

    public InitialSavingsEstimationReportModel getReport() {
        return report;
    }

    public void setReport(InitialSavingsEstimationReportModel report) {
        this.report = report;
    }
}
