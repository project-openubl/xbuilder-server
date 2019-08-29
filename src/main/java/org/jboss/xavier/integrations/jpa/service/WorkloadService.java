package org.jboss.xavier.integrations.jpa.service;

import org.jboss.xavier.analytics.pojo.output.workload.summary.WorkloadModel;
import org.jboss.xavier.integrations.jpa.repository.WorkloadRepository;
import org.jboss.xavier.integrations.route.model.PageBean;
import org.jboss.xavier.integrations.route.model.SortBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WorkloadService
{
    @Autowired
    WorkloadRepository workloadRepository;

    public List<WorkloadModel> calculateWorkloadsModels(Long analysisId)
    {
        return workloadRepository.calculateWorkloadsModels(analysisId);
    }

    public Page<WorkloadModel> findByReportAnalysisId(Long analysisId, PageBean pageBean, SortBean sortBean) {
        // Sort
        Sort.Direction sortDirection = sortBean.isOrderAsc() ? Sort.Direction.ASC : Sort.Direction.DESC;
        String orderBy = WorkloadModel.SUPPORTED_SORT_FIELDS.contains(sortBean.getOrderBy()) ? sortBean.getOrderBy() : WorkloadModel.DEFAULT_SORT_FIELD;
        Sort sort = new Sort(sortDirection, orderBy);

        // Pagination
        int page = pageBean.getPage();
        int size = pageBean.getSize();
        Pageable pageable = new PageRequest(page, size, sort);

        return workloadRepository.findByReportAnalysisId(analysisId, pageable);
    }

}
