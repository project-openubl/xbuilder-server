package org.jboss.xavier.integrations.route;

import org.apache.camel.Exchange;
import org.jboss.xavier.integrations.route.model.SortBean;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ToBeanRouter_DirectToSortBeanTest extends XavierCamelTest {

    @Test
    public void ToBeanRouterBuilder_routeToPaginationBean_GivenNoHeaders_ShouldAddPaginationHeader() throws Exception {
        //Given
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
