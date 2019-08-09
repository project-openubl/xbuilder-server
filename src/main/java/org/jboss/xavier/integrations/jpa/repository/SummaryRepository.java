package org.jboss.xavier.integrations.jpa.repository;

import org.jboss.xavier.analytics.pojo.output.workload.summary.SummaryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SummaryRepository  extends JpaRepository<SummaryModel, Long>
{
    // this name has to match the value after the '.' in the @NamedNativeQuery annotation
    List<SummaryModel> calculateSummaryModels(@Param("analysisId") Long analysisId);
}
