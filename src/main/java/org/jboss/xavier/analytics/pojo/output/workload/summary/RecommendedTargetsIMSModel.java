package org.jboss.xavier.analytics.pojo.output.workload.summary;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@SqlResultSetMapping(
        name = "mappingRecommendedTargetsIMSModels",
        classes = @ConstructorResult(
                targetClass = RecommendedTargetsIMSModel.class,
                columns = {
                        @ColumnResult(name = "total", type = Integer.class),
                        @ColumnResult(name = "rhv", type = Integer.class),
                        @ColumnResult(name = "osp", type = Integer.class),
                        @ColumnResult(name = "rhel", type = Integer.class)
                }
        )
)

@NamedNativeQuery(
        name = "RecommendedTargetsIMSModel.calculateRecommendedTargetsIMS",
        query = "select count(distinct wi.id) as total, coalesce(sum(case when lower(rt.recommended_targetsims)='rhv' then 1 else 0 end), 0) as rhv, coalesce(sum(case when lower(rt.recommended_targetsims)='osp' then 1 else 0 end), 0) as osp, coalesce(sum(case when lower(rt.recommended_targetsims)='convert2rhel' then 1 else 0 end), 0) as rhel from workload_inventory_report_model_recommended_targetsims rt right join workload_inventory_report_model wi on rt.workload_inventory_report_model_id=wi.id where wi.analysis_id = :analysisId",
        resultSetMapping = "mappingRecommendedTargetsIMSModels"
)

@Entity
public class RecommendedTargetsIMSModel
{

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO, generator = "RECOMMENDEDTARGETSIMS_ID_GENERATOR")
    @GenericGenerator(
            name = "RECOMMENDEDTARGETSIMS_ID_GENERATOR",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "RECOMMENDEDTARGETSIMS_SEQUENCE")
            }
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id")
    @JsonBackReference
    private WorkloadSummaryReportModel report;

    private Integer total;
    private Integer rhv;
    private Integer rhel;
    private Integer osp;

    public RecommendedTargetsIMSModel() {}

    public RecommendedTargetsIMSModel(Integer total, Integer rhv, Integer osp, Integer rhel) {
        this.total = total;
        this.rhv = rhv;
        this.osp = osp;
        this.rhel = rhel;
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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getRhv() {
        return rhv;
    }

    public void setRhv(Integer rhv) {
        this.rhv = rhv;
    }

    public Integer getRhel() {
        return rhel;
    }

    public void setRhel(Integer rhel) {
        this.rhel = rhel;
    }

    public Integer getOsp() {
        return osp;
    }

    public void setOsp(Integer osp) {
        this.osp = osp;
    }

    @Override
    public String toString() {
        return "SummaryModel{" +
                "id=" + id +
                ", report=" + report +
                ", rhv='" + rhv +
                ", rhel=" + rhel +
                ", osp=" + osp +
                '}';
    }

}
