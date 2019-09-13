package org.jboss.xavier.integrations.route;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.builder.RouteBuilder;
import org.jboss.xavier.analytics.pojo.output.AnalysisModel;
import org.jboss.xavier.analytics.pojo.output.workload.inventory.WorkloadInventoryReportModel;
import org.jboss.xavier.analytics.pojo.output.workload.summary.ComplexityModel;
import org.jboss.xavier.analytics.pojo.output.workload.summary.RecommendedTargetsIMSModel;
import org.jboss.xavier.analytics.pojo.output.workload.summary.FlagModel;
import org.jboss.xavier.analytics.pojo.output.workload.summary.SummaryModel;
import org.jboss.xavier.analytics.pojo.output.workload.summary.WorkloadModel;
import org.jboss.xavier.analytics.pojo.output.workload.summary.WorkloadSummaryReportModel;
import org.jboss.xavier.integrations.jpa.service.AnalysisService;
import org.jboss.xavier.integrations.jpa.service.ComplexityService;
import org.jboss.xavier.integrations.jpa.service.RecommendedTargetsIMSService;
import org.jboss.xavier.integrations.jpa.service.FlagService;
import org.jboss.xavier.integrations.jpa.service.SummaryService;
import org.jboss.xavier.integrations.jpa.service.WorkloadInventoryReportService;
import org.jboss.xavier.integrations.jpa.service.WorkloadService;
import org.jboss.xavier.analytics.pojo.output.workload.summary.*;
import org.jboss.xavier.integrations.jpa.service.*;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.logging.Logger;

@Named
public class WorkloadSummaryReportRoutes extends RouteBuilder {

    private final Logger logger = Logger.getLogger(WorkloadSummaryReportRoutes.class.getName());

    @Inject
    WorkloadInventoryReportService workloadInventoryReportService;

    @Inject
    WorkloadsDetectedOSTypeService workloadsDetectedOSTypeService;

    @Inject
    ComplexityService complexityService;

    @Inject
    RecommendedTargetsIMSService recommendedTargetsIMSService;

    @Inject
    WorkloadService workloadService;

    @Inject
    FlagService flagService;

    @Inject
    SummaryService summaryService;

    @Inject
    AnalysisService analysisService;

    @Override
    public void configure() {

        from("direct:calculate-workloadsummaryreportmodel")
            .id("calculate-workloadsummaryreportmodel")
            .process(exchange -> {
                Long analysisId = Long.parseLong(((Map<String, String>) exchange.getIn().getHeader("MA_metadata")).get(MainRouteBuilder.ANALYSIS_ID));
                WorkloadSummaryReportModel workloadSummaryReportModel = new WorkloadSummaryReportModel();

                //retrieve each model one after the other
                List<SummaryModel> summaryModels = summaryService.calculateSummaryModels(analysisId);
                // Set the components into the WorkloadSummaryReportModel bean
                workloadSummaryReportModel.setSummaryModels(new LinkedHashSet<>(summaryModels)); // LinkedHashSet to preserve the order

                // Calculate the other parts of the Workload Summary Report
                // and set them into the workloadSummaryReportModel bean
                ComplexityModel complexityModel = complexityService.calculateComplexityModels(analysisId);
                workloadSummaryReportModel.setComplexityModel(complexityModel);

                RecommendedTargetsIMSModel recommendedTargetsIMSModel = recommendedTargetsIMSService.calculateRecommendedTargetsIMS(analysisId);
                workloadSummaryReportModel.setRecommendedTargetsIMSModel(recommendedTargetsIMSModel);

                List<WorkloadModel> workloadModels = workloadService.calculateWorkloadsModels(analysisId);
                workloadSummaryReportModel.setWorkloadModels(workloadModels);

                List<FlagModel> flagModels = flagService.calculateFlagModels(analysisId);
                workloadSummaryReportModel.setFlagModels(flagModels);

                // Set the WorkloadSummaryReportModel into the AnalysisModel
                analysisService.setWorkloadSummaryReportModel(workloadSummaryReportModel, analysisId);

                // Refresh the workloadSummaryReportModel
                AnalysisModel analysisModel = analysisService.findByOwnerAndId(exchange.getIn().getHeader(MainRouteBuilder.USERNAME, String.class), analysisId);
                workloadSummaryReportModel = analysisModel.getWorkloadSummaryReportModels();

                // Calculate parts of the Workload Summary Report which depends of previous data
                List<WorkloadsDetectedOSTypeModel> workloadsDetectedOSTypeModels = workloadsDetectedOSTypeService.calculateWorkloadsDetectedOSTypeModels(analysisId);
                workloadSummaryReportModel.setWorkloadsDetectedOSTypeModels(new LinkedHashSet<>(workloadsDetectedOSTypeModels)); // // LinkedHashSet to preserve the order

                // Set the WorkloadSummaryReportModel into the AnalysisModel and update status to CREATED
                analysisService.setWorkloadSummaryReportModelAndUpdateStatus(workloadSummaryReportModel, analysisId);
            });
    }
}
