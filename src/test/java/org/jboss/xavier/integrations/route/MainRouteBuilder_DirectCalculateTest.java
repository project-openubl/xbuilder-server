package org.jboss.xavier.integrations.route;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.apache.camel.test.spring.UseAdviceWith;
import org.apache.commons.io.IOUtils;
import org.jboss.xavier.analytics.pojo.input.UploadFormInputDataModel;
import org.jboss.xavier.integrations.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.inject.Inject;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@MockEndpointsAndSkip("jms:queue:inputDataModel")
@UseAdviceWith // Disables automatic start of Camel context
@SpringBootTest(classes = {Application.class}) 
@ActiveProfiles("test")
public class MainRouteBuilder_DirectCalculateTest {
    @Inject
    CamelContext camelContext;
    
    @Inject
    MainRouteBuilder mainRouteBuilder;

    @EndpointInject(uri = "mock:jms:queue:inputDataModel")
    private MockEndpoint mockJmsQueue; 
    
    @Test
    public void mainRouteBuilder_DirectDownloadFile_PersistedNotificationGiven_ShouldCallFileWithGivenHeaders() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        String customerId = "CID123";
        String fileName = "cloudforms-export-v1.json";
        Integer hypervisor = 1;
        Long totaldiskspace = 281951062016L;
        Integer sourceproductindicator = 1;
        Double year1hypervisorpercentage = 10D;
        Double year2hypervisorpercentage = 20D;
        Double year3hypervisorpercentage = 30D;
        Double growthratepercentage = 7D;

        UploadFormInputDataModel expectedFormInputDataModelExpected = new UploadFormInputDataModel(customerId, fileName, hypervisor, totaldiskspace, sourceproductindicator, year1hypervisorpercentage, year2hypervisorpercentage, year3hypervisorpercentage, growthratepercentage);

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("customerid", customerId);
        metadata.put("filename", fileName);
        metadata.put("year1hypervisorpercentage", year1hypervisorpercentage);
        metadata.put("year2hypervisorpercentage", year2hypervisorpercentage);
        metadata.put("year3hypervisorpercentage", year3hypervisorpercentage);
        metadata.put("growthratepercentage", growthratepercentage);
        metadata.put("sourceproductindicator", sourceproductindicator);
        
        Map<String, Object> headers = new HashMap<>();
        headers.put("MA_metadata", metadata);

        //When
        camelContext.start();
        camelContext.startRoute("calculate");
        String body = IOUtils.toString(getClass().getClassLoader().getResourceAsStream(fileName), Charset.forName("UTF-8"));
        
        camelContext.createProducerTemplate().sendBodyAndHeaders("direct:calculate", body, headers);

        //Then
        assertThat(mockJmsQueue.getExchanges().get(0).getIn().getBody()).isEqualToComparingFieldByFieldRecursively(expectedFormInputDataModelExpected);

        camelContext.stop();
    }    
    
    @Test
    public void mainRouteBuilder_DirectDownloadFile_WrongJSONFileGiven_ShouldLogExceptionButNotCrash() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);
        mockJmsQueue.expectedMessageCount(0);

        String customerId = "CID123";
        String fileName = "cloudforms-export-v1.json";
        Map<String, Object> headers = new HashMap<>();
        headers.put("customerid", customerId);
        headers.put("filename", fileName);
        
        //When
        camelContext.start();
        camelContext.startRoute("calculate");
        String body = IOUtils.toString(getClass().getClassLoader().getResourceAsStream(fileName), Charset.forName("UTF-8"));

        Exchange message = camelContext.createProducerTemplate().request("direct:calculate", exchange -> {
            exchange.getIn().setBody("{ \"ñkajsñlkj\" : " + body);
            exchange.getIn().setHeaders(headers);
        });

        //Then
        mockJmsQueue.assertIsSatisfied();
        assertThat(message.getIn().getBody(String.class)).isEqualToIgnoringCase("Exception on parsing Cloudforms file");
        camelContext.stop();
    }
    
}