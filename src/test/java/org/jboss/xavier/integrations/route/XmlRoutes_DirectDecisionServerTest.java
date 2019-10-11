package org.jboss.xavier.integrations.route;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.jboss.xavier.analytics.pojo.input.UploadFormInputDataModel;
import org.jboss.xavier.integrations.DecisionServerHelper;
import org.junit.Test;
import org.kie.api.command.BatchExecutionCommand;
import org.springframework.beans.factory.annotation.Autowired;

@MockEndpointsAndSkip("http:*")
public class XmlRoutes_DirectDecisionServerTest extends XavierCamelTest {

    @EndpointInject(uri="mock:http:{{kieserver.devel-service}}/{{kieserver.path}}")
    MockEndpoint kieServer;

    @Autowired
    DecisionServerHelper decisionServerHelper;

    @Test
    public void mainRouteBuilder_DirectDecisionServer_ContentWithSeveralFilesGiven_ShouldReturnSameNumberOfMessages() throws Exception {
        //Given
        camelContext.getRouteDefinition("decision-server-rest").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                weaveById("route-to-decision-server-rest")
                        .after()
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("200"));
            }
        });

        String expectedBody = "<?xml version='1.0' encoding='UTF-8'?><batch-execution lookup=\"kiesession0\"><insert><org.jboss.xavier.analytics.pojo.input.UploadFormInputDataModel><customerId>CID123</customerId><fileName>cloudforms-export-v1.json</fileName><hypervisor>1</hypervisor><totalDiskSpace>1000</totalDiskSpace><sourceProductIndicator>1</sourceProductIndicator><year1HypervisorPercentage>10.0</year1HypervisorPercentage><year2HypervisorPercentage>20.0</year2HypervisorPercentage><year3HypervisorPercentage>30.0</year3HypervisorPercentage><growthRatePercentage>7.0</growthRatePercentage><dealIndicator>1</dealIndicator><openStackIndicator>1</openStackIndicator></org.jboss.xavier.analytics.pojo.input.UploadFormInputDataModel></insert><fire-all-rules/><query out-identifier=\"output\" name=\"get InitialSavingsEstimationReports\"/></batch-execution>";
        kieServer.expectedBodiesReceived(expectedBody);

        //When
        camelContext.start();
        camelContext.startRoute("decision-server-rest");
        String customerId = "CID123";
        String fileName = "cloudforms-export-v1.json";
        Integer hypervisor = 1;
        Long totaldiskspace = 1000L;
        Integer sourceproductindicator = 1;
        Double year1hypervisorpercentage = 10D;
        Double year2hypervisorpercentage = 20D;
        Double year3hypervisorpercentage = 30D;
        Double growthratepercentage = 7D;
        Long analysiId = 30L;
        UploadFormInputDataModel inputDataModel = new UploadFormInputDataModel(customerId, fileName, hypervisor, totaldiskspace, sourceproductindicator, year1hypervisorpercentage, year2hypervisorpercentage, year3hypervisorpercentage, growthratepercentage, analysiId);


        BatchExecutionCommand sentBody = decisionServerHelper.createMigrationAnalyticsCommand(inputDataModel);

        camelContext.createProducerTemplate().sendBody("direct:decisionserver", sentBody );

        //Then
        kieServer.assertIsSatisfied();
        camelContext.stop();
    }

}
