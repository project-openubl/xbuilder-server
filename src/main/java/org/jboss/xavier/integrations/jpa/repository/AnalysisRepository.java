package org.jboss.xavier.integrations.jpa.repository;

import org.jboss.xavier.analytics.pojo.AdministrationMetricsProjection;
import org.jboss.xavier.analytics.pojo.output.AnalysisModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AnalysisRepository extends JpaRepository<AnalysisModel, Long>
{

    Page<AnalysisModel> findByOwnerAndReportNameIgnoreCaseContaining(String owner, String filterText, Pageable pageable);

    Page<AnalysisModel> findAllByOwner(String owner, Pageable pageable);

    AnalysisModel findByOwnerAndId(String owner, Long id);

    Integer countByOwner(String owner);

    AnalysisModel findByIdAndStatusIgnoreCaseNot(Long id, String status);

    @Query(nativeQuery = true, value = "\n" +
            "select am.id, am.owner, am.payload_name as payloadName, am.status as analysisStatus, am.inserted as analysisInserted, count(wirm.id) as totalVms \n" +
            "from analysis_model am \n" +
            "left join workload_inventory_report_model wirm \n" +
            "on am.id = wirm.analysis_id \n" +
            "where am.inserted between :fromDate and :toDate \n" +
            "group by am.id \n" +
            "order by am.id")
    List<AdministrationMetricsProjection> getAdministrationMetrics(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
}
