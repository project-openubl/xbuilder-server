package org.jboss.xavier.integrations.jpa.repository;

import org.jboss.xavier.analytics.pojo.output.workload.summary.ComplexityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplexityRepository extends JpaRepository<ComplexityModel, Long>
{
    // this name has to match the value after the '.' in the @NamedNativeQuery annotation
    ComplexityModel calculateComplexityModels(@Param("analysisId") Long analysisId);
}
