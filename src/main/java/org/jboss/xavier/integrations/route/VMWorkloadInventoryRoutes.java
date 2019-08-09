package org.jboss.xavier.integrations.route;

import org.apache.camel.builder.RouteBuilder;
import org.jboss.xavier.analytics.pojo.output.workload.inventory.WorkloadInventoryReportModel;
import org.jboss.xavier.integrations.jpa.service.AnalysisService;
import org.jboss.xavier.integrations.jpa.service.WorkloadInventoryReportService;
import org.jboss.xavier.integrations.migrationanalytics.business.VMWorkloadInventoryCalculator;

import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class VMWorkloadInventoryRoutes extends RouteBuilder {

    @Inject
    AnalysisService analysisService;

    @Override
    public void configure() {
        from("direct:calculate-vmworkloadinventory")
                .id("calculate-vmworkloadinventory")
                .onCompletion().onCompleteOnly()
                .id("onCompletion-vmworkloadinventory")
                .to("direct:aggregate-vmworkloadinventory")
                .end()
                .doTry()
                    .transform().method(VMWorkloadInventoryCalculator.class, "calculate(${body}, ${header.MA_metadata})")
                    .split(body())
                    .to("jms:queue:vm-workload-inventory")
                .endDoTry()
                .doCatch(Exception.class)
                    .to("log:error?showCaughtException=true&showStackTrace=true")
                    .setBody(simple("Exception on parsing Cloudforms file"))
                .end();

        from ("jms:queue:vm-workload-inventory").id("extract-vmworkloadinventory")
            .to("log:INFO?showBody=true&showHeaders=true")
            .setHeader(MainRouteBuilder.ANALYSIS_ID, simple("${body." + MainRouteBuilder.ANALYSIS_ID + "}"))
            .transform().method("decisionServerHelper", "generateCommands(${body}, \"GetWorkloadInventoryReports\", \"WorkloadInventoryKSession0\")")
            .to("direct:decisionserver").id("workload-decisionserver")
            .transform().method("decisionServerHelper", "extractWorkloadInventoryReportModel")
            .process(e -> analysisService.addWorkloadInventoryReportModel(e.getIn().getBody(WorkloadInventoryReportModel.class), (Long) e.getIn().getHeader(MainRouteBuilder.ANALYSIS_ID)));
    }
}
