package org.jboss.xavier.analytics.pojo.output.workload.summary;

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
        name = "mappingSummaryModels",
        classes = @ConstructorResult(
                targetClass = SummaryModel.class,
                columns = {
                        @ColumnResult(name = "provider", type = String.class),
                        @ColumnResult(name = "product", type = String.class),
                        @ColumnResult(name = "version", type = String.class),
                        @ColumnResult(name = "hosts", type = Integer.class),
                        @ColumnResult(name = "clusters", type = Integer.class),
                        @ColumnResult(name = "sockets", type = Long.class),
                        @ColumnResult(name = "vms", type = Integer.class)
                }
        )
)

@NamedNativeQuery(
        name = "SummaryModel.calculateSummaryModels",
        query = "select provider, product, version, count(distinct host_name) as hosts, count(distinct cluster) as clusters, sum(cpu_cores) as sockets, count(*) as vms \n" +
                "from workload_inventory_report_model \n" +
                "where analysis_id = :analysisId \n" +
                "group by provider, product, version \n" +
                "order by provider, product, version",
        resultSetMapping = "mappingSummaryModels"
)

@Entity
public class SummaryModel
{
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO, generator = "SUMMARYMODEL_ID_GENERATOR")
    @GenericGenerator(
            name = "SUMMARYMODEL_ID_GENERATOR",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "SUMMARYMODEL_SEQUENCE")
            }
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id")
    @JsonBackReference
    private WorkloadSummaryReportModel report;

    private String provider;
    private Integer clusters;
    private Long sockets;
    private Integer vms;
    private Integer hosts;
    private String product;
    private String version;

    public SummaryModel() {}

    public SummaryModel(String provider, String product, String version, Integer hosts, Integer clusters, Long sockets, Integer vms) {
        this.provider = provider;
        this.product = product;
        this.version = version;
        this.hosts = hosts;
        this.clusters = clusters;
        this.sockets = sockets;
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

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Integer getClusters() {
        return clusters;
    }

    public void setClusters(Integer clusters) {
        this.clusters = clusters;
    }

    public Long getSockets() {
        return sockets;
    }

    public void setSockets(Long sockets) {
        this.sockets = sockets;
    }

    public Integer getVms() {
        return vms;
    }

    public void setVms(Integer vms) {
        this.vms = vms;
    }

    public Integer getHosts() {
        return hosts;
    }

    public void setHosts(Integer hosts) {
        this.hosts = hosts;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "SummaryModel{" +
                "id=" + id +
                ", report=" + report +
                ", providers='" + provider +
                ", clusters=" + clusters +
                ", sockets=" + sockets +
                ", vms=" + vms +
                ", hosts=" + hosts +
                ", product=" + product +
                ", version=" + version +
                '}';
    }
}
