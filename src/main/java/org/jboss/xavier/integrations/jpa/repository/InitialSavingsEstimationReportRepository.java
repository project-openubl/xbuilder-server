package org.jboss.xavier.integrations.jpa.repository;

import org.jboss.xavier.analytics.pojo.output.InitialSavingsEstimationReportModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InitialSavingsEstimationReportRepository extends JpaRepository<InitialSavingsEstimationReportModel, Long>
{
    InitialSavingsEstimationReportModel findByAnalysisOwnerAndAnalysisId(String owner, Long id);
}
