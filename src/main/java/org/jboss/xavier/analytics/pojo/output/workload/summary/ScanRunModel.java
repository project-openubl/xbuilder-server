package org.jboss.xavier.analytics.pojo.output.workload.summary;

import java.sql.Timestamp;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

@SqlResultSetMapping(
        name = "mappingScanRunModels",
        classes = @ConstructorResult(
                targetClass = ScanRunModel.class,
                columns = {
                        @ColumnResult(name = "target", type = String.class),
                        @ColumnResult(name = "scan_date", type = Timestamp.class),
                        @ColumnResult(name = "type", type = Boolean.class)
                }
        )
)

@NamedNativeQuery(
        name = "ScanRunModel.calculateScanRunModels",
        query = "select provider as target, cast(creation_date as date) as scan_date, bool_or(ssa_enabled) as type from workload_inventory_report_model  where analysis_id = :analysisId group by provider, scan_date order by provider;",
        resultSetMapping = "mappingScanRunModels"
)

@Entity
public class ScanRunModel {

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO, generator = "SCANRUNMODEL_ID_GENERATOR")
    @GenericGenerator(
            name = "SCANRUNMODEL_ID_GENERATOR",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "SCANRUNMODEL_SEQUENCE")
            }
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id")
    @JsonBackReference
    private WorkloadSummaryReportModel report;


    private String target;
    private Date date;
    private Boolean type;

    public ScanRunModel(){}

    public ScanRunModel(String target, Date date, Boolean type){
        this.target = target;
        this.date = date;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkloadSummaryReportModel getReport() {
        return report;
    }

    public void setReport(WorkloadSummaryReportModel report) {
        this.report = report;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        String typeString = "Virt Platform";
        return typeString + (type?" + SmartState": "");
    }

    public void setType(Boolean type) {
        this.type = type;
    }
}
