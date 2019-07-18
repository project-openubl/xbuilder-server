package org.jboss.xavier.analytics.pojo.output;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class SourceCostsModel
{
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JsonBackReference
    private InitialSavingsEstimationReportModel report;

    // SourceCosts
    private Double sourceLicenseValue;
    private Double sourceMaintenanceValue;
    private Integer year1Server;
    private Double year1SourceValue;
    private Double year1SourceMaintenanceValue;
    private Integer year2Server;
    private Double year2SourceValue;
    private Double year2SourceMaintenanceValue;
    private Integer year3Server;
    private Double year3SourceValue;
    private Double year3SourceMaintenanceValue;
    private Double totSourceValue;
    private Double totSourceMaintenanceValue;
    private Double totalSourceValue;
    private Integer sourceNewELAIndicator;
    private Double sourceNewHighValue;
    private Double sourceNewLikelyValue;
    private Double sourceNewLowValue;
    private Double sourceRenewHighValue;
    private Double sourceRenewLikelyValue;
    private Double sourceRenewLowValue;

    public SourceCostsModel() {}

    public Double getSourceLicenseValue() {
        return sourceLicenseValue;
    }

    public void setSourceLicenseValue(Double sourceLicenseValue) {
        this.sourceLicenseValue = sourceLicenseValue;
    }

    public Double getSourceMaintenanceValue() {
        return sourceMaintenanceValue;
    }

    public void setSourceMaintenanceValue(Double sourceMaintenanceValue) {
        this.sourceMaintenanceValue = sourceMaintenanceValue;
    }

    public Integer getYear1Server() {
        return year1Server;
    }

    public void setYear1Server(Integer year1Server) {
        this.year1Server = year1Server;
    }

    public Double getYear1SourceValue() {
        return year1SourceValue;
    }

    public void setYear1SourceValue(Double year1SourceValue) {
        this.year1SourceValue = year1SourceValue;
    }

    public Double getYear1SourceMaintenanceValue() {
        return year1SourceMaintenanceValue;
    }

    public void setYear1SourceMaintenanceValue(Double year1SourceMaintenanceValue) {
        this.year1SourceMaintenanceValue = year1SourceMaintenanceValue;
    }

    public Integer getYear2Server() {
        return year2Server;
    }

    public void setYear2Server(Integer year2Server) {
        this.year2Server = year2Server;
    }

    public Double getYear2SourceValue() {
        return year2SourceValue;
    }

    public void setYear2SourceValue(Double year2SourceValue) {
        this.year2SourceValue = year2SourceValue;
    }

    public Double getYear2SourceMaintenanceValue() {
        return year2SourceMaintenanceValue;
    }

    public void setYear2SourceMaintenanceValue(Double year2SourceMaintenanceValue) {
        this.year2SourceMaintenanceValue = year2SourceMaintenanceValue;
    }

    public Integer getYear3Server() {
        return year3Server;
    }

    public void setYear3Server(Integer year3Server) {
        this.year3Server = year3Server;
    }

    public Double getYear3SourceValue() {
        return year3SourceValue;
    }

    public void setYear3SourceValue(Double year3SourceValue) {
        this.year3SourceValue = year3SourceValue;
    }

    public Double getYear3SourceMaintenanceValue() {
        return year3SourceMaintenanceValue;
    }

    public void setYear3SourceMaintenanceValue(Double year3SourceMaintenanceValue) {
        this.year3SourceMaintenanceValue = year3SourceMaintenanceValue;
    }

    public Double getTotSourceValue() {
        return totSourceValue;
    }

    public void setTotSourceValue(Double totSourceValue) {
        this.totSourceValue = totSourceValue;
    }

    public Double getTotSourceMaintenanceValue() {
        return totSourceMaintenanceValue;
    }

    public void setTotSourceMaintenanceValue(Double totSourceMaintenanceValue) {
        this.totSourceMaintenanceValue = totSourceMaintenanceValue;
    }

    public Double getTotalSourceValue() {
        return totalSourceValue;
    }

    public void setTotalSourceValue(Double totalSourceValue) {
        this.totalSourceValue = totalSourceValue;
    }

    public Integer getSourceNewELAIndicator() {
        return sourceNewELAIndicator;
    }

    public void setSourceNewELAIndicator(Integer sourceNewELAIndicator) {
        this.sourceNewELAIndicator = sourceNewELAIndicator;
    }

    public Double getSourceNewHighValue() {
        return sourceNewHighValue;
    }

    public void setSourceNewHighValue(Double sourceNewHighValue) {
        this.sourceNewHighValue = sourceNewHighValue;
    }

    public Double getSourceNewLikelyValue() {
        return sourceNewLikelyValue;
    }

    public void setSourceNewLikelyValue(Double sourceNewLikelyValue) {
        this.sourceNewLikelyValue = sourceNewLikelyValue;
    }

    public Double getSourceNewLowValue() {
        return sourceNewLowValue;
    }

    public void setSourceNewLowValue(Double sourceNewLowValue) {
        this.sourceNewLowValue = sourceNewLowValue;
    }

    public Double getSourceRenewHighValue() {
        return sourceRenewHighValue;
    }

    public void setSourceRenewHighValue(Double sourceRenewHighValue) {
        this.sourceRenewHighValue = sourceRenewHighValue;
    }

    public Double getSourceRenewLikelyValue() {
        return sourceRenewLikelyValue;
    }

    public void setSourceRenewLikelyValue(Double sourceRenewLikelyValue) {
        this.sourceRenewLikelyValue = sourceRenewLikelyValue;
    }

    public Double getSourceRenewLowValue() {
        return sourceRenewLowValue;
    }

    public void setSourceRenewLowValue(Double sourceRenewLowValue) {
        this.sourceRenewLowValue = sourceRenewLowValue;
    }

    public InitialSavingsEstimationReportModel getReport() {
        return report;
    }

    public void setReport(InitialSavingsEstimationReportModel report) {
        this.report = report;
    }
}
