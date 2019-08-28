package org.jboss.xavier.analytics.pojo.output.workload.summary;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@SqlResultSetMapping(
        name = "mappingComplexityModels",
        classes = @ConstructorResult(
                targetClass = ComplexityModel.class,
                columns = {
                        @ColumnResult(name = "easy", type = Integer.class),
                        @ColumnResult(name = "medium", type = Integer.class),
                        @ColumnResult(name = "hard", type = Integer.class),
                        @ColumnResult(name = "unknown", type = Integer.class)
                }
        )
)

@NamedNativeQuery(
        name = "ComplexityModel.calculateComplexityModels",
        query = "select sum(case when lower(complexity)='easy' then 1 else 0 end) as easy, sum(case when lower(complexity)='medium' then 1 else 0 end) as medium, sum(case when lower(complexity)='hard' then 1 else 0 end) as hard, sum(case when (complexity is null or lower(complexity)='unknown') then 1 else 0 end) as \"unknown\" from workload_inventory_report_model where analysis_id = :analysisId",
        resultSetMapping = "mappingComplexityModels"
)

@Entity
public class ComplexityModel
{
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO, generator = "COMPLEXITYMODEL_ID_GENERATOR")
    @GenericGenerator(
            name = "COMPLEXITYMODEL_ID_GENERATOR",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "COMPLEXITYMODEL_SEQUENCE")
            }
    )
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JsonBackReference
    private WorkloadSummaryReportModel report;

    private Integer easy;
    private Integer medium;
    private Integer hard;
    private Integer unknown;

    public ComplexityModel() {}

    public ComplexityModel(Integer easy, Integer medium, Integer hard, Integer unknown) {
        this.easy = easy;
        this.medium = medium;
        this.hard = hard;
        this.unknown = unknown;
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

    public Integer getEasy() {
        return easy;
    }

    public void setEasy(Integer easy) {
        this.easy = easy;
    }

    public Integer getMedium() {
        return medium;
    }

    public void setMedium(Integer medium) {
        this.medium = medium;
    }

    public Integer getHard() {
        return hard;
    }

    public void setHard(Integer hard) {
        this.hard = hard;
    }

    public Integer getUnknown() {
        return unknown;
    }

    public void setUnknown(Integer unknown) {
        this.unknown = unknown;
    }

    @Override
    public String toString() {
        return "SummaryModel{" +
                "id=" + id +
                ", report=" + report +
                ", easy='" + easy +
                ", medium=" + medium +
                ", hard=" + hard +
                ", unknown=" + unknown +
                '}';
    }
}
