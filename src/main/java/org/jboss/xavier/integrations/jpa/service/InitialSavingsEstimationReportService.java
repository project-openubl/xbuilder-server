package org.jboss.xavier.integrations.jpa.service;

import org.jboss.xavier.analytics.pojo.output.InitialSavingsEstimationReportModel;
import org.jboss.xavier.integrations.jpa.repository.InitialSavingsEstimationReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitialSavingsEstimationReportService
{
    @Autowired
    InitialSavingsEstimationReportRepository reportRepository;

    public InitialSavingsEstimationReportModel findByAnalysisOwnerAndAnalysisId(String owner, Long id)
    {
        return reportRepository.findByAnalysisOwnerAndAnalysisId(owner, id);
    }
}
