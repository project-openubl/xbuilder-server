package org.jboss.xavier.integrations.jpa.service;

import org.jboss.xavier.analytics.pojo.output.workload.summary.SummaryModel;
import org.jboss.xavier.integrations.jpa.repository.SummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SummaryService
{
    @Autowired
    SummaryRepository summaryRepository;

    public List<SummaryModel> calculateSummaryModels(Long analysisId)
    {
        return summaryRepository.calculateSummaryModels(analysisId);
    }
}
