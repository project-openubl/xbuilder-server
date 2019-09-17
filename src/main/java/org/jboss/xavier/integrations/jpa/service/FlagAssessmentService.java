package org.jboss.xavier.integrations.jpa.service;

import org.jboss.xavier.analytics.pojo.output.workload.summary.FlagAssessmentModel;
import org.jboss.xavier.integrations.jpa.repository.FlagAssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FlagAssessmentService
{
    @Autowired
    FlagAssessmentRepository flagAssessmentRepository;

    public List<FlagAssessmentModel> findAll()
    {
        return flagAssessmentRepository.findAll();
    }

}
