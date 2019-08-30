package org.jboss.xavier.analytics.pojo.output.workload.summary;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@SqlResultSetMapping(
        name = "mappingWorkloadsDetectedOSTypeModels",
        classes = @ConstructorResult(
                targetClass = WorkloadsDetectedOSTypeModel.class,
                columns = {
                        @ColumnResult(name = "osName", type = String.class),
                        @ColumnResult(name = "total", type = Integer.class)
                }
        )
)

@NamedNativeQuery(
        name = "WorkloadsDetectedOSTypeModel.calculateWorkloadsDetectedOSTypeModels",
        query = "select coalesce(wm.os_name, 'unknown') as osName, sum(wm.vms) as total \n" +
                "from workload_model wm \n" +
                "inner join workload_summary_report_model ws on wm.report_id=ws.id \n" +
                "inner join analysis_model am on ws.analysis_id=am.id \n" +
                "where am.id = :analysisId \n" +
                "group by os_name \n" +
                "order by os_name",
        resultSetMapping = "mappingWorkloadsDetectedOSTypeModels"
)

@Entity
public class WorkloadsDetectedOSTypeModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "WORKLOADSDETECTEDOSTYPEMODEL_ID_GENERATOR")
    @GenericGenerator(
            name = "WORKLOADSDETECTEDOSTYPEMODEL_ID_GENERATOR",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "WORKLOADSDETECTEDOSTYPEMODEL_SEQUENCE")
            }
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id")
    @JsonBackReference
    private WorkloadSummaryReportModel report;

    private String osName;
    private Integer total;

    public WorkloadsDetectedOSTypeModel() {}

    public WorkloadsDetectedOSTypeModel(String osName, Integer total) {
        this.osName = osName;
        this.total = total;
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

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "WorkloadsDetectedOSTypeModel{" +
                "id=" + id +
                ", report=" + report +
                ", osName='" + osName +
                ", total=" + total +
                '}';
    }
}
