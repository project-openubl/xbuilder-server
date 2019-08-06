package org.jboss.xavier.integrations.jpa.repository;

import org.jboss.xavier.analytics.pojo.output.InitialSavingsEstimationReportModel;
import org.jboss.xavier.integrations.jpa.projection.InitialSavingsEstimationReportSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InitialSavingsEstimationReportRepository extends JpaRepository<InitialSavingsEstimationReportModel, Long>
{
    Iterable<InitialSavingsEstimationReportSummary> findAllReportSummaryBy();

    Page<InitialSavingsEstimationReportSummary> findAllReportSummaryBy(Pageable pageable);

    InitialSavingsEstimationReportSummary findReportSummaryById(Long id);

    InitialSavingsEstimationReportModel findOneByAnalysisId(Long id);
}
