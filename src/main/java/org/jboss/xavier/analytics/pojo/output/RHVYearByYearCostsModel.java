package org.jboss.xavier.analytics.pojo.output;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class RHVYearByYearCostsModel
{
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JsonBackReference
    private InitialSavingsEstimationReportModel report;

    // RHVYearByYearCosts
    private Double year1RhvGrandTotalValue;
    private Double year2RhvGrandTotalValue;
    private Double year3RhvGrandTotalValue;
    private Double year1RhvBudgetFreedHighValue;
    private Double year1RhvBudgetFreedLikelyValue;
    private Double year1RhvBudgetFreedLowValue;
    private Double year2RhvBudgetFreedHighValue;
    private Double year2RhvBudgetFreedLikelyValue;
    private Double year2RhvBudgetFreedLowValue;
    private Double year3RhvBudgetFreedHighValue;
    private Double year3RhvBudgetFreedLikelyValue;
    private Double year3RhvBudgetFreedLowValue;

    public RHVYearByYearCostsModel() {}

    public Double getYear1RhvGrandTotalValue() {
        return year1RhvGrandTotalValue;
    }

    public void setYear1RhvGrandTotalValue(Double year1RhvGrandTotalValue) {
        this.year1RhvGrandTotalValue = year1RhvGrandTotalValue;
    }

    public Double getYear2RhvGrandTotalValue() {
        return year2RhvGrandTotalValue;
    }

    public void setYear2RhvGrandTotalValue(Double year2RhvGrandTotalValue) {
        this.year2RhvGrandTotalValue = year2RhvGrandTotalValue;
    }

    public Double getYear3RhvGrandTotalValue() {
        return year3RhvGrandTotalValue;
    }

    public void setYear3RhvGrandTotalValue(Double year3RhvGrandTotalValue) {
        this.year3RhvGrandTotalValue = year3RhvGrandTotalValue;
    }

    public Double getYear1RhvBudgetFreedHighValue() {
        return year1RhvBudgetFreedHighValue;
    }

    public void setYear1RhvBudgetFreedHighValue(Double year1RhvBudgetFreedHighValue) {
        this.year1RhvBudgetFreedHighValue = year1RhvBudgetFreedHighValue;
    }

    public Double getYear1RhvBudgetFreedLikelyValue() {
        return year1RhvBudgetFreedLikelyValue;
    }

    public void setYear1RhvBudgetFreedLikelyValue(Double year1RhvBudgetFreedLikelyValue) {
        this.year1RhvBudgetFreedLikelyValue = year1RhvBudgetFreedLikelyValue;
    }

    public Double getYear1RhvBudgetFreedLowValue() {
        return year1RhvBudgetFreedLowValue;
    }

    public void setYear1RhvBudgetFreedLowValue(Double year1RhvBudgetFreedLowValue) {
        this.year1RhvBudgetFreedLowValue = year1RhvBudgetFreedLowValue;
    }

    public Double getYear2RhvBudgetFreedHighValue() {
        return year2RhvBudgetFreedHighValue;
    }

    public void setYear2RhvBudgetFreedHighValue(Double year2RhvBudgetFreedHighValue) {
        this.year2RhvBudgetFreedHighValue = year2RhvBudgetFreedHighValue;
    }

    public Double getYear2RhvBudgetFreedLikelyValue() {
        return year2RhvBudgetFreedLikelyValue;
    }

    public void setYear2RhvBudgetFreedLikelyValue(Double year2RhvBudgetFreedLikelyValue) {
        this.year2RhvBudgetFreedLikelyValue = year2RhvBudgetFreedLikelyValue;
    }

    public Double getYear2RhvBudgetFreedLowValue() {
        return year2RhvBudgetFreedLowValue;
    }

    public void setYear2RhvBudgetFreedLowValue(Double year2RhvBudgetFreedLowValue) {
        this.year2RhvBudgetFreedLowValue = year2RhvBudgetFreedLowValue;
    }

    public Double getYear3RhvBudgetFreedHighValue() {
        return year3RhvBudgetFreedHighValue;
    }

    public void setYear3RhvBudgetFreedHighValue(Double year3RhvBudgetFreedHighValue) {
        this.year3RhvBudgetFreedHighValue = year3RhvBudgetFreedHighValue;
    }

    public Double getYear3RhvBudgetFreedLikelyValue() {
        return year3RhvBudgetFreedLikelyValue;
    }

    public void setYear3RhvBudgetFreedLikelyValue(Double year3RhvBudgetFreedLikelyValue) {
        this.year3RhvBudgetFreedLikelyValue = year3RhvBudgetFreedLikelyValue;
    }

    public Double getYear3RhvBudgetFreedLowValue() {
        return year3RhvBudgetFreedLowValue;
    }

    public void setYear3RhvBudgetFreedLowValue(Double year3RhvBudgetFreedLowValue) {
        this.year3RhvBudgetFreedLowValue = year3RhvBudgetFreedLowValue;
    }

    public InitialSavingsEstimationReportModel getReport() {
        return report;
    }

    public void setReport(InitialSavingsEstimationReportModel report) {
        this.report = report;
    }
}
