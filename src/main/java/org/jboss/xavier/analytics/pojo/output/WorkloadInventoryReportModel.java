package org.jboss.xavier.analytics.pojo.output;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Transactional
@Table(
        indexes = {
                @Index(name = "WorkloadInventoryReportModel_" +
                        WorkloadInventoryReportModel.ANALYSIS_ID + "_index",
                        columnList = WorkloadInventoryReportModel.ANALYSIS_ID, unique = false)
        }
)
public class WorkloadInventoryReportModel
{
    static final long serialVersionUID = 1L;
    static final String ANALYSIS_ID = "analysis_id";

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO, generator = "WORKLOADINVENTORYREPORTMODEL_ID_GENERATOR")
    @GenericGenerator(
            name = "WORKLOADINVENTORYREPORTMODEL_ID_GENERATOR",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "WORKLOADINVENTORYREPORT_SEQUENCE")
            }
    )
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ANALYSIS_ID)
    @JsonBackReference
    private AnalysisModel analysis;

    private String provider;
    private String datacenter;
    private String cluster;
    private String vmName;
    private String osName;
    private String osDescription;
    private BigDecimal diskSpace;
    private Integer memory;
    private Integer cpuCores;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            indexes = {
                    @Index(columnList = "workload_inventory_report_model_id", unique = false)
            }
    )
    private Set<String> workloads;
    private String complexity;
    // with "IMS" suffix in case one day we will have
    // their "AMM" counterparts
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            indexes = {
                    @Index(columnList = "workload_inventory_report_model_id", unique = false)
            }
    )
    private Set<String> recommendedTargetsIMS;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            indexes = {
                    @Index(columnList = "workload_inventory_report_model_id", unique = false)
            }
    )
    private Set<String> flagsIMS;
    private Date creationDate;

    public WorkloadInventoryReportModel() {}

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AnalysisModel getAnalysis() {
        return analysis;
    }

    public void setAnalysis(AnalysisModel analysis) {
        this.analysis = analysis;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getDatacenter() {
        return datacenter;
    }

    public void setDatacenter(String datacenter) {
        this.datacenter = datacenter;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getOsDescription() {
        return osDescription;
    }

    public void setOsDescription(String osDescription) {
        this.osDescription = osDescription;
    }

    public BigDecimal getDiskSpace() {
        return diskSpace;
    }

    public void setDiskSpace(BigDecimal diskSpace) {
        this.diskSpace = diskSpace;
    }

    public Integer getMemory() {
        return memory;
    }

    public void setMemory(Integer memory) {
        this.memory = memory;
    }

    public Integer getCpuCores() {
        return cpuCores;
    }

    public void setCpuCores(Integer cpuCores) {
        this.cpuCores = cpuCores;
    }

    public Set<String> getWorkloads() {
        return workloads;
    }

    public void setWorkloads(Set<String> workloads) {
        this.workloads = workloads;
    }

    public void addWorkload(String workload)
    {
        if (this.workloads == null) this.workloads = new HashSet<>();
        this.workloads.add(workload);
    }

    public String getComplexity() {
        return complexity;
    }

    public void setComplexity(String complexity) {
        this.complexity = complexity;
    }

    public Set<String> getRecommendedTargetsIMS() {
        return recommendedTargetsIMS;
    }

    public void setRecommendedTargetsIMS(Set<String> recommendedTargetsIMS) {
        this.recommendedTargetsIMS = recommendedTargetsIMS;
    }

    public void addRecommendedTargetsIMS(String recommendedTargetIMS)
    {
        if (this.recommendedTargetsIMS == null) this.recommendedTargetsIMS = new HashSet<>();
        this.recommendedTargetsIMS.add(recommendedTargetIMS);
    }

    public Set<String> getFlagsIMS() {
        return flagsIMS;
    }

    public void setFlagsIMS(Set<String> flagsIMS) {
        this.flagsIMS = flagsIMS;
    }

    public void addFlagIMS(String flagIMS)
    {
        if (this.flagsIMS == null) this.flagsIMS = new HashSet<>();
        this.flagsIMS.add(flagIMS);
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
