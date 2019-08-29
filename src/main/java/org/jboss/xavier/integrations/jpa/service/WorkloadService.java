package org.jboss.xavier.integrations.jpa.service;

import org.jboss.xavier.analytics.pojo.output.workload.summary.WorkloadModel;
import org.jboss.xavier.integrations.jpa.repository.WorkloadRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

}
