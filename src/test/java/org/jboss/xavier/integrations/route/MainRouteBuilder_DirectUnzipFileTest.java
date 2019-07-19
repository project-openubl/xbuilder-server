package org.jboss.xavier.integrations.route;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.apache.camel.test.spring.UseAdviceWith;
import org.jboss.xavier.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.inject.Inject;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@MockEndpointsAndSkip("direct:store|direct:calculate")
@UseAdviceWith // Disables automatic start of Camel context
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
public class MainRouteBuilder_DirectUnzipFileTest {
    @Autowired
    CamelContext camelContext;

    @EndpointInject(uri = "mock:direct:store")
    private MockEndpoint mockStore;

    @EndpointInject(uri = "mock:direct:calculate")
    private MockEndpoint mockCalculate;

    @Value("#{'${insights.properties}'.split(',')}")
    List<String> properties;

    @Inject
    MainRouteBuilder mainRouteBuilder;

    @Test
    public void mainRouteBuilder_routeDirectUnzip_ZipFileWith3FilesGiven_ShouldReturn3Messages() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);
        mockCalculate.expectedMessageCount(3);

        //When
        camelContext.start();
        camelContext.startRoute("unzip-file");

        String nameOfFile = "txt-files-samples.zip";
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(nameOfFile);

        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/zip");
        headers.put(Exchange.FILE_NAME, nameOfFile);

        Map<String,Object> metadata = new HashMap<>();
        metadata.put("filename", nameOfFile);
        metadata.put("dummy", "CID123");
        headers.put("MA_metadata", metadata);


        camelContext.createProducerTemplate().sendBodyAndHeaders("direct:unzip-file", resourceAsStream, headers); 

        //Then
        mockCalculate.assertIsSatisfied();

        camelContext.stop();
    }

    @Test
    public void mainRouteBuilder_routeDirectUnzip_TarGzFileWith2FilesGiven_ShouldReturn2Messages() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);
        mockCalculate.expectedMessageCount(2);

        //When
        camelContext.start();
        camelContext.startRoute("unzip-file");

        String nameOfFile = "cloudforms-export-v1-multiple-files.tar.gz";
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(nameOfFile);

        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/gzip");
        headers.put(Exchange.FILE_NAME, nameOfFile);

        Map<String,Object> metadata = new HashMap<>();
        metadata.put("filename", nameOfFile);
        metadata.put("dummy", "CID123");
        headers.put("MA_metadata", metadata);        
        
        camelContext.createProducerTemplate().sendBodyAndHeaders("direct:unzip-file", resourceAsStream, headers); 

        //Then
        mockCalculate.assertIsSatisfied();

        camelContext.stop();
    }
    
    @Test
    public void mainRouteBuilder_routeDirectUnzip_TarGzFileWith2FilesGivenAndTarGzContentType_ShouldReturn2Messages() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);
        mockCalculate.expectedMessageCount(2);

        //When
        camelContext.start();
        camelContext.startRoute("unzip-file");

        String nameOfFile = "cloudforms-export-v1-multiple-files.tar.gz";
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(nameOfFile);

        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/tar+gz");
        headers.put(Exchange.FILE_NAME, nameOfFile);

        Map<String,Object> metadata = new HashMap<>();
        metadata.put("filename", nameOfFile);
        metadata.put("dummy", "CID123");
        headers.put("MA_metadata", metadata);        
        
        camelContext.createProducerTemplate().sendBodyAndHeaders("direct:unzip-file", resourceAsStream, headers); 

        //Then
        mockCalculate.assertIsSatisfied();

        camelContext.stop();
    }

    @Test
    public void mainRouteBuilder_routeDirectUnzip_JsonFileGiven_ShouldReturn1Messages() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);
        mockCalculate.expectedMessageCount(1);

        //When
        camelContext.start();
        camelContext.startRoute("unzip-file");

        String nameOfFile = "cloudforms-export-v1-json";
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(nameOfFile);

        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "text/plain");
        headers.put(Exchange.FILE_NAME, nameOfFile);

        Map<String,Object> metadata = new HashMap<>();
        metadata.put("filename", nameOfFile);
        metadata.put("dummy", "CID123");
        headers.put("MA_metadata", metadata);

        camelContext.createProducerTemplate().sendBodyAndHeaders("direct:unzip-file", resourceAsStream, headers); 

        //Then
        mockCalculate.assertIsSatisfied();

        camelContext.stop();
    }




}