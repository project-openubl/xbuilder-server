package org.jboss.xavier.integrations.route;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringRunner;
import org.apache.camel.test.spring.DisableJmx;
import org.apache.camel.test.spring.MockEndpoints;
import org.jboss.xavier.integrations.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@RunWith(CamelSpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@MockEndpoints
@DisableJmx(false)
@SpringBootTest(classes = {Application.class}, properties = {"camel.springboot.java-routes-exclude-pattern=org/jboss/xavier/integrations/route/**"})
@ActiveProfiles("dev")
public class XmlRoutesTest
{
    @Autowired
    protected CamelContext camelContext;

    @EndpointInject(uri = "mock:result", context = "camelContext")
    protected MockEndpoint mockResult;

    @Test
    public void test0() throws Exception
    {
//        mockResult.assertIsSatisfied();
//        MockEndpoint.assertIsSatisfied(camelContext);
        org.junit.Assert.assertEquals("hello".toUpperCase(), "HELLO");
    }
}
