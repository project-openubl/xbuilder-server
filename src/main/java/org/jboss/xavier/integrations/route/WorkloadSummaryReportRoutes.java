package org.jboss.xavier.integrations.route;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.builder.RouteBuilder;
import org.jboss.xavier.analytics.pojo.output.workload.inventory.WorkloadInventoryReportModel;
import org.jboss.xavier.analytics.pojo.output.workload.summary.ComplexityModel;
import org.jboss.xavier.analytics.pojo.output.workload.summary.SummaryModel;
import org.jboss.xavier.analytics.pojo.output.workload.summary.WorkloadSummaryReportModel;
import org.jboss.xavier.integrations.jpa.service.AnalysisService;
import org.jboss.xavier.integrations.jpa.service.ComplexityService;
import org.jboss.xavier.integrations.jpa.service.SummaryService;
import org.jboss.xavier.integrations.jpa.service.WorkloadInventoryReportService;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Named
public class WorkloadSummaryReportRoutes extends RouteBuilder {

    private final Logger logger = Logger.getLogger(WorkloadSummaryReportRoutes.class.getName());

    @Inject
    WorkloadInventoryReportService workloadInventoryReportService;

    @Inject
    ComplexityService complexityService;

    @Inject
    SummaryService summaryService;

    @Inject
    AnalysisService analysisService;

    @Value("${report.workload.summary.polling.delay}")
    private long delay;

    @Value("${report.workload.summary.polling.max-attempts}")
    private long maxAttempts;

    @Override
    public void configure() {

        from("direct:aggregate-vmworkloadinventory")
            .id("aggregate-vmworkloadinventory")
            .process(exchange -> {
                Integer expectedSize = ((Collection) exchange.getIn().getBody()).size();
                String analysisId = ((Map<String, String>) exchange.getIn().getHeader("MA_metadata")).get(MainRouteBuilder.ANALYSIS_ID);
                List<WorkloadInventoryReportModel> workloadInventoryReportModels  = workloadInventoryReportService.findByAnalysisId(Long.parseLong(analysisId));
                int attempts = 0;
                for (; workloadInventoryReportModels.size() < expectedSize && attempts < maxAttempts; attempts++)
                {
                    logger.warning("workloadInventoryReportModels.size() < expectedSize since " + workloadInventoryReportModels.size()  + " < " + expectedSize);
                    Thread.sleep(delay);
                    workloadInventoryReportModels  = workloadInventoryReportService.findByAnalysisId(Long.parseLong(analysisId));
                }
                if (maxAttempts == attempts) throw new CamelExecutionException("Unable to find the expected " + expectedSize + " WorkloadInventoryReportModels in the DB", exchange);
            })
            .to("direct:calculate-workloadsummaryreportmodel");

        from("direct:calculate-workloadsummaryreportmodel")
            .id("calculate-workloadsummaryreportmodel")
            .process(exchange -> {
                Long analysisId = Long.parseLong(((Map<String, String>) exchange.getIn().getHeader("MA_metadata")).get(MainRouteBuilder.ANALYSIS_ID));
                WorkloadSummaryReportModel workloadSummaryReportModel = new WorkloadSummaryReportModel();

                //retrieve each model one after the other
                List<SummaryModel> summaryModels = summaryService.calculateSummaryModels(analysisId);
                // Set the components into the WorkloadSummaryReportModel bean
                workloadSummaryReportModel.setSummaryModels(summaryModels);

                // TODO Calculate the other parts of the Workload Summary Report
                // and set them into the workloadSummaryReportModel bean
                ComplexityModel complexityModel = complexityService.calculateComplexityModels(analysisId);
                workloadSummaryReportModel.setComplexityModel(complexityModel);

                // Set the WorkloadSummaryReportModel into the AnalysisModel
                analysisService.setWorkloadSummaryReportModel(workloadSummaryReportModel, analysisId);
            });
    }
}
