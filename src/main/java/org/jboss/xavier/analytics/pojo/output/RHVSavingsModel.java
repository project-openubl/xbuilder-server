package org.jboss.xavier.analytics.pojo.output;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class RHVSavingsModel
{
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JsonBackReference
    private InitialSavingsEstimationReportModel report;

    // RHVSavings
    private Double rhvSaveHighValue;
    private Double rhvSaveLikelyValue;
    private Double rhvSaveLowValue;
    private Double rhvSaveFromValue;
    private Double rhvSaveToValue;

    public RHVSavingsModel() {}

    public Double getRhvSaveHighValue() {
        return rhvSaveHighValue;
    }

    public void setRhvSaveHighValue(Double rhvSaveHighValue) {
        this.rhvSaveHighValue = rhvSaveHighValue;
    }

    public Double getRhvSaveLikelyValue() {
        return rhvSaveLikelyValue;
    }

    public void setRhvSaveLikelyValue(Double rhvSaveLikelyValue) {
        this.rhvSaveLikelyValue = rhvSaveLikelyValue;
    }

    public Double getRhvSaveLowValue() {
        return rhvSaveLowValue;
    }

    public void setRhvSaveLowValue(Double rhvSaveLowValue) {
        this.rhvSaveLowValue = rhvSaveLowValue;
    }

    public Double getRhvSaveFromValue() {
        return rhvSaveFromValue;
    }

    public void setRhvSaveFromValue(Double rhvSaveFromValue) {
        this.rhvSaveFromValue = rhvSaveFromValue;
    }

    public Double getRhvSaveToValue() {
        return rhvSaveToValue;
    }

    public void setRhvSaveToValue(Double rhvSaveToValue) {
        this.rhvSaveToValue = rhvSaveToValue;
    }

    public InitialSavingsEstimationReportModel getReport() {
        return report;
    }

    public void setReport(InitialSavingsEstimationReportModel report) {
        this.report = report;
    }
}
