package org.jboss.xavier.integrations.jpa.service;

import org.jboss.xavier.analytics.pojo.output.WorkloadInventoryReportModel;
import org.jboss.xavier.integrations.jpa.repository.WorkloadInventoryReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class WorkloadInventoryReportService
{
    @Autowired
    WorkloadInventoryReportRepository reportRepository;

    public Page<WorkloadInventoryReportModel> findByAnalysisId(Long analysisId, int page, int size)
    {
        Pageable pageable = new PageRequest(page, size);
        return reportRepository.findByAnalysisId(analysisId, pageable);
    }
}
