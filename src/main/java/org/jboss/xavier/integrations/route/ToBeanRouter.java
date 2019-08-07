package org.jboss.xavier.integrations.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.jboss.xavier.integrations.route.model.PageBean;
import org.jboss.xavier.integrations.route.model.SortBean;
import org.jboss.xavier.utils.ConversionUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ToBeanRouter extends RouteBuilder {

    public static final String PAGE_HEADER_NAME = "pageBean";
    public static final String SORT_HEADER_NAME = "sortBean";

    @Override
    public void configure() throws Exception {
        from("direct:to-paginationBean")
                .id("to-paginationBean")
                .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        // extract the name parameter from the Camel message which we want to use
                        Object pageHeader = exchange.getIn().getHeader("page");
                        Integer page = ConversionUtils.toInteger(pageHeader);

                        Object sizeHeader = exchange.getIn().getHeader("size");
                        Integer size = ConversionUtils.toInteger(sizeHeader);

                        // create pagination header
                        Map<String, Object> headers = exchange.getIn().getHeaders();
                        headers.put(PAGE_HEADER_NAME, new PageBean(page, size));

                        // store the reply from the bean on the OUT message
                        exchange.getOut().setHeaders(headers);
                        exchange.getOut().setBody(exchange.getIn().getBody());
                    }
                });

        from("direct:to-sortBean")
                .id("to-sortBean")
                .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        // extract the name parameter from the Camel message which we want to use
                        Object orderByHeader = exchange.getIn().getHeader("orderBy");
                        String orderBy = orderByHeader != null ? (String) orderByHeader : null;

                        Object orderAscHeader = exchange.getIn().getHeader("orderAsc");
                        Boolean orderAsc = ConversionUtils.toBoolean(orderAscHeader);

                        // create pagination header
                        Map<String, Object> headers = exchange.getIn().getHeaders();
                        headers.put(SORT_HEADER_NAME, new SortBean(orderBy, orderAsc));

                        // store the reply from the bean on the OUT message
                        exchange.getOut().setHeaders(headers);
                        exchange.getOut().setBody(exchange.getIn().getBody());
                    }
                });
    }

}
