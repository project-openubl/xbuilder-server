package org.jboss.xavier.integrations.jpa.service;

import org.jboss.xavier.analytics.pojo.output.AnalysisModel;
import org.jboss.xavier.integrations.jpa.repository.AnalysisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}
