package org.jboss.xavier.integrations.jpa.repository;

import org.jboss.xavier.analytics.pojo.output.workload.summary.WorkloadSummaryReportModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkloadSummaryReportRepository extends JpaRepository<WorkloadSummaryReportModel, Long>
{
    WorkloadSummaryReportModel findByAnalysisOwnerAndAnalysisId(String analysisOwner, Long analysisId);
}
