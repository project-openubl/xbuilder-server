package org.jboss.xavier.integrations.jpa.repository;

import org.jboss.xavier.analytics.pojo.output.workload.summary.WorkloadModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkloadRepository extends JpaRepository<WorkloadModel, Long>
{
    // this name has to match the value after the '.' in the @NamedNativeQuery annotation
    List<WorkloadModel> calculateWorkloadsModels(@Param("analysisId") Long analysisId);

    Page<WorkloadModel> findByReportAnalysisId(@Param("analysisId") Long analysisId, Pageable pageable);
}
