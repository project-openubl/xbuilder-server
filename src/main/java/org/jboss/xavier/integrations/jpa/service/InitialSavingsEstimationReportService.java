package org.jboss.xavier.integrations.jpa.service;

import org.jboss.xavier.analytics.pojo.output.InitialSavingsEstimationReportModel;
import org.jboss.xavier.integrations.jpa.projection.InitialSavingsEstimationReportSummary;
import org.jboss.xavier.integrations.jpa.repository.InitialSavingsEstimationReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class InitialSavingsEstimationReportService
{
    @Autowired
    InitialSavingsEstimationReportRepository reportRepository;

    public Page<InitialSavingsEstimationReportModel> findReports()
    {
        Pageable page = new PageRequest(0, 5);
        return reportRepository.findAll(page);
    }

    public Page<InitialSavingsEstimationReportModel> findReports(int page, int size)
    {
        Pageable pageable = new PageRequest(page, size);
        return reportRepository.findAll(pageable);
    }

    public Iterable<InitialSavingsEstimationReportSummary> findReportSummary()
    {
        return reportRepository.findAllReportSummaryBy();
    }

    public Page<InitialSavingsEstimationReportSummary> findReportSummary(int page, int size)
    {
        Pageable pageable = new PageRequest(page, size, new Sort(Sort.Direction.DESC, "id"));
        return reportRepository.findAllReportSummaryBy(pageable);
    }

    public InitialSavingsEstimationReportSummary findReportSummaryById(Long id)
    {
        return reportRepository.findReportSummaryById(id);
    }

    public InitialSavingsEstimationReportModel findReportDetails(Long id)
    {
        return reportRepository.findOne(id);
    }
}
