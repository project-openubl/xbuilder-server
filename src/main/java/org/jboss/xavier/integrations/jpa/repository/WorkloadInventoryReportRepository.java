package org.jboss.xavier.integrations.jpa.repository;

import org.jboss.xavier.analytics.pojo.output.workload.inventory.WorkloadInventoryReportModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.Set;

@Repository
public interface WorkloadInventoryReportRepository extends JpaRepository<WorkloadInventoryReportModel, Long>, JpaSpecificationExecutor<WorkloadInventoryReportModel>
{
    List<WorkloadInventoryReportModel> findByAnalysisOwnerAndAnalysisId(String analysisOwner, Long analysisId);

    Page<WorkloadInventoryReportModel> findByAnalysisId(Long analysisId, Pageable pageable);

    Page<WorkloadInventoryReportModel> findByAnalysisId(Long analysisId, Specification<WorkloadInventoryReportModel> specification, Pageable pageable);

    @Query(value = "select distinct wir.provider from WorkloadInventoryReportModel wir where wir.analysis.id = :analysisId")
    Set<String> findAllDistinctProvidersByAnalysisId(@Param("analysisId") Long analysisId);

    @Query(value = "select distinct wir.cluster from WorkloadInventoryReportModel wir where wir.analysis.id = :analysisId")
    Set<String> findAllDistinctClustersByAnalysisId(@Param("analysisId") Long analysisId);

    @Query(value = "select distinct wir.datacenter from WorkloadInventoryReportModel wir where wir.analysis.id = :analysisId")
    Set<String> findAllDistinctDatacentersByAnalysisId(@Param("analysisId") Long analysisId);

    @Query(value = "select distinct wir.complexity from WorkloadInventoryReportModel wir where wir.analysis.id = :analysisId")
    Set<String> findAllDistinctComplexitiesByAnalysisId(@Param("analysisId") Long analysisId);

    @Query(value = "SELECT DISTINCT workload_inventory_report_model_workloads.workloads " +
            "FROM workload_inventory_report_model_workloads " +
            "INNER JOIN workload_inventory_report_model ON workload_inventory_report_model_workloads.workload_inventory_report_model_id = workload_inventory_report_model.id " +
            "INNER JOIN analysis_model ON workload_inventory_report_model.analysis_id = analysis_model.id " +
            "WHERE analysis_model.id = :analysisId", nativeQuery = true)
    Set<String> findAllDistinctWorkloadsByAnalysisId(@Param("analysisId") Long analysisId);

    @Query(value = "SELECT DISTINCT workload_inventory_report_model_recommended_targetsims.recommended_targetsims " +
            "FROM workload_inventory_report_model_recommended_targetsims " +
            "INNER JOIN workload_inventory_report_model ON workload_inventory_report_model_recommended_targetsims.workload_inventory_report_model_id = workload_inventory_report_model.id " +
            "INNER JOIN analysis_model ON workload_inventory_report_model.analysis_id = analysis_model.id " +
            "WHERE analysis_model.id = :analysisId", nativeQuery = true)
    Set<String> findAllDistinctRecommendedTargetsIMSByAnalysisId(@Param("analysisId") Long analysisId);

    @Query(value = "SELECT DISTINCT workload_inventory_report_model_flagsims.flagsims " +
            "FROM workload_inventory_report_model_flagsims " +
            "INNER JOIN workload_inventory_report_model ON workload_inventory_report_model_flagsims.workload_inventory_report_model_id = workload_inventory_report_model.id " +
            "INNER JOIN analysis_model ON workload_inventory_report_model.analysis_id = analysis_model.id " +
            "WHERE analysis_model.id = :analysisId", nativeQuery = true)
    Set<String> findAllDistinctFlagsIMSByAnalysisId(@Param("analysisId") Long analysisId);

    @Query(value = "select distinct wir.osName from WorkloadInventoryReportModel wir where wir.analysis.id = :analysisId")
    Set<String> findAllDistinctOsNamesByAnalysisId(@Param("analysisId") Long analysisId);

}
