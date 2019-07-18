package org.jboss.xavier.analytics.pojo.output;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class RHVOrderFormModel
{
    @Id
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JsonBackReference
    private InitialSavingsEstimationReportModel report;

    // RHVOrderForm
    private Double year1RhvOrderSku;
    private Double year1RhvOrder;
    private Double year1RhvOrderConsult;
    private Double year1RhvOrderTAndE;
    private Double year1RhvOrderLearningSubs;
    private Double year1RhvOrderListValue;
    private Double year1RhvOrderConsultListValue;
    private Double year1RhvOrderTAndEValue;
    private Double year1RhvOrderLearningSubsListValue;
    private Double year1RhvOrderDiscountValue;
    private Double year1RhvOrderConsultDiscountValue;
    private Double year1RhvOrderTAndEDiscountValue;
    private Double year1RhvOrderLearningSubsDiscountValue;
    private Double year1RhvOrderTotalValue;
    private Double year1RhvOrderConsultTotalValue;
    private Double year1RhvOrderTAndETotalValue;
    private Double year1RhvOrderLearningSubsTotalValue;
    private Double year1RhvOrderGrandTotal;
    private Double year2RhvOrderSku;
    private Double year2RhvOrder;
    private Double year2RhvOrderListValue;
    private Double year2RhvOrderDiscountValue;
    private Double year2RhvOrderTotalValue;
    private Double year2RhvOrderGrandTotal;
    private Double year3RhvOrderSku;
    private Double year3RhvOrder;
    private Double year3RhvOrderListValue;
    private Double year3RhvOrderDiscountValue;
    private Double year3RhvOrderTotalValue;
    private Double year3RhvOrderGrandTotal;

    public RHVOrderFormModel() {}

    public Double getYear1RhvOrderSku() {
        return year1RhvOrderSku;
    }

    public void setYear1RhvOrderSku(Double year1RhvOrderSku) {
        this.year1RhvOrderSku = year1RhvOrderSku;
    }

    public Double getYear1RhvOrder() {
        return year1RhvOrder;
    }

    public void setYear1RhvOrder(Double year1RhvOrder) {
        this.year1RhvOrder = year1RhvOrder;
    }

    public Double getYear1RhvOrderConsult() {
        return year1RhvOrderConsult;
    }

    public void setYear1RhvOrderConsult(Double year1RhvOrderConsult) {
        this.year1RhvOrderConsult = year1RhvOrderConsult;
    }

    public Double getYear1RhvOrderTAndE() {
        return year1RhvOrderTAndE;
    }

    public void setYear1RhvOrderTAndE(Double year1RhvOrderTAndE) {
        this.year1RhvOrderTAndE = year1RhvOrderTAndE;
    }

    public Double getYear1RhvOrderLearningSubs() {
        return year1RhvOrderLearningSubs;
    }

    public void setYear1RhvOrderLearningSubs(Double year1RhvOrderLearningSubs) {
        this.year1RhvOrderLearningSubs = year1RhvOrderLearningSubs;
    }

    public Double getYear1RhvOrderListValue() {
        return year1RhvOrderListValue;
    }

    public void setYear1RhvOrderListValue(Double year1RhvOrderListValue) {
        this.year1RhvOrderListValue = year1RhvOrderListValue;
    }

    public Double getYear1RhvOrderConsultListValue() {
        return year1RhvOrderConsultListValue;
    }

    public void setYear1RhvOrderConsultListValue(Double year1RhvOrderConsultListValue) {
        this.year1RhvOrderConsultListValue = year1RhvOrderConsultListValue;
    }

    public Double getYear1RhvOrderTAndEValue() {
        return year1RhvOrderTAndEValue;
    }

    public void setYear1RhvOrderTAndEValue(Double year1RhvOrderTAndEValue) {
        this.year1RhvOrderTAndEValue = year1RhvOrderTAndEValue;
    }

    public Double getYear1RhvOrderLearningSubsListValue() {
        return year1RhvOrderLearningSubsListValue;
    }

    public void setYear1RhvOrderLearningSubsListValue(Double year1RhvOrderLearningSubsListValue) {
        this.year1RhvOrderLearningSubsListValue = year1RhvOrderLearningSubsListValue;
    }

    public Double getYear1RhvOrderDiscountValue() {
        return year1RhvOrderDiscountValue;
    }

    public void setYear1RhvOrderDiscountValue(Double year1RhvOrderDiscountValue) {
        this.year1RhvOrderDiscountValue = year1RhvOrderDiscountValue;
    }

    public Double getYear1RhvOrderConsultDiscountValue() {
        return year1RhvOrderConsultDiscountValue;
    }

    public void setYear1RhvOrderConsultDiscountValue(Double year1RhvOrderConsultDiscountValue) {
        this.year1RhvOrderConsultDiscountValue = year1RhvOrderConsultDiscountValue;
    }

    public Double getYear1RhvOrderTAndEDiscountValue() {
        return year1RhvOrderTAndEDiscountValue;
    }

    public void setYear1RhvOrderTAndEDiscountValue(Double year1RhvOrderTAndEDiscountValue) {
        this.year1RhvOrderTAndEDiscountValue = year1RhvOrderTAndEDiscountValue;
    }

    public Double getYear1RhvOrderLearningSubsDiscountValue() {
        return year1RhvOrderLearningSubsDiscountValue;
    }

    public void setYear1RhvOrderLearningSubsDiscountValue(Double year1RhvOrderLearningSubsDiscountValue) {
        this.year1RhvOrderLearningSubsDiscountValue = year1RhvOrderLearningSubsDiscountValue;
    }

    public Double getYear1RhvOrderTotalValue() {
        return year1RhvOrderTotalValue;
    }

    public void setYear1RhvOrderTotalValue(Double year1RhvOrderTotalValue) {
        this.year1RhvOrderTotalValue = year1RhvOrderTotalValue;
    }

    public Double getYear1RhvOrderConsultTotalValue() {
        return year1RhvOrderConsultTotalValue;
    }

    public void setYear1RhvOrderConsultTotalValue(Double year1RhvOrderConsultTotalValue) {
        this.year1RhvOrderConsultTotalValue = year1RhvOrderConsultTotalValue;
    }

    public Double getYear1RhvOrderTAndETotalValue() {
        return year1RhvOrderTAndETotalValue;
    }

    public void setYear1RhvOrderTAndETotalValue(Double year1RhvOrderTAndETotalValue) {
        this.year1RhvOrderTAndETotalValue = year1RhvOrderTAndETotalValue;
    }

    public Double getYear1RhvOrderLearningSubsTotalValue() {
        return year1RhvOrderLearningSubsTotalValue;
    }

    public void setYear1RhvOrderLearningSubsTotalValue(Double year1RhvOrderLearningSubsTotalValue) {
        this.year1RhvOrderLearningSubsTotalValue = year1RhvOrderLearningSubsTotalValue;
    }

    public Double getYear1RhvOrderGrandTotal() {
        return year1RhvOrderGrandTotal;
    }

    public void setYear1RhvOrderGrandTotal(Double year1RhvOrderGrandTotal) {
        this.year1RhvOrderGrandTotal = year1RhvOrderGrandTotal;
    }

    public Double getYear2RhvOrderSku() {
        return year2RhvOrderSku;
    }

    public void setYear2RhvOrderSku(Double year2RhvOrderSku) {
        this.year2RhvOrderSku = year2RhvOrderSku;
    }

    public Double getYear2RhvOrder() {
        return year2RhvOrder;
    }

    public void setYear2RhvOrder(Double year2RhvOrder) {
        this.year2RhvOrder = year2RhvOrder;
    }

    public Double getYear2RhvOrderListValue() {
        return year2RhvOrderListValue;
    }

    public void setYear2RhvOrderListValue(Double year2RhvOrderListValue) {
        this.year2RhvOrderListValue = year2RhvOrderListValue;
    }

    public Double getYear2RhvOrderDiscountValue() {
        return year2RhvOrderDiscountValue;
    }

    public void setYear2RhvOrderDiscountValue(Double year2RhvOrderDiscountValue) {
        this.year2RhvOrderDiscountValue = year2RhvOrderDiscountValue;
    }

    public Double getYear2RhvOrderTotalValue() {
        return year2RhvOrderTotalValue;
    }

    public void setYear2RhvOrderTotalValue(Double year2RhvOrderTotalValue) {
        this.year2RhvOrderTotalValue = year2RhvOrderTotalValue;
    }

    public Double getYear2RhvOrderGrandTotal() {
        return year2RhvOrderGrandTotal;
    }

    public void setYear2RhvOrderGrandTotal(Double year2RhvOrderGrandTotal) {
        this.year2RhvOrderGrandTotal = year2RhvOrderGrandTotal;
    }

    public Double getYear3RhvOrderSku() {
        return year3RhvOrderSku;
    }

    public void setYear3RhvOrderSku(Double year3RhvOrderSku) {
        this.year3RhvOrderSku = year3RhvOrderSku;
    }

    public Double getYear3RhvOrder() {
        return year3RhvOrder;
    }

    public void setYear3RhvOrder(Double year3RhvOrder) {
        this.year3RhvOrder = year3RhvOrder;
    }

    public Double getYear3RhvOrderListValue() {
        return year3RhvOrderListValue;
    }

    public void setYear3RhvOrderListValue(Double year3RhvOrderListValue) {
        this.year3RhvOrderListValue = year3RhvOrderListValue;
    }

    public Double getYear3RhvOrderDiscountValue() {
        return year3RhvOrderDiscountValue;
    }

    public void setYear3RhvOrderDiscountValue(Double year3RhvOrderDiscountValue) {
        this.year3RhvOrderDiscountValue = year3RhvOrderDiscountValue;
    }

    public Double getYear3RhvOrderTotalValue() {
        return year3RhvOrderTotalValue;
    }

    public void setYear3RhvOrderTotalValue(Double year3RhvOrderTotalValue) {
        this.year3RhvOrderTotalValue = year3RhvOrderTotalValue;
    }

    public Double getYear3RhvOrderGrandTotal() {
        return year3RhvOrderGrandTotal;
    }

    public void setYear3RhvOrderGrandTotal(Double year3RhvOrderGrandTotal) {
        this.year3RhvOrderGrandTotal = year3RhvOrderGrandTotal;
    }

    public InitialSavingsEstimationReportModel getReport() {
        return report;
    }

    public void setReport(InitialSavingsEstimationReportModel report) {
        this.report = report;
    }
}
