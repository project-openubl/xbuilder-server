package org.jboss.xavier.integrations.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.jboss.xavier.analytics.pojo.output.workload.inventory.WorkloadInventoryReportModel;
import org.jboss.xavier.integrations.jpa.service.AnalysisService;
import org.jboss.xavier.integrations.migrationanalytics.business.VMWorkloadInventoryCalculator;
import org.jboss.xavier.integrations.route.strategy.WorkloadInventoryReportModelAggregationStrategy;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Named
public class VMWorkloadInventoryRoutes extends RouteBuilder {

    @Inject
    AnalysisService analysisService;

    @Value("${parallel.wir}")
    private boolean parallel;

    @Override
    public void configure() {
        from("direct:calculate-vmworkloadinventory").id("calculate-vmworkloadinventory")
                .onCompletion().onCompleteOnly().id("onCompletion-vmworkloadinventory")
                    .to("direct:aggregate-vmworkloadinventory")
                .end()
                .transform().method(VMWorkloadInventoryCalculator.class, "calculate(${body}, ${header.MA_metadata})")
                .split(body()).parallelProcessing(parallel).aggregationStrategy(new WorkloadInventoryReportModelAggregationStrategy())
                .to("direct:vm-workload-inventory")
                .end()
                .process(exchange -> {
                    analysisService.addWorkloadInventoryReportModels(exchange.getIn().getBody(List.class),
                            Long.parseLong(exchange.getIn().getHeader("MA_metadata", Map.class).get(MainRouteBuilder.ANALYSIS_ID).toString()));
                });

        from ("direct:vm-workload-inventory").id("extract-vmworkloadinventory")
            .doTry()
                .transform().method("decisionServerHelper", "generateCommands(${body}, \"GetWorkloadInventoryReports\", \"WorkloadInventoryKSession0\")")
                .to("direct:decisionserver").id("workload-decisionserver")
                .transform().method("decisionServerHelper", "extractWorkloadInventoryReportModel")
            .endDoTry()
            .doCatch(Exception.class)
                .to("log:error?showCaughtException=true&showStackTrace=true")
                .transform().method("analysisService", "updateStatus(\"FAILED\", ${header.analysisId})")
                .stop()
            .end();

    }
}
