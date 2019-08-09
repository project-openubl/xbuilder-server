package org.jboss.xavier.integrations.jpa.service;

import org.jboss.xavier.analytics.pojo.output.workload.summary.WorkloadSummaryReportModel;
import org.jboss.xavier.integrations.jpa.repository.WorkloadSummaryReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkloadSummaryReportService
{
    @Autowired
    WorkloadSummaryReportRepository reportRepository;

    public WorkloadSummaryReportModel findByAnalysisId(Long analysisId)
    {
        return reportRepository.findByAnalysisId(analysisId);
    }
}
