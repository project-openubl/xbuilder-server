package org.jboss.xavier.integrations.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.jboss.xavier.integrations.route.model.PageBean;
import org.jboss.xavier.integrations.route.model.SortBean;
import org.jboss.xavier.integrations.route.model.WorkloadInventoryFilterBean;
import org.jboss.xavier.utils.ConversionUtils;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class ToBeanRouter extends RouteBuilderExceptionHandler {

    public static final String PAGE_HEADER_NAME = "pageBean";
    public static final String SORT_HEADER_NAME = "sortBean";
    public static final String WORKLOAD_INVENTORY_FILTER_HEADER_NAME = "workloadInventoryFilterBean";

    @Override
    public void configure() throws Exception {
        super.configure();

        from("direct:to-paginationBean")
                .routeId("to-paginationBean")
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
                        exchange.getOut().setAttachments(exchange.getIn().getAttachments());
                    }
                });

        from("direct:to-sortBean")
                .routeId("to-sortBean")
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
                        exchange.getOut().setAttachments(exchange.getIn().getAttachments());
                    }
                });

        from("direct:to-workloadInventoryFilterBean")
                .routeId("to-workloadInventoryFilterBean")
                .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        // extract the name parameter from the Camel message which we want to use
                        Object providerByHeader = exchange.getIn().getHeader("provider");
                        List<String> providerList = ConversionUtils.toList(providerByHeader);
                        Set<String> provider = providerList != null ? new HashSet<>(providerList) : null;

                        Object datacenterHeader = exchange.getIn().getHeader("datacenter");
                        List<String> datacenterList = ConversionUtils.toList(datacenterHeader);
                        Set<String> datacenter = datacenterList != null ? new HashSet<>(datacenterList) : null;

                        Object clusterHeader = exchange.getIn().getHeader("cluster");
                        List<String> clusterList = ConversionUtils.toList(clusterHeader);
                        Set<String> cluster = clusterList != null ? new HashSet<>(clusterList) : null;

                        Object vmNameHeader = exchange.getIn().getHeader("vmName");
                        List<String> vmNameList = ConversionUtils.toList(vmNameHeader);
                        Set<String> vmName = vmNameList != null ? new HashSet<>(vmNameList) : null;

                        Object osNameHeader = exchange.getIn().getHeader("osName");
                        List<String> osNameList = ConversionUtils.toList(osNameHeader);
                        Set<String> osName = osNameList != null ? new HashSet<>(osNameList) : null;

                        Object workloadsHeader = exchange.getIn().getHeader("workload");
                        List<String> workloadsList = ConversionUtils.toList(workloadsHeader);
                        Set<String> workloads = workloadsList != null ? new HashSet<>(workloadsList) : null;

                        Object complexityHeader = exchange.getIn().getHeader("complexity");
                        List<String> complexityList = ConversionUtils.toList(complexityHeader);
                        Set<String> complexity = complexityList != null ? new HashSet<>(complexityList) : null;

                        Object recommendedTargetsHeader = exchange.getIn().getHeader("recommendedTargetIMS");
                        List<String> recommendedTargetsList = ConversionUtils.toList(recommendedTargetsHeader);
                        Set<String> recommendedTargets = recommendedTargetsList != null ? new HashSet<>(recommendedTargetsList) : null;

                        Object flagsHeader = exchange.getIn().getHeader("flagIMS");
                        List<String> flagsList = ConversionUtils.toList(flagsHeader);
                        Set<String> flags = flagsList != null ? new HashSet<>(flagsList) : null;

                        // create pagination header
                        WorkloadInventoryFilterBean filterBean = new WorkloadInventoryFilterBean();
                        filterBean.setProviders(provider);
                        filterBean.setDatacenters(datacenter);
                        filterBean.setClusters(cluster);
                        filterBean.setVmNames(vmName);
                        filterBean.setOsNames(osName);
                        filterBean.setWorkloads(workloads);
                        filterBean.setComplexities(complexity);
                        filterBean.setRecommendedTargetsIMS(recommendedTargets);
                        filterBean.setFlagsIMS(flags);

                        Map<String, Object> headers = exchange.getIn().getHeaders();
                        headers.put(WORKLOAD_INVENTORY_FILTER_HEADER_NAME, filterBean);

                        // store the reply from the bean on the OUT message
                        exchange.getOut().setHeaders(headers);
                        exchange.getOut().setBody(exchange.getIn().getBody());
                        exchange.getOut().setAttachments(exchange.getIn().getAttachments());
                    }
                });
    }

}
