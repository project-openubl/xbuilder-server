package org.jboss.xavier.integrations.jpa.repository;

import org.jboss.xavier.analytics.pojo.output.workload.summary.FlagAssessmentIdentityModel;
import org.jboss.xavier.analytics.pojo.output.workload.summary.FlagAssessmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlagAssessmentRepository extends JpaRepository<FlagAssessmentModel, FlagAssessmentIdentityModel>
{
}
