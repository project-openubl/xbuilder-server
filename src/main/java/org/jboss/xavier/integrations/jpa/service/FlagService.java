package org.jboss.xavier.integrations.jpa.service;

import org.jboss.xavier.analytics.pojo.output.workload.summary.FlagModel;
import org.jboss.xavier.integrations.jpa.repository.FlagRepository;
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
public class FlagService
{
    @Autowired
    FlagRepository flagRepository;

    public List<FlagModel> calculateFlagModels(Long analysisId)
    {
        return flagRepository.calculateFlagModels(analysisId);
    }

    public Page<FlagModel> findByReportAnalysisOwnerAndReportAnalysisId(String analysisOwner, Long analysisId, PageBean pageBean, SortBean sortBean)
    {
        // Sort
        Sort.Direction sortDirection = sortBean.isOrderAsc() ? Sort.Direction.ASC : Sort.Direction.DESC;
        String orderBy = FlagModel.SUPPORTED_SORT_FIELDS.contains(sortBean.getOrderBy()) ? sortBean.getOrderBy() : FlagModel.DEFAULT_SORT_FIELD;
        Sort sort = new Sort(sortDirection, orderBy);

        // Pagination
        int page = pageBean.getPage();
        int size = pageBean.getSize();
        Pageable pageable = new PageRequest(page, size, sort);

        return flagRepository.findByReportAnalysisOwnerAndReportAnalysisId(analysisOwner, analysisId, pageable);
    }
}
