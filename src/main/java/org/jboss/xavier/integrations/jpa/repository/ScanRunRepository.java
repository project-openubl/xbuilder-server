package org.jboss.xavier.integrations.jpa.repository;

import org.jboss.xavier.analytics.pojo.output.workload.summary.ScanRunModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ScanRunRepository   extends JpaRepository<ScanRunModel, Long> {
    // this name has to match the value after the '.' in the @NamedNativeQuery annotation
    Set<ScanRunModel> calculateScanRunModels(@Param("analysisId") Long analysisId);
}
