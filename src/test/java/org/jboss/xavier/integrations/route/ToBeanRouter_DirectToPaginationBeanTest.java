package org.jboss.xavier.integrations.route;

import org.apache.camel.Exchange;
import org.jboss.xavier.integrations.route.model.PageBean;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


public class ToBeanRouter_DirectToPaginationBeanTest extends XavierCamelTest {

    @Test
    public void ToBeanRouterBuilder_routeToPaginationBean_GivenNoHeaders_ShouldAddPaginationHeader() throws Exception {
        //Given
        Map<String, Object> headers = new HashMap<>();
        headers.put("anotherHeader", "my custom header value");

        //When
        camelContext.start();
        camelContext.startRoute("to-paginationBean");
        Exchange routeExchange = camelContext.createProducerTemplate().request("direct:to-paginationBean", exchange -> {
            exchange.getIn().setBody("my custom body");
            exchange.getIn().setHeaders(headers);
        });

        //Then
        assertThat(routeExchange.getIn().getBody()).isEqualTo(routeExchange.getOut().getBody());
        assertThat(routeExchange.getOut().getHeaders().entrySet()).containsAll(headers.entrySet());

        Object paginationHeader = routeExchange.getOut().getHeaders().get(ToBeanRouter.PAGE_HEADER_NAME);
        assertThat(paginationHeader).isInstanceOf(PageBean.class);

        PageBean pageBean = (PageBean) paginationHeader;
        assertThat(pageBean.getPage()).isNull();
        assertThat(pageBean.getSize()).isNull();

        camelContext.stop();
    }

    @Test
    public void ToBeanRouterBuilder_routeToPaginationBean_GivenHeaders_ShouldAddPaginationHeader() throws Exception {
        //Given
        Map<String, Object> headers = new HashMap<>();
        headers.put("page", 2);
        headers.put("size", 20);
        headers.put("anotherHeader", "my custom header value");

        //When
        camelContext.start();
        camelContext.startRoute("to-paginationBean");
        Exchange routeExchange = camelContext.createProducerTemplate().request("direct:to-paginationBean", exchange -> {
            exchange.getIn().setBody("my custom body");
            exchange.getIn().setHeaders(headers);
        });

        //Then
        assertThat(routeExchange.getIn().getBody()).isEqualTo(routeExchange.getOut().getBody());
        assertThat(routeExchange.getOut().getHeaders().entrySet()).containsAll(headers.entrySet());

        Object paginationHeader = routeExchange.getOut().getHeaders().get(ToBeanRouter.PAGE_HEADER_NAME);
        assertThat(paginationHeader).isInstanceOf(PageBean.class);

        PageBean pageBean = (PageBean) paginationHeader;
        assertThat(pageBean.getPage()).isEqualTo(headers.get("page"));
        assertThat(pageBean.getSize()).isEqualTo(headers.get("size"));

        camelContext.stop();
    }

    @Test
    public void ToBeanRouterBuilder_routeToPaginationBean_GivenNullHeaders_ShouldAddPaginationHeader() throws Exception {
        //Given
        Map<String, Object> headers = new HashMap<>();
        headers.put("page", null);
        headers.put("size", null);
        headers.put("anotherHeader", "my custom header value");

        //When
        camelContext.start();
        camelContext.startRoute("to-paginationBean");
        Exchange routeExchange = camelContext.createProducerTemplate().request("direct:to-paginationBean", exchange -> {
            exchange.getIn().setBody("my custom body");
            exchange.getIn().setHeaders(headers);
        });

        //Then
        assertThat(routeExchange.getIn().getBody()).isEqualTo(routeExchange.getOut().getBody());
        assertThat(routeExchange.getOut().getHeaders().entrySet()).containsAll(headers.entrySet());

        Object paginationHeader = routeExchange.getOut().getHeaders().get(ToBeanRouter.PAGE_HEADER_NAME);
        assertThat(paginationHeader).isInstanceOf(PageBean.class);

        PageBean pageBean = (PageBean) paginationHeader;
        assertThat(pageBean.getPage()).isNull();
        assertThat(pageBean.getSize()).isNull();

        camelContext.stop();
    }

    @Test
    public void ToBeanRouterBuilder_routeToPaginationBean_GivenStringHeaders_ShouldAddPaginationHeader() throws Exception {
        //Given
        Map<String, Object> headers = new HashMap<>();
        headers.put("page", "2");
        headers.put("size", "20");
        headers.put("anotherHeader", "my custom header value");

        //When
        camelContext.start();
        camelContext.startRoute("to-paginationBean");
        Exchange routeExchange = camelContext.createProducerTemplate().request("direct:to-paginationBean", exchange -> {
            exchange.getIn().setBody("my custom body");
            exchange.getIn().setHeaders(headers);
        });

        //Then
        assertThat(routeExchange.getIn().getBody()).isEqualTo(routeExchange.getOut().getBody());
        assertThat(routeExchange.getOut().getHeaders().entrySet()).containsAll(headers.entrySet());

        Object paginationHeader = routeExchange.getOut().getHeaders().get(ToBeanRouter.PAGE_HEADER_NAME);
        assertThat(paginationHeader).isInstanceOf(PageBean.class);

        PageBean pageBean = (PageBean) paginationHeader;
        assertThat(pageBean.getPage()).isEqualTo(2);
        assertThat(pageBean.getSize()).isEqualTo(20);

        camelContext.stop();
    }
}
