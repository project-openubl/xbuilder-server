package org.jboss.xavier.integrations.jpa.repository;

import org.jboss.xavier.integrations.jpa.projection.ReportSummary;
import org.jboss.xavier.integrations.migrationanalytics.output.ReportDataModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<ReportDataModel, Long>
{
    Iterable<ReportSummary> findAllReportSummaryBy();
}
