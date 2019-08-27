package org.jboss.xavier.integrations.route;

import org.apache.camel.builder.RouteBuilder;
import org.jboss.xavier.analytics.pojo.output.workload.inventory.WorkloadInventoryReportModel;
import org.jboss.xavier.integrations.jpa.service.AnalysisService;
import org.jboss.xavier.integrations.migrationanalytics.business.VMWorkloadInventoryCalculator;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class VMWorkloadInventoryRoutes extends RouteBuilder {

    @Inject
    AnalysisService analysisService;

    @Override
    public void configure() {
        from("direct:calculate-vmworkloadinventory").id("calculate-vmworkloadinventory")
                .onCompletion().onCompleteOnly().id("onCompletion-vmworkloadinventory")
                    .to("direct:aggregate-vmworkloadinventory")
                .end()
                .transform().method(VMWorkloadInventoryCalculator.class, "calculate(${body}, ${header.MA_metadata})")
                .split(body())
                .to("jms:queue:vm-workload-inventory")
                .end();

        from ("jms:queue:vm-workload-inventory").id("extract-vmworkloadinventory")
            .to("log:INFO?showBody=true&showHeaders=true")
            .doTry()
                .setHeader(MainRouteBuilder.ANALYSIS_ID, simple("${body." + MainRouteBuilder.ANALYSIS_ID + "}", String.class))
                .transform().method("decisionServerHelper", "generateCommands(${body}, \"GetWorkloadInventoryReports\", \"WorkloadInventoryKSession0\")")
                .to("direct:decisionserver").id("workload-decisionserver")
                .transform().method("decisionServerHelper", "extractWorkloadInventoryReportModel")
                .process(e -> analysisService.addWorkloadInventoryReportModel(e.getIn().getBody(WorkloadInventoryReportModel.class), Long.parseLong(e.getIn().getHeader(MainRouteBuilder.ANALYSIS_ID, String.class))))
            .endDoTry()
            .doCatch(Exception.class)
                .to("log:error?showCaughtException=true&showStackTrace=true")
                .transform().method("analysisService", "updateStatus(\"FAILED\", ${header.analysisId})")
                .stop()
            .end();

    }
}
