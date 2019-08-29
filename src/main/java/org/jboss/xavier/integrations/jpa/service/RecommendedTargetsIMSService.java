package org.jboss.xavier.integrations.jpa.service;

import org.jboss.xavier.analytics.pojo.output.workload.summary.RecommendedTargetsIMSModel;
import org.jboss.xavier.integrations.jpa.repository.RecommendedTargetsIMSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecommendedTargetsIMSService
{
    @Autowired
    RecommendedTargetsIMSRepository recommendedTargetsIMSRepository;

    public RecommendedTargetsIMSModel calculateRecommendedTargetsIMS(Long analysisId)
    {
        return recommendedTargetsIMSRepository.calculateRecommendedTargetsIMS(analysisId);
    }
}
