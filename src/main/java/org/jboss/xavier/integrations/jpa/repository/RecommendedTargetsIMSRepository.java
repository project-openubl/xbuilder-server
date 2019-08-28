package org.jboss.xavier.integrations.jpa.repository;

import org.jboss.xavier.analytics.pojo.output.workload.summary.RecommendedTargetsIMSModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendedTargetsIMSRepository extends JpaRepository<RecommendedTargetsIMSModel, Long>
{
    // this name has to match the value after the '.' in the @NamedNativeQuery annotation
    RecommendedTargetsIMSModel calculateRecommendedTargetsIMS(@Param("analysisId") Long analysisId);
}
