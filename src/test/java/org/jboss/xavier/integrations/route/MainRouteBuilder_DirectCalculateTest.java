package org.jboss.xavier.integrations.route;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.apache.camel.test.spring.UseAdviceWith;
import org.apache.commons.io.IOUtils;
import org.jboss.xavier.Application;
import org.jboss.xavier.analytics.pojo.input.UploadFormInputDataModel;
import org.jboss.xavier.integrations.migrationanalytics.business.Calculator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.inject.Inject;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@MockEndpointsAndSkip("jms:queue:uploadFormInputDataModel")
@UseAdviceWith // Disables automatic start of Camel context
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
public class MainRouteBuilder_DirectCalculateTest {
    @Inject
    CamelContext camelContext;

    @Inject
    MainRouteBuilder mainRouteBuilder;

    @EndpointInject(uri = "mock:jms:queue:uploadFormInputDataModel")
    private MockEndpoint mockJmsQueue;

    @Test
    public void mainRouteBuilder_DirectCalculate_PersistedNotificationGiven_ShouldCallFileWithGivenHeaders() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        String customerId = "CID123";
        String fileName = "cloudforms-export-v1.json";
        Integer hypervisor = 2;
        Long totaldiskspace = 563902124032L;
        Integer sourceproductindicator = null;
        Double year1hypervisorpercentage = 10D;
        Double year2hypervisorpercentage = 20D;
        Double year3hypervisorpercentage = 30D;
        Double growthratepercentage = 7D;

        UploadFormInputDataModel expectedFormInputDataModelExpected = new UploadFormInputDataModel(customerId, fileName, hypervisor, totaldiskspace, 
                sourceproductindicator, year1hypervisorpercentage/100, year2hypervisorpercentage/100, 
                year3hypervisorpercentage/100, growthratepercentage/100);

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("filename", fileName);
        metadata.put("org_id", customerId);
        metadata.put(Calculator.YEAR_1_HYPERVISORPERCENTAGE, year1hypervisorpercentage);
        metadata.put(Calculator.YEAR_2_HYPERVISORPERCENTAGE, year2hypervisorpercentage);
        metadata.put(Calculator.YEAR_3_HYPERVISORPERCENTAGE, year3hypervisorpercentage);
        metadata.put(Calculator.GROWTHRATEPERCENTAGE, growthratepercentage);

        Map<String, Object> headers = new HashMap<>();
        headers.put("MA_metadata", metadata);

        //When
        camelContext.start();
        camelContext.startRoute("calculate");
        InputStream body = getClass().getClassLoader().getResourceAsStream(fileName);
        
        camelContext.createProducerTemplate().sendBodyAndHeaders("direct:calculate", body, headers);

        //Then
        assertThat(mockJmsQueue.getExchanges().get(0).getIn().getBody()).isEqualToComparingFieldByFieldRecursively(expectedFormInputDataModelExpected);

        camelContext.stop();
    }

    @Test
    public void mainRouteBuilder_DirectCalculate_WrongJSONFileGiven_ShouldLogExceptionButNotCrash() throws Exception {
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
    
    @Test
    public void mainRouteBuilder_DirectCalculate_FileGiven_ShouldSendMessageToJMS() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);
        mockJmsQueue.expectedMessageCount(1);

        String fileName = "cloudforms-export-v1.json";

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("filename", fileName);
        metadata.put("dummy", "dummy");
        metadata.put(Calculator.YEAR_1_HYPERVISORPERCENTAGE, 10D);
        metadata.put(Calculator.YEAR_2_HYPERVISORPERCENTAGE, 20D);
        metadata.put(Calculator.YEAR_3_HYPERVISORPERCENTAGE, 30D);
        metadata.put(Calculator.GROWTHRATEPERCENTAGE, 7D);
        
        Map<String, Object> headers = new HashMap<>();
        headers.put("MA_metadata", metadata);
        headers.put("Content-type", "application/zip");
        
        //When
        camelContext.start();
        camelContext.startRoute("unzip-file");
        camelContext.startRoute("calculate");
        String body = IOUtils.toString(getClass().getClassLoader().getResourceAsStream(fileName), Charset.forName("UTF-8"));

        Exchange message = camelContext.createProducerTemplate().request("direct:unzip-file", exchange -> {
            exchange.getIn().setBody(body);
            exchange.getIn().setHeaders(headers);
        });

        //Then
        mockJmsQueue.assertIsSatisfied();
        assertThat(message.getIn().getBody(UploadFormInputDataModel.class).getTotalDiskSpace()).isEqualTo(563902124032L);
        assertThat(message.getIn().getBody(UploadFormInputDataModel.class).getHypervisor()).isEqualTo(2);
        camelContext.stop();
    }

}