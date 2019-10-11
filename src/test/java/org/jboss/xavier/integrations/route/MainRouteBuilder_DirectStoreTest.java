package org.jboss.xavier.integrations.route;

import org.apache.camel.EndpointInject;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

@MockEndpointsAndSkip("direct:insights|file:*|direct:analysis-model")
public class MainRouteBuilder_DirectStoreTest extends XavierCamelTest {
    @EndpointInject(uri = "mock:direct:insights")
    private MockEndpoint mockInsights;

    @Test
    public void mainRouteBuilder_routeDirectStore_ContentGiven_ShouldStoreinLocalFile() throws Exception {
        //Given

        String body = "this is a test body";
        mockInsights.expectedBodiesReceived(body);

        //When
        camelContext.start();
        camelContext.startRoute("direct-store");
        camelContext.createProducerTemplate().sendBodyAndHeader("direct:store", body, "CamelFileName", "testfilename.txt");

        //Then
        mockInsights.assertIsSatisfied();

        camelContext.stop();
    }

    @Test
    public void mainRouteBuilder_routeDirectStore_BinaryContentGiven_ShouldStoreinLocalFile() throws Exception {
        //Given

        byte[] body = IOUtils.resourceToByteArray("cloudforms-export-v1.tar.gz", this.getClass().getClassLoader());
        mockInsights.expectedBodiesReceived(body);

        //When
        camelContext.start();
        camelContext.startRoute("direct-store");
        camelContext.createProducerTemplate().sendBodyAndHeader("direct:store", body, "CamelFileName", "cloudforms-export-v1.tar.gz");

        //Then
        mockInsights.assertIsSatisfied();

        camelContext.stop();
    }



}
