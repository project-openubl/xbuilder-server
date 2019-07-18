package org.jboss.xavier.analytics.pojo.output;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class RHVRampUpCostsModel
{
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JsonBackReference
    private InitialSavingsEstimationReportModel report;

    // RHVRampUpCosts
    private Double year1RhvServers;
    private Double year1RhvCompSubs;
    private Double year1RhvPaidSubs;
    private Double year1RhvPerServerValue;
    private Double year1RhvTotalValue;
    private Double year1RhvServersGrowth;
    private Double year1RhvCompSubsGrowth;
    private Double year1RhvPaidSubsGrowth;
    private Double year1RhvPerServerGrowthValue;
    private Double year1RhvTotalGrowthValue;
    private Double year1RhvGrandTotalGrowthValue;
    private Double rhvSwitchLearningSubsValue;
    private Double rhvSwitchConsultValue;
    private Double rhvSwitchTAndEValue;
    private Double year2RhvServers;
    private Double year2RhvCompSubs;
    private Double year2RhvPaidSubs;
    private Double year2RhvPerServerValue;
    private Double year2RhvTotalValue;
    private Double year2RhvServersGrowth;
    private Double year2RhvCompSubsGrowth;
    private Double year2RhvPaidSubsGrowth;
    private Double year2RhvPerServerGrowthValue;
    private Double year2RhvTotalGrowthValue;
    private Double year2RhvGrandTotalGrowthValue;
    private Double year3RhvServers;
    private Double year3RhvCompSubs;
    private Double year3RhvPaidSubs;
    private Double year3RhvPerServerValue;
    private Double year3RhvTotalValue;
    private Double year3RhvServersGrowth;
    private Double year3RhvCompSubsGrowth;
    private Double year3RhvPaidSubsGrowth;
    private Double year3RhvPerServerGrowthValue;
    private Double year3RhvTotalGrowthValue;
    private Double year3RhvGrandTotalGrowthValue;

    public RHVRampUpCostsModel() {}

    public Double getYear1RhvServers() {
        return year1RhvServers;
    }

    public void setYear1RhvServers(Double year1RhvServers) {
        this.year1RhvServers = year1RhvServers;
    }

    public Double getYear1RhvCompSubs() {
        return year1RhvCompSubs;
    }

    public void setYear1RhvCompSubs(Double year1RhvCompSubs) {
        this.year1RhvCompSubs = year1RhvCompSubs;
    }

    public Double getYear1RhvPaidSubs() {
        return year1RhvPaidSubs;
    }

    public void setYear1RhvPaidSubs(Double year1RhvPaidSubs) {
        this.year1RhvPaidSubs = year1RhvPaidSubs;
    }

    public Double getYear1RhvPerServerValue() {
        return year1RhvPerServerValue;
    }

    public void setYear1RhvPerServerValue(Double year1RhvPerServerValue) {
        this.year1RhvPerServerValue = year1RhvPerServerValue;
    }

    public Double getYear1RhvTotalValue() {
        return year1RhvTotalValue;
    }

    public void setYear1RhvTotalValue(Double year1RhvTotalValue) {
        this.year1RhvTotalValue = year1RhvTotalValue;
    }

    public Double getYear1RhvServersGrowth() {
        return year1RhvServersGrowth;
    }

    public void setYear1RhvServersGrowth(Double year1RhvServersGrowth) {
        this.year1RhvServersGrowth = year1RhvServersGrowth;
    }

    public Double getYear1RhvCompSubsGrowth() {
        return year1RhvCompSubsGrowth;
    }

    public void setYear1RhvCompSubsGrowth(Double year1RhvCompSubsGrowth) {
        this.year1RhvCompSubsGrowth = year1RhvCompSubsGrowth;
    }

    public Double getYear1RhvPaidSubsGrowth() {
        return year1RhvPaidSubsGrowth;
    }

    public void setYear1RhvPaidSubsGrowth(Double year1RhvPaidSubsGrowth) {
        this.year1RhvPaidSubsGrowth = year1RhvPaidSubsGrowth;
    }

    public Double getYear1RhvPerServerGrowthValue() {
        return year1RhvPerServerGrowthValue;
    }

    public void setYear1RhvPerServerGrowthValue(Double year1RhvPerServerGrowthValue) {
        this.year1RhvPerServerGrowthValue = year1RhvPerServerGrowthValue;
    }

    public Double getYear1RhvTotalGrowthValue() {
        return year1RhvTotalGrowthValue;
    }

    public void setYear1RhvTotalGrowthValue(Double year1RhvTotalGrowthValue) {
        this.year1RhvTotalGrowthValue = year1RhvTotalGrowthValue;
    }

    public Double getYear1RhvGrandTotalGrowthValue() {
        return year1RhvGrandTotalGrowthValue;
    }

    public void setYear1RhvGrandTotalGrowthValue(Double year1RhvGrandTotalGrowthValue) {
        this.year1RhvGrandTotalGrowthValue = year1RhvGrandTotalGrowthValue;
    }

    public Double getRhvSwitchLearningSubsValue() {
        return rhvSwitchLearningSubsValue;
    }

    public void setRhvSwitchLearningSubsValue(Double rhvSwitchLearningSubsValue) {
        this.rhvSwitchLearningSubsValue = rhvSwitchLearningSubsValue;
    }

    public Double getRhvSwitchConsultValue() {
        return rhvSwitchConsultValue;
    }

    public void setRhvSwitchConsultValue(Double rhvSwitchConsultValue) {
        this.rhvSwitchConsultValue = rhvSwitchConsultValue;
    }

    public Double getRhvSwitchTAndEValue() {
        return rhvSwitchTAndEValue;
    }

    public void setRhvSwitchTAndEValue(Double rhvSwitchTAndEValue) {
        this.rhvSwitchTAndEValue = rhvSwitchTAndEValue;
    }

    public Double getYear2RhvServers() {
        return year2RhvServers;
    }

    public void setYear2RhvServers(Double year2RhvServers) {
        this.year2RhvServers = year2RhvServers;
    }

    public Double getYear2RhvCompSubs() {
        return year2RhvCompSubs;
    }

    public void setYear2RhvCompSubs(Double year2RhvCompSubs) {
        this.year2RhvCompSubs = year2RhvCompSubs;
    }

    public Double getYear2RhvPaidSubs() {
        return year2RhvPaidSubs;
    }

    public void setYear2RhvPaidSubs(Double year2RhvPaidSubs) {
        this.year2RhvPaidSubs = year2RhvPaidSubs;
    }

    public Double getYear2RhvPerServerValue() {
        return year2RhvPerServerValue;
    }

    public void setYear2RhvPerServerValue(Double year2RhvPerServerValue) {
        this.year2RhvPerServerValue = year2RhvPerServerValue;
    }

    public Double getYear2RhvTotalValue() {
        return year2RhvTotalValue;
    }

    public void setYear2RhvTotalValue(Double year2RhvTotalValue) {
        this.year2RhvTotalValue = year2RhvTotalValue;
    }

    public Double getYear2RhvServersGrowth() {
        return year2RhvServersGrowth;
    }

    public void setYear2RhvServersGrowth(Double year2RhvServersGrowth) {
        this.year2RhvServersGrowth = year2RhvServersGrowth;
    }

    public Double getYear2RhvCompSubsGrowth() {
        return year2RhvCompSubsGrowth;
    }

    public void setYear2RhvCompSubsGrowth(Double year2RhvCompSubsGrowth) {
        this.year2RhvCompSubsGrowth = year2RhvCompSubsGrowth;
    }

    public Double getYear2RhvPaidSubsGrowth() {
        return year2RhvPaidSubsGrowth;
    }

    public void setYear2RhvPaidSubsGrowth(Double year2RhvPaidSubsGrowth) {
        this.year2RhvPaidSubsGrowth = year2RhvPaidSubsGrowth;
    }

    public Double getYear2RhvPerServerGrowthValue() {
        return year2RhvPerServerGrowthValue;
    }

    public void setYear2RhvPerServerGrowthValue(Double year2RhvPerServerGrowthValue) {
        this.year2RhvPerServerGrowthValue = year2RhvPerServerGrowthValue;
    }

    public Double getYear2RhvTotalGrowthValue() {
        return year2RhvTotalGrowthValue;
    }

    public void setYear2RhvTotalGrowthValue(Double year2RhvTotalGrowthValue) {
        this.year2RhvTotalGrowthValue = year2RhvTotalGrowthValue;
    }

    public Double getYear2RhvGrandTotalGrowthValue() {
        return year2RhvGrandTotalGrowthValue;
    }

    public void setYear2RhvGrandTotalGrowthValue(Double year2RhvGrandTotalGrowthValue) {
        this.year2RhvGrandTotalGrowthValue = year2RhvGrandTotalGrowthValue;
    }

    public Double getYear3RhvServers() {
        return year3RhvServers;
    }

    public void setYear3RhvServers(Double year3RhvServers) {
        this.year3RhvServers = year3RhvServers;
    }

    public Double getYear3RhvCompSubs() {
        return year3RhvCompSubs;
    }

    public void setYear3RhvCompSubs(Double year3RhvCompSubs) {
        this.year3RhvCompSubs = year3RhvCompSubs;
    }

    public Double getYear3RhvPaidSubs() {
        return year3RhvPaidSubs;
    }

    public void setYear3RhvPaidSubs(Double year3RhvPaidSubs) {
        this.year3RhvPaidSubs = year3RhvPaidSubs;
    }

    public Double getYear3RhvPerServerValue() {
        return year3RhvPerServerValue;
    }

    public void setYear3RhvPerServerValue(Double year3RhvPerServerValue) {
        this.year3RhvPerServerValue = year3RhvPerServerValue;
    }

    public Double getYear3RhvTotalValue() {
        return year3RhvTotalValue;
    }

    public void setYear3RhvTotalValue(Double year3RhvTotalValue) {
        this.year3RhvTotalValue = year3RhvTotalValue;
    }

    public Double getYear3RhvServersGrowth() {
        return year3RhvServersGrowth;
    }

    public void setYear3RhvServersGrowth(Double year3RhvServersGrowth) {
        this.year3RhvServersGrowth = year3RhvServersGrowth;
    }

    public Double getYear3RhvCompSubsGrowth() {
        return year3RhvCompSubsGrowth;
    }

    public void setYear3RhvCompSubsGrowth(Double year3RhvCompSubsGrowth) {
        this.year3RhvCompSubsGrowth = year3RhvCompSubsGrowth;
    }

    public Double getYear3RhvPaidSubsGrowth() {
        return year3RhvPaidSubsGrowth;
    }

    public void setYear3RhvPaidSubsGrowth(Double year3RhvPaidSubsGrowth) {
        this.year3RhvPaidSubsGrowth = year3RhvPaidSubsGrowth;
    }

    public Double getYear3RhvPerServerGrowthValue() {
        return year3RhvPerServerGrowthValue;
    }

    public void setYear3RhvPerServerGrowthValue(Double year3RhvPerServerGrowthValue) {
        this.year3RhvPerServerGrowthValue = year3RhvPerServerGrowthValue;
    }

    public Double getYear3RhvTotalGrowthValue() {
        return year3RhvTotalGrowthValue;
    }

    public void setYear3RhvTotalGrowthValue(Double year3RhvTotalGrowthValue) {
        this.year3RhvTotalGrowthValue = year3RhvTotalGrowthValue;
    }

    public Double getYear3RhvGrandTotalGrowthValue() {
        return year3RhvGrandTotalGrowthValue;
    }

    public void setYear3RhvGrandTotalGrowthValue(Double year3RhvGrandTotalGrowthValue) {
        this.year3RhvGrandTotalGrowthValue = year3RhvGrandTotalGrowthValue;
    }

    public InitialSavingsEstimationReportModel getReport() {
        return report;
    }

    public void setReport(InitialSavingsEstimationReportModel report) {
        this.report = report;
    }
}
