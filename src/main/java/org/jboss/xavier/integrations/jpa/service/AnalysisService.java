package org.jboss.xavier.integrations.jpa.service;

import org.jboss.xavier.analytics.pojo.AdministrationMetricsProjection;
import org.jboss.xavier.analytics.pojo.output.AnalysisModel;
import org.jboss.xavier.analytics.pojo.output.InitialSavingsEstimationReportModel;
import org.jboss.xavier.analytics.pojo.output.workload.inventory.WorkloadInventoryReportModel;
import org.jboss.xavier.analytics.pojo.output.workload.summary.WorkloadSummaryReportModel;
import org.jboss.xavier.integrations.jpa.repository.AnalysisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class AnalysisService
{
    public enum STATUS {
        FAILED,
        IN_PROGRESS,
        CREATED
    }

    @Autowired
    AnalysisRepository analysisRepository;

    // WARNING: BE CAREFUL
    // think about changing this "private" modifier
    // every time you "find" a report it should check that the
    // analysis belongs to the right owner
    private AnalysisModel findById(Long id)
    {
        return analysisRepository.findOne(id);
    }

    public AnalysisModel findByOwnerAndId(String owner, Long id)
    {
        return analysisRepository.findByOwnerAndId(owner, id);
    }

    public void deleteById(Long id)
    {
        analysisRepository.delete(id);
    }

    public AnalysisModel buildAndSave(String reportName, String reportDescription, String payloadName, String owner) {
        AnalysisModel analysisModel = new AnalysisModel();
        analysisModel.setPayloadName(payloadName);
        analysisModel.setReportDescription(reportDescription);
        analysisModel.setReportName(reportName);
        analysisModel.setInserted(new Date());
        analysisModel.setLastUpdate(new Date());
        analysisModel.setStatus(STATUS.IN_PROGRESS.toString());
        analysisModel.setOwner(owner);
        return analysisRepository.save(analysisModel);
    }

    public void setInitialSavingsEstimationReportModel(InitialSavingsEstimationReportModel reportModel, Long id) {
        AnalysisModel analysisModel = findById(id);
        analysisModel.setInitialSavingsEstimationReportModel(reportModel);
        analysisRepository.save(analysisModel);
    }

    @Deprecated
    public void addWorkloadInventoryReportModel(WorkloadInventoryReportModel reportModel, Long id) {
        AnalysisModel analysisModel = findById(id);
        analysisModel.addWorkloadInventoryReportModel(reportModel);
        analysisRepository.save(analysisModel);
    }

    public void addWorkloadInventoryReportModels(List<WorkloadInventoryReportModel> reportModels, Long id) {
        AnalysisModel analysisModel = findById(id);
        analysisModel.setWorkloadInventoryReportModels(reportModels);
        analysisRepository.save(analysisModel);
    }

    public void setWorkloadSummaryReportModel(WorkloadSummaryReportModel reportModel, Long id) {
        AnalysisModel analysisModel = findById(id);
        analysisModel.setWorkloadSummaryReportModels(reportModel);
        reportModel.setAnalysis(analysisModel);
        analysisRepository.save(analysisModel);
    }

    public void setWorkloadSummaryReportModelAndUpdateStatus(WorkloadSummaryReportModel reportModel, Long id) {
        AnalysisModel analysisModel = findById(id);
        analysisModel.setWorkloadSummaryReportModels(reportModel);
        reportModel.setAnalysis(analysisModel);
        // TODO remove this since it's just a temporary workaround to change the status
        if (!STATUS.FAILED.toString().equalsIgnoreCase(analysisModel.getStatus())) {
            analysisModel.setStatus(STATUS.CREATED.toString());
        }
        analysisModel.setLastUpdate(new Date());
        analysisRepository.save(analysisModel);
    }

    public Page<AnalysisModel> findAllByOwner(String owner, int page, int size)
    {
        Pageable pageable = new PageRequest(page, size, new Sort(Sort.Direction.DESC, "id"));
        return analysisRepository.findAllByOwner(owner, pageable);
    }

    public Integer countByOwner(String owner)
    {
        return analysisRepository.countByOwner(owner);
    }

    public Page<AnalysisModel> findByOwnerAndReportName(String owner, String filterText, int page, int size)
    {
        Pageable pageable = new PageRequest(page, size, new Sort(Sort.Direction.DESC, "id"));
        return analysisRepository.findByOwnerAndReportNameIgnoreCaseContaining(owner, filterText.trim(), pageable);
    }

    public void updateStatus(String status, Long id) {
        AnalysisModel analysisModel = findById(id);
        analysisModel.setStatus(status);
        analysisModel.setLastUpdate(new Date());
        analysisRepository.save(analysisModel);
    }

    public List<AdministrationMetricsProjection> getAdministrationMetrics(Date fromDate, Date toDate)
    {
        return analysisRepository.getAdministrationMetrics(fromDate, toDate);
    }

    public void markAsFailedIfNotCreated(Long id) {
        AnalysisModel analysisModel = findByIdAndStatusIgnoreCaseNot(id, STATUS.CREATED.toString());
        if (analysisModel != null) {
            analysisModel.setStatus(STATUS.FAILED.toString());
            analysisModel.setLastUpdate(new Date());
            analysisRepository.save(analysisModel);
        }
    }

    public AnalysisModel findByIdAndStatusIgnoreCaseNot(Long id, String status) {
        return analysisRepository.findByIdAndStatusIgnoreCaseNot(id, status);
    }
}
