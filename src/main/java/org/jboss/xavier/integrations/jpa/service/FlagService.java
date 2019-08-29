package org.jboss.xavier.integrations.jpa.service;

import org.jboss.xavier.analytics.pojo.output.workload.summary.FlagModel;
import org.jboss.xavier.integrations.jpa.repository.FlagRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}
