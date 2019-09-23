package org.jboss.xavier.integrations.route;

import org.apache.camel.builder.RouteBuilder;
import org.jboss.xavier.analytics.pojo.output.workload.inventory.WorkloadInventoryReportModel;
import org.jboss.xavier.integrations.jpa.service.AnalysisService;
import org.jboss.xavier.integrations.jpa.service.WorkloadInventoryReportService;
import org.jboss.xavier.integrations.migrationanalytics.business.FlagSharedDisksCalculator;
import org.jboss.xavier.integrations.migrationanalytics.business.VMWorkloadInventoryCalculator;
import org.jboss.xavier.integrations.route.strategy.WorkloadInventoryReportModelAggregationStrategy;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Named
public class VMWorkloadInventoryRoutes extends RouteBuilder {

    @Inject
    AnalysisService analysisService;

    @Inject
    WorkloadInventoryReportService workloadInventoryReportService;

    @Value("${parallel.wir}")
    private boolean parallel;

    @Override
    public void configure() {
        from("direct:calculate-vmworkloadinventory").id("calculate-vmworkloadinventory")
            .transform().method(VMWorkloadInventoryCalculator.class, "calculate(${body}, ${header.${type:org.jboss.xavier.integrations.route.MainRouteBuilder.MA_METADATA}})")
            .split(body()).parallelProcessing(parallel).aggregationStrategy(new WorkloadInventoryReportModelAggregationStrategy())
            .to("direct:vm-workload-inventory")
            .end()
            .process(exchange -> {
                analysisService.addWorkloadInventoryReportModels(exchange.getIn().getBody(List.class),
                        Long.parseLong(exchange.getIn().getHeader(MainRouteBuilder.MA_METADATA, Map.class).get(MainRouteBuilder.ANALYSIS_ID).toString()));
            });

        from ("direct:vm-workload-inventory").id("extract-vmworkloadinventory")
            .doTry()
                .setHeader(MainRouteBuilder.ANALYSIS_ID, simple("${body." + MainRouteBuilder.ANALYSIS_ID + "}", String.class))
                .transform().method("decisionServerHelper", "generateCommands(${body}, \"GetWorkloadInventoryReports\", \"WorkloadInventoryKSession0\")")
                .to("direct:decisionserver").id("workload-decisionserver")
                .transform().method("decisionServerHelper", "extractWorkloadInventoryReportModel")
            .endDoTry()
            .doCatch(Exception.class)
                .to("log:error?showCaughtException=true&showStackTrace=true")
                .transform().method("analysisService", "updateStatus(\"FAILED\", ${header.${type:org.jboss.xavier.integrations.route.MainRouteBuilder.ANALYSIS_ID}})")
                .stop()
            .end();

        from("direct:flags-shared-disks").id("flags-shared-disks")
            .doTry()
                .transform().method(FlagSharedDisksCalculator.class, "calculate(${body}, ${header.${type:org.jboss.xavier.integrations.route.MainRouteBuilder.MA_METADATA}})")
                .process(exchange -> {
                    Set<String> vmNamesWithSharedDisk = exchange.getIn().getBody(Set.class);
                    List<WorkloadInventoryReportModel> workloadInventoryReportModels = workloadInventoryReportService.findByAnalysisOwnerAndAnalysisId(
                            exchange.getIn().getHeader(MainRouteBuilder.USERNAME, String.class),
                            Long.parseLong(exchange.getIn().getHeader(MainRouteBuilder.MA_METADATA, Map.class).get(MainRouteBuilder.ANALYSIS_ID).toString()));
                    List<WorkloadInventoryReportModel> workloadInventoryReportModelsToUpdate = workloadInventoryReportModels.stream()
                        .filter(workloadInventoryReportModel -> vmNamesWithSharedDisk.contains(workloadInventoryReportModel.getVmName()))
                        .map(workloadInventoryReportModel -> {
                            workloadInventoryReportModel.addFlagIMS("Shared Disk");
                            return workloadInventoryReportModel;
                        }).collect(Collectors.toList());
                    workloadInventoryReportService.saveAll(workloadInventoryReportModelsToUpdate);
                })
            .endDoTry()
            .doCatch(Exception.class)
                .to("log:error?showCaughtException=true&showStackTrace=true")
                .transform().method("analysisService", "updateStatus(\"FAILED\", ${header.${type:org.jboss.xavier.integrations.route.MainRouteBuilder.MA_METADATA}[" + MainRouteBuilder.ANALYSIS_ID + "]}")
                .stop()
            .end();
    }
}
