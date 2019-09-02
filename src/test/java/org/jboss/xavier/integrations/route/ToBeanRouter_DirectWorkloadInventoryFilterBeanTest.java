package org.jboss.xavier.integrations.route;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.apache.camel.test.spring.UseAdviceWith;
import org.jboss.xavier.Application;
import org.jboss.xavier.integrations.route.model.WorkloadInventoryFilterBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@MockEndpointsAndSkip("")
@UseAdviceWith // Disables automatic start of Camel context
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
public class ToBeanRouter_DirectWorkloadInventoryFilterBeanTest {

    @Autowired
    CamelContext camelContext;

    @Test
    public void ToBeanRouterBuilder_routeToPaginationBean_GivenNoValidHeaders_ShouldAddFilterHeader() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        Map<String, Object> headers = new HashMap<>();
        headers.put("anotherHeader", "my custom header value");

        //When
        camelContext.start();
        camelContext.startRoute("to-workloadInventoryFilterBean");
        Exchange routeExchange = camelContext.createProducerTemplate().request("direct:to-workloadInventoryFilterBean", exchange -> {
            exchange.getIn().setBody("my custom body");
            exchange.getIn().setHeaders(headers);
        });

        //Then
        assertThat(routeExchange.getIn().getBody()).isEqualTo(routeExchange.getOut().getBody());
        assertThat(routeExchange.getOut().getHeaders().entrySet()).containsAll(headers.entrySet());

        Object filterHeader = routeExchange.getOut().getHeaders().get(ToBeanRouter.WORKLOAD_INVENTORY_FILTER_HEADER_NAME);
        assertThat(filterHeader).isInstanceOf(WorkloadInventoryFilterBean.class);

        WorkloadInventoryFilterBean filterBean = (WorkloadInventoryFilterBean) filterHeader;
        assertThat(filterBean.getProviders()).isNull();
        assertThat(filterBean.getDatacenters()).isNull();
        assertThat(filterBean.getClusters()).isNull();
        assertThat(filterBean.getVmNames()).isNull();
        assertThat(filterBean.getOsNames()).isNull();
        assertThat(filterBean.getWorkloads()).isNull();
        assertThat(filterBean.getComplexities()).isNull();
        assertThat(filterBean.getRecommendedTargetsIMS()).isNull();
        assertThat(filterBean.getFlagsIMS()).isNull();

        camelContext.stop();
    }

    @Test
    public void ToBeanRouterBuilder_routeToPaginationBean_GivenListHeaders_ShouldAddFilterHeader() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        Map<String, Object> headers = new HashMap<>();
        headers.put("anotherHeader", "my custom header value");

        Set<String> provider = new HashSet<>(Arrays.asList("my provider1", "my provider2"));
        headers.put("provider", provider);

        Set<String> cluster = new HashSet<>(Arrays.asList("my cluster1", "my cluster2"));
        headers.put("cluster", cluster);

        Set<String> datacenter = new HashSet<>(Arrays.asList("my datacenter1", "my datacenter2"));
        headers.put("datacenter", datacenter);

        Set<String> vmName = new HashSet<>(Arrays.asList("my vmName1", "my vmName2"));
        headers.put("vmName", vmName);

        Set<String> osName = new HashSet<>(Arrays.asList("my osName1", "my osName2"));
        headers.put("osName", osName);

        Set<String> workloads = new HashSet<>(Arrays.asList("my workload1", "my workload2"));
        headers.put("workload", workloads);

        Set<String> recommendedTargetsIMS = new HashSet<>(Arrays.asList("my recommendedTarget1", "my recommendedTarget2"));
        headers.put("recommendedTargetIMS", recommendedTargetsIMS);

        Set<String> flagsIMS = new HashSet<>(Arrays.asList("my flags1", "my flags2"));
        headers.put("flagIMS", flagsIMS);

        Set<String> complexity = new HashSet<>(Arrays.asList("my complexity1", "my complexity2"));
        headers.put("complexity", complexity);

        //When
        camelContext.start();
        camelContext.startRoute("to-workloadInventoryFilterBean");
        Exchange routeExchange = camelContext.createProducerTemplate().request("direct:to-workloadInventoryFilterBean", exchange -> {
            exchange.getIn().setBody("my custom body");
            exchange.getIn().setHeaders(headers);
        });

        //Then
        assertThat(routeExchange.getIn().getBody()).isEqualTo(routeExchange.getOut().getBody());
        assertThat(routeExchange.getOut().getHeaders().entrySet()).containsAll(headers.entrySet());

        Object filterHeader = routeExchange.getOut().getHeaders().get(ToBeanRouter.WORKLOAD_INVENTORY_FILTER_HEADER_NAME);
        assertThat(filterHeader).isInstanceOf(WorkloadInventoryFilterBean.class);

        WorkloadInventoryFilterBean filterBean = (WorkloadInventoryFilterBean) filterHeader;
        assertThat(filterBean.getProviders()).isEqualTo(provider);
        assertThat(filterBean.getDatacenters()).isEqualTo(datacenter);
        assertThat(filterBean.getClusters()).isEqualTo(cluster);
        assertThat(filterBean.getVmNames()).isEqualTo(vmName);
        assertThat(filterBean.getOsNames()).isEqualTo(osName);
        assertThat(filterBean.getWorkloads()).isEqualTo(workloads);
        assertThat(filterBean.getComplexities()).isEqualTo(complexity);
        assertThat(filterBean.getRecommendedTargetsIMS()).isEqualTo(recommendedTargetsIMS);
        assertThat(filterBean.getFlagsIMS()).isEqualTo(flagsIMS);

        camelContext.stop();
    }

    @Test
    public void ToBeanRouterBuilder_routeToPaginationBean_GivenHeaders_ShouldAddFilterHeader() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        Map<String, Object> headers = new HashMap<>();
        headers.put("anotherHeader", "my custom header value");

        String provider = "my provider";
        headers.put("provider", provider);

        String cluster = "my cluster";
        headers.put("cluster", cluster);

        String datacenter = "my datacenter";
        headers.put("datacenter", datacenter);

        String vmName = "my vmName";
        headers.put("vmName", vmName);

        String osName = "my osName";
        headers.put("osName", osName);

        String workload = "my workload";
        headers.put("workload", workload);

        String recommendedTargetIMS = "my recommendedTarget";
        headers.put("recommendedTargetIMS", recommendedTargetIMS);

        String flagIMS = "my flags";
        headers.put("flagIMS", flagIMS);

        String complexity = "my complexity";
        headers.put("complexity", complexity);

        //When
        camelContext.start();
        camelContext.startRoute("to-workloadInventoryFilterBean");
        Exchange routeExchange = camelContext.createProducerTemplate().request("direct:to-workloadInventoryFilterBean", exchange -> {
            exchange.getIn().setBody("my custom body");
            exchange.getIn().setHeaders(headers);
        });

        //Then
        assertThat(routeExchange.getIn().getBody()).isEqualTo(routeExchange.getOut().getBody());
        assertThat(routeExchange.getOut().getHeaders().entrySet()).containsAll(headers.entrySet());

        Object filterHeader = routeExchange.getOut().getHeaders().get(ToBeanRouter.WORKLOAD_INVENTORY_FILTER_HEADER_NAME);
        assertThat(filterHeader).isInstanceOf(WorkloadInventoryFilterBean.class);

        WorkloadInventoryFilterBean filterBean = (WorkloadInventoryFilterBean) filterHeader;
        assertThat(filterBean.getProviders()).isEqualTo(new HashSet<>(Collections.singletonList(provider)));
        assertThat(filterBean.getDatacenters()).isEqualTo(new HashSet<>(Collections.singletonList(datacenter)));
        assertThat(filterBean.getClusters()).isEqualTo(new HashSet<>(Collections.singletonList(cluster)));
        assertThat(filterBean.getVmNames()).isEqualTo(new HashSet<>(Collections.singletonList(vmName)));
        assertThat(filterBean.getOsNames()).isEqualTo(new HashSet<>(Collections.singletonList(osName)));
        assertThat(filterBean.getWorkloads()).isEqualTo(new HashSet<>(Collections.singletonList(workload)));
        assertThat(filterBean.getComplexities()).isEqualTo(new HashSet<>(Collections.singletonList(complexity)));
        assertThat(filterBean.getRecommendedTargetsIMS()).isEqualTo(new HashSet<>(Collections.singletonList(recommendedTargetIMS)));
        assertThat(filterBean.getFlagsIMS()).isEqualTo(new HashSet<>(Collections.singletonList(flagIMS)));

        camelContext.stop();
    }

}
