package org.jboss.xavier.integrations.jpa.repository;

import org.jboss.xavier.analytics.pojo.output.AnalysisModel;
import org.jboss.xavier.analytics.pojo.output.workload.inventory.WorkloadInventoryReportModel;
import org.jboss.xavier.integrations.route.model.WorkloadInventoryFilterBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class WorkloadInventoryReportSpecs {

    private WorkloadInventoryReportSpecs() {
        // TODO declared private constructor because this should contains just static methods
    }

    public static Specification<WorkloadInventoryReportModel> getIsMemberSpecification(String attributeName, Set<String> values) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            values.forEach(val -> {
                Expression<Collection<String>> expression = root.get(attributeName);
                Predicate predicate = cb.isMember(val.trim(), expression);
                predicates.add(predicate);
            });

            return cb.or(predicates.stream().toArray(Predicate[]::new));
        };
    }

    public static Specification<WorkloadInventoryReportModel> getEqualSpecification(String attributeName, Set<String> values) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            values.forEach(val -> {
                Expression<String> expression = root.get(attributeName);
                Predicate predicate = cb.equal(cb.lower(expression), val.trim().toLowerCase());
                predicates.add(predicate);
            });

            return cb.or(predicates.stream().toArray(Predicate[]::new));
        };
    }

    public static Specification<WorkloadInventoryReportModel> getLikeSpecification(String attributeName, Set<String> values) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            values.forEach(val -> {
                Expression<String> expression = root.get(attributeName);
                Predicate predicate = cb.like(cb.lower(expression), "%" + val.trim().toLowerCase() + "%");
                predicates.add(predicate);
            });

            return cb.or(predicates.stream().toArray(Predicate[]::new));
        };
    }

    public static Specification<WorkloadInventoryReportModel> getByAnalysisIdAndFilterBean(Long analysisId, WorkloadInventoryFilterBean filterBean) {
        List<Specification<WorkloadInventoryReportModel>> specifications = new ArrayList<>();

        // analysisId
        Specification<WorkloadInventoryReportModel> analysisIdSpec = (root, query, cb) -> {
            Join<WorkloadInventoryReportModel, AnalysisModel> analysis = root.join("analysis", JoinType.INNER);
            return cb.equal(analysis.get("id"), analysisId);
        };

        // filterBean
        if (filterBean.getClusters() != null && !filterBean.getClusters().isEmpty()) {
            specifications.add(getEqualSpecification("cluster", filterBean.getClusters()));
        }

        if (filterBean.getProviders() != null && !filterBean.getProviders().isEmpty()) {
            specifications.add(getEqualSpecification("provider", filterBean.getProviders()));
        }

        if (filterBean.getDatacenters() != null && !filterBean.getDatacenters().isEmpty()) {
            specifications.add(getEqualSpecification("datacenter", filterBean.getDatacenters()));
        }

        if (filterBean.getComplexities() != null && !filterBean.getComplexities().isEmpty()) {
            specifications.add(getEqualSpecification("complexity", filterBean.getComplexities()));
        }

        if (filterBean.getVmNames() != null && !filterBean.getVmNames().isEmpty()) {
            specifications.add(getLikeSpecification("vmName", filterBean.getVmNames()));
        }

        if (filterBean.getOsNames() != null && !filterBean.getOsNames().isEmpty()) {
            specifications.add(getEqualSpecification("osName", filterBean.getOsNames()));
        }

        if (filterBean.getWorkloads() != null && !filterBean.getWorkloads().isEmpty()) {
            specifications.add(getIsMemberSpecification("workloads", filterBean.getWorkloads()));
        }

        if (filterBean.getRecommendedTargetsIMS() != null && !filterBean.getRecommendedTargetsIMS().isEmpty()) {
            specifications.add(getIsMemberSpecification("recommendedTargetsIMS", filterBean.getRecommendedTargetsIMS()));
        }

        if (filterBean.getFlagsIMS() != null && !filterBean.getFlagsIMS().isEmpty()) {
            specifications.add(getIsMemberSpecification("flagsIMS", filterBean.getRecommendedTargetsIMS()));
        }

        // union of specifications
        Specifications<WorkloadInventoryReportModel> where = Specifications.where(analysisIdSpec);
        for (Specification<WorkloadInventoryReportModel> specification : specifications) {
            where = where.and(specification);
        }

        return where;
    }
}
