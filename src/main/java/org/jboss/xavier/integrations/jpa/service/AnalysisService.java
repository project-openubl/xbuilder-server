package org.jboss.xavier.integrations.jpa.service;

import org.jboss.xavier.analytics.pojo.output.AnalysisModel;
import org.jboss.xavier.analytics.pojo.output.InitialSavingsEstimationReportModel;
import org.jboss.xavier.analytics.pojo.output.workload.inventory.WorkloadInventoryReportModel;
import org.jboss.xavier.integrations.jpa.repository.AnalysisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AnalysisService
{
    @Autowired
    AnalysisRepository analysisRepository;

    public AnalysisModel findById(Long id)
    {
        return analysisRepository.findOne(id);
    }

    public void deleteById(Long id)
    {
        analysisRepository.delete(id);
    }

    public AnalysisModel buildAndSave(String reportName, String reportDescription, String payloadName) {
        AnalysisModel analysisModel = new AnalysisModel();
        analysisModel.setPayloadName(payloadName);
        analysisModel.setReportDescription(reportDescription);
        analysisModel.setReportName(reportName);
        analysisModel.setInserted(new Date());
        analysisModel.setLastUpdate(new Date());
        analysisModel.setStatus("IN_PROGRESS");

        return analysisRepository.save(analysisModel);
    }

    public void setInitialSavingsEstimationReportModel(InitialSavingsEstimationReportModel reportModel, Long id) {
        AnalysisModel analysisModel = findById(id);
        analysisModel.setInitialSavingsEstimationReportModel(reportModel);
        // TODO remove this since it's just a temporary workaround to change the status
        analysisModel.setStatus("CREATED");
        analysisRepository.save(analysisModel);
    }

    public void addWorkloadInventoryReportModel(WorkloadInventoryReportModel reportModel, Long id) {
        AnalysisModel analysisModel = findById(id);
        analysisModel.addWorkloadInventoryReportModel(reportModel);
        analysisRepository.save(analysisModel);
    }

    public Page<AnalysisModel> findReports(int page, int size)
    {
        Pageable pageable = new PageRequest(page, size, new Sort(Sort.Direction.DESC, "id"));
        return analysisRepository.findAll(pageable);
    }
}
