package org.jboss.xavier.integrations.route;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.apache.camel.test.spring.UseAdviceWith;
import org.jboss.xavier.analytics.pojo.input.UploadFormInputDataModel;
import org.jboss.xavier.integrations.Application;
import org.jboss.xavier.integrations.DecisionServerHelper;
import org.jboss.xavier.integrations.migrationanalytics.output.ReportDataModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.ExecutionResults;
import org.kie.server.api.model.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@MockEndpointsAndSkip("jpa:org.jboss.xavier.integrations.migrationanalytics.output.ReportDataModel|direct:decisionserver")  
@UseAdviceWith // Disables automatic start of Camel context
@SpringBootTest(classes = {Application.class}) 
@ActiveProfiles("test")
public class XmlRoutes_CallKieExtractReportsTest {
    @Autowired
    CamelContext camelContext;

    @EndpointInject(uri = "mock:jpa:org.jboss.xavier.integrations.migrationanalytics.output.ReportDataModel")
    private MockEndpoint mockJPA;
    
    @SpyBean
    DecisionServerHelper decisionServerHelper;
    
    @Before
    public void setup() throws Exception {
        camelContext.getRouteDefinition("call-kie-extract-reports").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
                replaceFromWith("direct:inputDataModel");
                weaveById("decisionserver").after().process(exchange -> exchange.getIn().setBody(new ServiceResponse<ExecutionResults>()));
            }
        });     
        doReturn(getReportModelSample()).when(decisionServerHelper).extractReports(any());
    }

    @Test
    public void xmlroutes_directInputDataModel_InputDataModelGiven_ShouldReportDecisionServerHelperValues() throws Exception
    {
      
        mockJPA.expectedBodiesReceived(getReportModelSample());

        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);
        camelContext.start();
        camelContext.startRoute("call-kie-extract-reports");

        camelContext.createProducerTemplate().sendBody("direct:inputDataModel", getInputDataModelSample());

        mockJPA.assertIsSatisfied();
        
        camelContext.stop();
    }

    private ReportDataModel getReportModelSample() {
        return ReportDataModel.builder().numberOfHosts(10).totalDiskSpace(100L).totalPrice(1000).build();
    }    

    private UploadFormInputDataModel getInputDataModelSample() {
        String customerId = "CID123";
        String fileName = "cloudforms-export-v1.json";
        Integer hypervisor = 1;
        Long totaldiskspace = 1000L;
        Integer sourceproductindicator = 1;
        Double year1hypervisorpercentage = 10D;
        Double year2hypervisorpercentage = 20D;
        Double year3hypervisorpercentage = 30D;
        Double growthratepercentage = 7D;
        return new UploadFormInputDataModel(customerId, fileName, hypervisor, totaldiskspace, sourceproductindicator, year1hypervisorpercentage, year2hypervisorpercentage, year3hypervisorpercentage, growthratepercentage);
    }
    
}
