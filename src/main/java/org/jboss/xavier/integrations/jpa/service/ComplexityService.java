package org.jboss.xavier.integrations.jpa.service;

import org.jboss.xavier.analytics.pojo.output.workload.summary.ComplexityModel;
import org.jboss.xavier.integrations.jpa.repository.ComplexityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComplexityService
{
    @Autowired
    ComplexityRepository complexityRepository;

    public ComplexityModel calculateComplexityModels(Long analysisId)
    {
        return complexityRepository.calculateComplexityModels(analysisId);
    }
}
