package org.jboss.xavier.analytics.pojo.output.workload.summary;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SqlResultSetMapping(
        name = "mappingFlagModels",
        classes = @ConstructorResult(
                targetClass = FlagModel.class,
                columns = {
                        @ColumnResult(name = "flag", type = String.class),
                        @ColumnResult(name = "osName", type = String.class),
                        @ColumnResult(name = "clusters", type = Integer.class),
                        @ColumnResult(name = "vms", type = Integer.class)
                }
        )
)

@NamedNativeQuery(
        name = "FlagModel.calculateFlagModels",
        query = "select B.flagsims as flag, A.os_name as osName, count(distinct A.cluster) as clusters, count(distinct A.id) as vms \n" +
                "from workload_inventory_report_model A,  workload_inventory_report_model_flagsims B \n" +
                "where A.analysis_id = :analysisId and A.id=B.workload_inventory_report_model_id \n" +
                "group by B.flagsims, A.os_name \n" +
                "order by vms desc, B.flagsims, A.os_name",
        resultSetMapping = "mappingFlagModels"
)

@Entity
@Table(
        indexes = {
                @Index(name = "FlagModel_" +
                        FlagModel.REPORT_ID + "_index",
                        columnList = FlagModel.REPORT_ID, unique = false),
                @Index(name = "FlagModel_" +
                        FlagModel.FLAG + "_index",
                        columnList = FlagModel.FLAG, unique = false),
                @Index(name = "FlagModel_" +
                        FlagModel.OS_NAME + "_index",
                        columnList = FlagModel.OS_NAME, unique = false),
                @Index(name = "FlagModel_" +
                        FlagModel.VMS + "_index",
                        columnList = FlagModel.VMS, unique = false)
        }
)
public class FlagModel
{

    public static final String DEFAULT_SORT_FIELD = "id";
    public static final Set<String> SUPPORTED_SORT_FIELDS = new HashSet<>(
            Arrays.asList(FlagModel.DEFAULT_SORT_FIELD, FlagModel.FLAG, FlagModel.OS_NAME, FlagModel.VMS)
    );

    static final String REPORT_ID = "report_id";
    static final String FLAG = "flag";
    static final String OS_NAME = "osName";
    static final String VMS = "vms";

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO, generator = "FLAGMODEL_ID_GENERATOR")
    @GenericGenerator(
            name = "FLAGMODEL_ID_GENERATOR",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "FLAGMODEL_SEQUENCE")
            }
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = REPORT_ID)
    @JsonBackReference
    private WorkloadSummaryReportModel report;

    private String flag;
    private String osName;
    private Integer clusters;
    private Integer vms;

    public FlagModel() {}

    public FlagModel(String flag, String osName, Integer clusters, Integer vms) {
        this.flag = flag;
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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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
        return "SummaryModel{" +
                "id=" + id +
                ", report=" + report +
                ", flag='" + flag +
                ", osName=" + osName +
                ", clusters=" + clusters +
                ", vms=" + vms +
                '}';
    }
}
