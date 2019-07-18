package org.jboss.xavier.integrations.route;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.apache.camel.test.spring.UseAdviceWith;
import org.jboss.xavier.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@MockEndpointsAndSkip("direct:insights|file:*")
@UseAdviceWith // Disables automatic start of Camel context
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
public class MainRouteBuilder_DirectStoreTest {
    @Autowired
    CamelContext camelContext;

    @EndpointInject(uri = "mock:direct:insights")
    private MockEndpoint mockInsights;

    @Test
    public void mainRouteBuilder_routeDirectStore_ContentGiven_ShouldStoreinLocalFile() throws Exception {
        //Given

        String body = "this is a test body";
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);
        mockInsights.expectedBodiesReceived(body);

        //When
        camelContext.start();
        camelContext.startRoute("direct-store");
        camelContext.createProducerTemplate().sendBodyAndHeader("direct:store", body, "CamelFileName", "testfilename.txt");

        //Then
        mockInsights.assertIsSatisfied();

        camelContext.stop();
    }



}
