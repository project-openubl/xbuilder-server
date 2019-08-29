package org.jboss.xavier.analytics.pojo.output.workload.summary;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@SqlResultSetMapping(
        name = "mappingWorkloadModels",
        classes = @ConstructorResult(
                targetClass = WorkloadModel.class,
                columns = {
                        @ColumnResult(name = "workload", type = String.class),
                        @ColumnResult(name = "osName", type = String.class),
                        @ColumnResult(name = "clusters", type = Integer.class),
                        @ColumnResult(name = "vms", type = Integer.class)
                }
        )
)

@NamedNativeQuery(
        name = "WorkloadModel.calculateWorkloadsModels",
        query = "select B.workloads as workload, A.os_name as osName, count(distinct A.cluster) as clusters, count(distinct A.id) as vms \n" +
                "from workload_inventory_report_model A,  workload_inventory_report_model_workloads B \n" +
                "where A.analysis_id = :analysisId and A.id=B.workload_inventory_report_model_id \n" +
                "group by B.workloads, A.os_name \n" +
                "order by B.workloads, A.os_name, vms;",
        resultSetMapping = "mappingWorkloadModels"
)

@Entity
public class WorkloadModel
{
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO, generator = "WORKLOADMODEL_ID_GENERATOR")
    @GenericGenerator(
            name = "WORKLOADMODEL_ID_GENERATOR",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "WORKLOADMODEL_SEQUENCE")
            }
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id")
    @JsonBackReference
    private WorkloadSummaryReportModel report;

    private String workload;
    private String osName;
    private Integer clusters;
    private Integer vms;

    public WorkloadModel() {}

    public WorkloadModel(String workload, String osName, Integer clusters, Integer vms) {
        this.workload = workload;
        this.osName = osName;
        this.clusters = clusters;
        this.vms = vms;
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

    public String getWorkload() {
        return workload;
    }

    public void setWorkload(String workload) {
        this.workload = workload;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public Integer getClusters() {
        return clusters;
    }

    public void setClusters(Integer clusters) {
        this.clusters = clusters;
    }

    public Integer getVms() {
        return vms;
    }

    public void setVms(Integer vms) {
        this.vms = vms;
    }

    @Override
    public String toString() {
        return "WorkloadDetectedModel{" +
                "id=" + id +
                ", report=" + report +
                ", workload='" + workload +
                ", osName=" + osName +
                ", clusters=" + clusters +
                ", vms=" + vms +
                '}';
    }
}
