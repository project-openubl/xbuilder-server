package org.jboss.xavier.integrations.jpa.service;

import org.jboss.xavier.analytics.pojo.output.workload.summary.WorkloadsDetectedOSTypeModel;
import org.jboss.xavier.integrations.jpa.repository.WorkloadsDetectedOSTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WorkloadsDetectedOSTypeService
{
    @Autowired
    WorkloadsDetectedOSTypeRepository workloadsDetectedRepository;

    public List<WorkloadsDetectedOSTypeModel> calculateWorkloadsDetectedOSTypeModels(Long analysisId)
    {
        return workloadsDetectedRepository.calculateWorkloadsDetectedOSTypeModels(analysisId);
    }
}
