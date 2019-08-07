package org.jboss.xavier.integrations.route;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.apache.camel.test.spring.UseAdviceWith;
import org.jboss.xavier.Application;
import org.jboss.xavier.integrations.route.model.SortBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@MockEndpointsAndSkip("")
@UseAdviceWith // Disables automatic start of Camel context
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
public class ToBeanRouter_DirectToSortBeanTest {

    @Autowired
    CamelContext camelContext;

    @Test
    public void ToBeanRouterBuilder_routeToPaginationBean_GivenNoHeaders_ShouldAddPaginationHeader() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        Map<String, Object> headers = new HashMap<>();
        headers.put("anotherHeader", "my custom header value");

        //When
        camelContext.start();
        camelContext.startRoute("to-sortBean");
        Exchange routeExchange = camelContext.createProducerTemplate().request("direct:to-sortBean", exchange -> {
            exchange.getIn().setBody("my custom body");
            exchange.getIn().setHeaders(headers);
        });

        //Then
        assertThat(routeExchange.getIn().getBody()).isEqualTo(routeExchange.getOut().getBody());
        assertThat(routeExchange.getOut().getHeaders().entrySet()).containsAll(headers.entrySet());

        Object sortBean = routeExchange.getOut().getHeaders().get(ToBeanRouter.SORT_HEADER_NAME);
        assertThat(sortBean).isInstanceOf(SortBean.class);

        SortBean pageBean = (SortBean) sortBean;
        assertThat(pageBean.getOrderBy()).isNull();
        assertThat(pageBean.isOrderAsc()).isNull();

        camelContext.stop();
    }

    @Test
    public void ToBeanRouterBuilder_routeToPaginationBean_GivenHeaders_ShouldAddPaginationHeader() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        Map<String, Object> headers = new HashMap<>();
        headers.put("orderBy", "myColumnName");
        headers.put("orderAsc", true);
        headers.put("anotherHeader", "my custom header value");

        //When
        camelContext.start();
        camelContext.startRoute("to-sortBean");
        Exchange routeExchange = camelContext.createProducerTemplate().request("direct:to-sortBean", exchange -> {
            exchange.getIn().setBody("my custom body");
            exchange.getIn().setHeaders(headers);
        });

        //Then
        assertThat(routeExchange.getIn().getBody()).isEqualTo(routeExchange.getOut().getBody());
        assertThat(routeExchange.getOut().getHeaders().entrySet()).containsAll(headers.entrySet());

        Object paginationHeader = routeExchange.getOut().getHeaders().get(ToBeanRouter.SORT_HEADER_NAME);
        assertThat(paginationHeader).isInstanceOf(SortBean.class);

        SortBean sortBean = (SortBean) paginationHeader;
        assertThat(sortBean.getOrderBy()).isEqualTo(headers.get("orderBy"));
        assertThat(sortBean.isOrderAsc()).isEqualTo(headers.get("orderAsc"));

        camelContext.stop();
    }

    @Test
    public void ToBeanRouterBuilder_routeToPaginationBean_GivenNullHeaders_ShouldAddPaginationHeader() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        Map<String, Object> headers = new HashMap<>();
        headers.put("orderBy", null);
        headers.put("orderAsc", null);
        headers.put("anotherHeader", "my custom header value");

        //When
        camelContext.start();
        camelContext.startRoute("to-sortBean");
        Exchange routeExchange = camelContext.createProducerTemplate().request("direct:to-sortBean", exchange -> {
            exchange.getIn().setBody("my custom body");
            exchange.getIn().setHeaders(headers);
        });

        //Then
        assertThat(routeExchange.getIn().getBody()).isEqualTo(routeExchange.getOut().getBody());
        assertThat(routeExchange.getOut().getHeaders().entrySet()).containsAll(headers.entrySet());

        Object paginationHeader = routeExchange.getOut().getHeaders().get(ToBeanRouter.SORT_HEADER_NAME);
        assertThat(paginationHeader).isInstanceOf(SortBean.class);

        SortBean sortBean = (SortBean) paginationHeader;
        assertThat(sortBean.getOrderBy()).isNull();
        assertThat(sortBean.isOrderAsc()).isNull();

        camelContext.stop();
    }

    @Test
    public void ToBeanRouterBuilder_routeToPaginationBean_GivenStringHeaders_ShouldAddPaginationHeader() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        Map<String, Object> headers = new HashMap<>();
        headers.put("orderBy", "myColumnName");
        headers.put("orderAsc", "true");
        headers.put("anotherHeader", "my custom header value");

        //When
        camelContext.start();
        camelContext.startRoute("to-sortBean");
        Exchange routeExchange = camelContext.createProducerTemplate().request("direct:to-sortBean", exchange -> {
            exchange.getIn().setBody("my custom body");
            exchange.getIn().setHeaders(headers);
        });

        //Then
        assertThat(routeExchange.getIn().getBody()).isEqualTo(routeExchange.getOut().getBody());
        assertThat(routeExchange.getOut().getHeaders().entrySet()).containsAll(headers.entrySet());

        Object paginationHeader = routeExchange.getOut().getHeaders().get(ToBeanRouter.SORT_HEADER_NAME);
        assertThat(paginationHeader).isInstanceOf(SortBean.class);

        SortBean sortBean = (SortBean) paginationHeader;
        assertThat(sortBean.isOrderAsc()).isEqualTo(true);

        camelContext.stop();
    }
}
