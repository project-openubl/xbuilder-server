package org.jboss.xavier.analytics.pojo.output;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class RHVAdditionalContainerCapacityModel
{
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JsonBackReference
    private InitialSavingsEstimationReportModel report;

    // RHVAdditionalContainerCapacity
    private Double rhvContainerHigh;
    private Double rhvContainerLikely;
    private Double rhvContainerLow;
    private Double rhvContainerFrom;
    private Double rhvContainerTo;

    public RHVAdditionalContainerCapacityModel() {}

    public Double getRhvContainerHigh() {
        return rhvContainerHigh;
    }

    public void setRhvContainerHigh(Double rhvContainerHigh) {
        this.rhvContainerHigh = rhvContainerHigh;
    }

    public Double getRhvContainerLikely() {
        return rhvContainerLikely;
    }

    public void setRhvContainerLikely(Double rhvContainerLikely) {
        this.rhvContainerLikely = rhvContainerLikely;
    }

    public Double getRhvContainerLow() {
        return rhvContainerLow;
    }

    public void setRhvContainerLow(Double rhvContainerLow) {
        this.rhvContainerLow = rhvContainerLow;
    }

    public Double getRhvContainerFrom() {
        return rhvContainerFrom;
    }

    public void setRhvContainerFrom(Double rhvContainerFrom) {
        this.rhvContainerFrom = rhvContainerFrom;
    }

    public Double getRhvContainerTo() {
        return rhvContainerTo;
    }

    public void setRhvContainerTo(Double rhvContainerTo) {
        this.rhvContainerTo = rhvContainerTo;
    }

    public InitialSavingsEstimationReportModel getReport() {
        return report;
    }

    public void setReport(InitialSavingsEstimationReportModel report) {
        this.report = report;
    }

}
