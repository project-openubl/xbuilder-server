package org.jboss.xavier.integrations.jpa.service;

import org.jboss.xavier.analytics.pojo.output.workload.inventory.WorkloadInventoryReportModel;
import org.jboss.xavier.integrations.jpa.repository.WorkloadInventoryReportRepository;
import org.jboss.xavier.integrations.route.model.PageBean;
import org.jboss.xavier.integrations.route.model.SortBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

import java.util.function.Function;

@Component
public class WorkloadInventoryReportService
{
    @Autowired
    WorkloadInventoryReportRepository reportRepository;

    public static final Function<String, String> mapToSupportedSortField = value -> {
        // Result should contain the name of the @Entity field
        String result = "id";
        if (value == null) {
            return result;
        }

        switch (value) {
            case "vmName":
                result = "vmName";
                break;
            case "osName":
                result = "osName";
                break;
            case "complexity":
                result = "complexity";
                break;
        }
        return result;
    };

    public List<WorkloadInventoryReportModel> findByAnalysisId(Long analysisId) {
        return reportRepository.findByAnalysisId(analysisId);
    }

    public Page<WorkloadInventoryReportModel> findByAnalysisId(Long analysisId, PageBean pageBean, SortBean sortBean) {
        // Sort
        Sort.Direction sortDirection = sortBean.isOrderAsc() ? Sort.Direction.ASC : Sort.Direction.DESC;
        String orderBy = mapToSupportedSortField.apply(sortBean.getOrderBy());
        Sort sort = new Sort(sortDirection, orderBy);

        // Pagination
        int page = pageBean.getPage();
        int size = pageBean.getSize();
        Pageable pageable = new PageRequest(page, size, sort);

        return reportRepository.findByAnalysisId(analysisId, pageable);
    }
}
