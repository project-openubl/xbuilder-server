package org.jboss.xavier.analytics.pojo.output;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class SourceRampDownCostsModel
{
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JsonBackReference
    private InitialSavingsEstimationReportModel report;

    // SourceRampDownCosts
    private Integer year1ServersOffSource;
    private Integer year1SourceActiveLicense;
    private Integer year1SourcePaidMaintenance;
    private Double year1SourceMaintenancePerServerValue;
    private Double year1SourceMaintenanceTotalValue;
    private Integer year2ServersOffSource;
    private Integer year2SourceActiveLicense;
    private Integer year2SourcePaidMaintenance;
    private Double year2SourceMaintenancePerServerValue;
    private Double year2SourceMaintenanceTotalValue;
    private Integer year3ServersOffSource;
    private Integer year3SourceActiveLicense;
    private Integer year3SourcePaidMaintenance;
    private Double year3SourceMaintenancePerServerValue;
    private Double year3SourceMaintenanceTotalValue;

    public SourceRampDownCostsModel() {}

    public Integer getYear1ServersOffSource() {
        return year1ServersOffSource;
    }

    public void setYear1ServersOffSource(Integer year1ServersOffSource) {
        this.year1ServersOffSource = year1ServersOffSource;
    }

    public Integer getYear1SourceActiveLicense() {
        return year1SourceActiveLicense;
    }

    public void setYear1SourceActiveLicense(Integer year1SourceActiveLicense) {
        this.year1SourceActiveLicense = year1SourceActiveLicense;
    }

    public Integer getYear1SourcePaidMaintenance() {
        return year1SourcePaidMaintenance;
    }

    public void setYear1SourcePaidMaintenance(Integer year1SourcePaidMaintenance) {
        this.year1SourcePaidMaintenance = year1SourcePaidMaintenance;
    }

    public Double getYear1SourceMaintenancePerServerValue() {
        return year1SourceMaintenancePerServerValue;
    }

    public void setYear1SourceMaintenancePerServerValue(Double year1SourceMaintenancePerServerValue) {
        this.year1SourceMaintenancePerServerValue = year1SourceMaintenancePerServerValue;
    }

    public Double getYear1SourceMaintenanceTotalValue() {
        return year1SourceMaintenanceTotalValue;
    }

    public void setYear1SourceMaintenanceTotalValue(Double year1SourceMaintenanceTotalValue) {
        this.year1SourceMaintenanceTotalValue = year1SourceMaintenanceTotalValue;
    }

    public Integer getYear2ServersOffSource() {
        return year2ServersOffSource;
    }

    public void setYear2ServersOffSource(Integer year2ServersOffSource) {
        this.year2ServersOffSource = year2ServersOffSource;
    }

    public Integer getYear2SourceActiveLicense() {
        return year2SourceActiveLicense;
    }

    public void setYear2SourceActiveLicense(Integer year2SourceActiveLicense) {
        this.year2SourceActiveLicense = year2SourceActiveLicense;
    }

    public Integer getYear2SourcePaidMaintenance() {
        return year2SourcePaidMaintenance;
    }

    public void setYear2SourcePaidMaintenance(Integer year2SourcePaidMaintenance) {
        this.year2SourcePaidMaintenance = year2SourcePaidMaintenance;
    }

    public Double getYear2SourceMaintenancePerServerValue() {
        return year2SourceMaintenancePerServerValue;
    }

    public void setYear2SourceMaintenancePerServerValue(Double year2SourceMaintenancePerServerValue) {
        this.year2SourceMaintenancePerServerValue = year2SourceMaintenancePerServerValue;
    }

    public Double getYear2SourceMaintenanceTotalValue() {
        return year2SourceMaintenanceTotalValue;
    }

    public void setYear2SourceMaintenanceTotalValue(Double year2SourceMaintenanceTotalValue) {
        this.year2SourceMaintenanceTotalValue = year2SourceMaintenanceTotalValue;
    }

    public Integer getYear3ServersOffSource() {
        return year3ServersOffSource;
    }

    public void setYear3ServersOffSource(Integer year3ServersOffSource) {
        this.year3ServersOffSource = year3ServersOffSource;
    }

    public Integer getYear3SourceActiveLicense() {
        return year3SourceActiveLicense;
    }

    public void setYear3SourceActiveLicense(Integer year3SourceActiveLicense) {
        this.year3SourceActiveLicense = year3SourceActiveLicense;
    }

    public Integer getYear3SourcePaidMaintenance() {
        return year3SourcePaidMaintenance;
    }

    public void setYear3SourcePaidMaintenance(Integer year3SourcePaidMaintenance) {
        this.year3SourcePaidMaintenance = year3SourcePaidMaintenance;
    }

    public Double getYear3SourceMaintenancePerServerValue() {
        return year3SourceMaintenancePerServerValue;
    }

    public void setYear3SourceMaintenancePerServerValue(Double year3SourceMaintenancePerServerValue) {
        this.year3SourceMaintenancePerServerValue = year3SourceMaintenancePerServerValue;
    }

    public Double getYear3SourceMaintenanceTotalValue() {
        return year3SourceMaintenanceTotalValue;
    }

    public void setYear3SourceMaintenanceTotalValue(Double year3SourceMaintenanceTotalValue) {
        this.year3SourceMaintenanceTotalValue = year3SourceMaintenanceTotalValue;
    }

    public InitialSavingsEstimationReportModel getReport() {
        return report;
    }

    public void setReport(InitialSavingsEstimationReportModel report) {
        this.report = report;
    }
}
