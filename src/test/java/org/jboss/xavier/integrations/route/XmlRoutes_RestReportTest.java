package org.jboss.xavier.integrations.route;

import org.apache.camel.CamelContext;
import org.apache.camel.component.rest.RestEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.apache.camel.test.spring.UseAdviceWith;
import org.jboss.xavier.Application;
import org.jboss.xavier.analytics.pojo.output.AnalysisModel;
import org.jboss.xavier.integrations.jpa.service.*;
import org.jboss.xavier.integrations.route.model.PageBean;
import org.jboss.xavier.integrations.route.model.SortBean;
import org.jboss.xavier.integrations.route.model.WorkloadInventoryFilterBean;
import org.jboss.xavier.integrations.util.TestUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@MockEndpointsAndSkip("")
@UseAdviceWith // Disables automatic start of Camel context
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class XmlRoutes_RestReportTest {
    @Autowired
    CamelContext camelContext;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private InitialSavingsEstimationReportService initialSavingsEstimationReportService;

    @MockBean
    private WorkloadInventoryReportService workloadInventoryReportService;

    @MockBean
    private WorkloadService workloadService;

    @MockBean
    private FlagService flagService;

    @SpyBean
    private AnalysisService analysisService;

    @SpyBean
    private WorkloadSummaryReportService workloadSummaryReportService;

    @Value("${camel.component.servlet.mapping.context-path}")
    String camel_context;

    @Before
    public void setup() {
        camel_context = camel_context.substring(0, camel_context.indexOf("*"));
    }

    @Test
    public void xmlRouteBuilder_RestReport_NoParamGiven_ShouldCallFindReports() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        TestUtil.startUsernameRoutes(camelContext);
        camelContext.startRoute("reports-get-all");

        HttpHeaders headers = new HttpHeaders();
        headers.set(TestUtil.HEADER_RH_IDENTITY, TestUtil.getBase64RHIdentity());
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(camel_context + "report/", HttpMethod.GET, entity, String.class);

        //Then
        verify(analysisService).findAllByOwner("mrizzi@redhat.com", 0, 10);
        assertThat(response).isNotNull();
        assertThat(response.getBody()).contains("\"content\":[]");
        assertThat(response.getBody()).contains("\"size\":10");
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestReport_NoRHIdentityGiven_ShouldReturnForbidden() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        TestUtil.startUsernameRoutes(camelContext);
        camelContext.startRoute("reports-get-all");
        ResponseEntity<String> result = restTemplate.getForEntity(camel_context + "report", String.class);

        //Then
        assertThat(result).isNotNull();
        assertThat(result.getStatusCodeValue()).isEqualByComparingTo(403);
        assertThat(result.getBody()).isEqualTo("Forbidden");
        verifyZeroInteractions(analysisService);
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestReport_PageAndSizeParamGiven_ShouldCallFindReports() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        TestUtil.startUsernameRoutes(camelContext);
        camelContext.startRoute("reports-get-all");
        Map<String, Object> variables = new HashMap<>();
        int page = 2;
        variables.put("page", page);
        int size = 3;
        variables.put("size", size);

        HttpHeaders headers = new HttpHeaders();
        headers.set(TestUtil.HEADER_RH_IDENTITY, TestUtil.getBase64RHIdentity());
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(camel_context + "report?page={page}&size={size}", HttpMethod.GET, entity, String.class, variables);

        //Then
        verify(analysisService).findAllByOwner("mrizzi@redhat.com", page, size);
        assertThat(response).isNotNull();
        assertThat(response.getBody()).contains("\"content\":[]");
        assertThat(response.getBody()).contains("\"size\":3");
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestReport_FilterTextPageAndSizeParamGiven_ShouldCallFindReports() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        TestUtil.startUsernameRoutes(camelContext);
        camelContext.startRoute("reports-get-all");
        Map<String, Object> variables = new HashMap<>();
        int page = 2;
        variables.put("page", page);
        int size = 3;
        variables.put("size", size);
        String filterText = "my report name which I'm searching";
        variables.put("filterText", filterText);

        HttpHeaders headers = new HttpHeaders();
        headers.set(TestUtil.HEADER_RH_IDENTITY, TestUtil.getBase64RHIdentity());
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(camel_context + "report?page={page}&size={size}&filterText={filterText}", HttpMethod.GET, entity, String.class, variables);

        //Then
        verify(analysisService).findByOwnerAndReportName("mrizzi@redhat.com", filterText, page, size);
        assertThat(response).isNotNull();
        assertThat(response.getBody()).contains("\"content\":[]");
        assertThat(response.getBody()).contains("\"size\":3");
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestReportId_IdParamGiven_ShouldCallFindById() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        TestUtil.startUsernameRoutes(camelContext);
        camelContext.startRoute("report-get-details");
        camelContext.startRoute("add-username-header");

        Map<String, Long> variables = new HashMap<>();
        Long one = 1L;
        variables.put("id", one);

        HttpHeaders headers = new HttpHeaders();
        headers.set(TestUtil.HEADER_RH_IDENTITY, TestUtil.getBase64RHIdentity());
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(camel_context + "report/{id}", HttpMethod.GET, entity, String.class, variables);

        //Then
        verify(analysisService).findByOwnerAndId("mrizzi@redhat.com", one);
        assertThat(response).isNotNull();
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestReportIdInitialSavingsEstimation_IdParamGiven_ShouldCallFindOneByAnalysisId() throws Exception {
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        TestUtil.startUsernameRoutes(camelContext);
        camelContext.startRoute("reports-get-details");

        HttpHeaders headers = new HttpHeaders();
        headers.set(TestUtil.HEADER_RH_IDENTITY, TestUtil.getBase64RHIdentity());
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        Map<String, Object> variables = new HashMap<>();
        Long one = 1L;
        variables.put("id", one);

        ResponseEntity<String> response = restTemplate.exchange(camel_context + "report/{id}/initial-saving-estimation", HttpMethod.GET, entity, String.class, variables);

        //Then
        verify(initialSavingsEstimationReportService).findByAnalysisOwnerAndAnalysisId("mrizzi@redhat.com", one);
        assertThat(response).isNotNull();
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestReportIdWorkloadInventory_IdParamGiven_PageParamGiven_SizeParamGiven_ShouldCallFindByAnalysisId() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        TestUtil.startUsernameRoutes(camelContext);
        camelContext.startRoute("to-paginationBean");
        camelContext.startRoute("to-sortBean");
        camelContext.startRoute("to-workloadInventoryFilterBean");
        camelContext.startRoute("workload-inventory-report-get-details");
        Map<String, Object> variables = new HashMap<>();
        Long one = 1L;
        variables.put("id", one);
        int page = 2;
        variables.put("page", page);
        int size = 3;
        variables.put("size", size);

        HttpHeaders headers = new HttpHeaders();
        headers.set(TestUtil.HEADER_RH_IDENTITY, TestUtil.getBase64RHIdentity());
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(camel_context + "report/{id}/workload-inventory?page={page}&size={size}", HttpMethod.GET, entity, String.class, variables);

        //Then
        PageBean pageBean = new PageBean(page, size);
        SortBean sortBean = new SortBean("id", false);
        WorkloadInventoryFilterBean filterBean = new WorkloadInventoryFilterBean();

        verify(workloadInventoryReportService).findByAnalysisOwnerAndAnalysisId("mrizzi@redhat.com", one, pageBean, sortBean, filterBean);
        assertThat(response).isNotNull();
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestReportIdWorkloadInventory_IdParamGiven_ShouldCallFindByAnalysisId() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        TestUtil.startUsernameRoutes(camelContext);
        camelContext.startRoute("to-paginationBean");
        camelContext.startRoute("to-sortBean");
        camelContext.startRoute("to-workloadInventoryFilterBean");
        camelContext.startRoute("workload-inventory-report-get-details");
        Map<String, Object> variables = new HashMap<>();
        Long one = 1L;
        variables.put("id", one);

        HttpHeaders headers = new HttpHeaders();
        headers.set(TestUtil.HEADER_RH_IDENTITY, TestUtil.getBase64RHIdentity());
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(camel_context + "report/{id}/workload-inventory", HttpMethod.GET, entity, String.class, variables);

        //Then
        PageBean pageBean = new PageBean(0, 10);
        SortBean sortBean = new SortBean("id", false);
        WorkloadInventoryFilterBean filterBean = new WorkloadInventoryFilterBean();

        verify(workloadInventoryReportService).findByAnalysisOwnerAndAnalysisId("mrizzi@redhat.com", one, pageBean, sortBean, filterBean);
        assertThat(response).isNotNull();
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestReportIdWorkloadInventory_IdParamGiven_SortParamGiven_ShouldCallFindByAnalysisId() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        TestUtil.startUsernameRoutes(camelContext);
        camelContext.startRoute("to-paginationBean");
        camelContext.startRoute("to-sortBean");
        camelContext.startRoute("to-workloadInventoryFilterBean");
        camelContext.startRoute("workload-inventory-report-get-details");
        Map<String, Object> variables = new HashMap<>();
        Long one = 1L;
        variables.put("id", one);
        String orderBy = "vmName";
        variables.put("orderBy", orderBy);
        Boolean orderAsc = true;
        variables.put("orderAsc", orderAsc);

        HttpHeaders headers = new HttpHeaders();
        headers.set(TestUtil.HEADER_RH_IDENTITY, TestUtil.getBase64RHIdentity());
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(camel_context + "report/{id}/workload-inventory?orderBy={orderBy}&orderAsc={orderAsc}", HttpMethod.GET, entity, String.class, variables);

        //Then
        PageBean pageBean = new PageBean(0, 10);
        SortBean sortBean = new SortBean(orderBy, orderAsc);
        WorkloadInventoryFilterBean filterBean = new WorkloadInventoryFilterBean();

        verify(workloadInventoryReportService).findByAnalysisOwnerAndAnalysisId("mrizzi@redhat.com", one, pageBean, sortBean, filterBean);
        assertThat(response).isNotNull();
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestReportIdWorkloadInventory_IdParamGiven_FiltersGiven_ShouldCallFindByAnalysisId() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        TestUtil.startUsernameRoutes(camelContext);
        camelContext.startRoute("to-paginationBean");
        camelContext.startRoute("to-sortBean");
        camelContext.startRoute("to-workloadInventoryFilterBean");
        camelContext.startRoute("workload-inventory-report-get-details");

        Map<String, Object> variables = new HashMap<>();

        Long one = 1L;
        variables.put("id", one);

        String provider1 = "my provider1";
        variables.put("provider1", provider1);
        String provider2 = "my provider2";
        variables.put("provider2", provider2);

        String cluster1 = "my cluster1";
        variables.put("cluster1", cluster1);
        String cluster2 = "my cluster2";
        variables.put("cluster2", cluster2);

        String datacenter1 = "my datacenter1";
        variables.put("datacenter1", datacenter1);
        String datacenter2 = "my datacenter2";
        variables.put("datacenter2", datacenter2);

        String vmName1 = "my vmName1";
        variables.put("vmName1", vmName1);
        String vmName2 = "my vmName2";
        variables.put("vmName2", vmName2);

        String osName1 = "my osName1";
        variables.put("osName1", osName1);
        String osName2 = "my osName2";
        variables.put("osName2", osName2);

        String workload1 = "my workload1";
        variables.put("workload1", workload1);
        String workload2 = "my workload2";
        variables.put("workload2", workload2);

        String recommendedTarget1 = "my recommendedTarget1";
        variables.put("recommendedTarget1", recommendedTarget1);
        String recommendedTarget2 = "my recommendedTarget2";
        variables.put("recommendedTarget2", recommendedTarget2);

        String flag1 = "my flag1";
        variables.put("flag1", flag1);
        String flag2 = "my flag2";
        variables.put("flag2", flag2);

        String complexity1 = "my complexity1";
        variables.put("complexity1", complexity1);
        String complexity2 = "my complexity2";
        variables.put("complexity2", complexity2);

        StringBuilder sb = new StringBuilder(camel_context + "report/{id}/workload-inventory?")
                .append("provider={provider1}&")
                .append("provider={provider2}&")
                .append("cluster={cluster1}&")
                .append("cluster={cluster2}&")
                .append("datacenter={datacenter1}&")
                .append("datacenter={datacenter2}&")
                .append("vmName={vmName1}&")
                .append("vmName={vmName2}&")
                .append("osName={osName1}&")
                .append("osName={osName2}&")
                .append("workload={workload1}&")
                .append("workload={workload2}&")
                .append("recommendedTargetIMS={recommendedTarget1}&")
                .append("recommendedTargetIMS={recommendedTarget2}&")
                .append("flagIMS={flag1}&")
                .append("flagIMS={flag2}&")
                .append("complexity={complexity1}&")
                .append("complexity={complexity2}");

        HttpHeaders headers = new HttpHeaders();
        headers.set(TestUtil.HEADER_RH_IDENTITY, TestUtil.getBase64RHIdentity());
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(sb.toString(), HttpMethod.GET, entity, String.class, variables);

        //Then
        PageBean pageBean = new PageBean(0, 10);
        SortBean sortBean = new SortBean("id", false);
        WorkloadInventoryFilterBean filterBean = new WorkloadInventoryFilterBean();
        filterBean.setProviders(new HashSet<>(Arrays.asList(provider1, provider2)));
        filterBean.setClusters(new HashSet<>(Arrays.asList(cluster1, cluster2)));
        filterBean.setDatacenters(new HashSet<>(Arrays.asList(datacenter1, datacenter2)));;
        filterBean.setVmNames(new HashSet<>(Arrays.asList(vmName1, vmName2)));;
        filterBean.setOsNames(new HashSet<>(Arrays.asList(osName1, osName2)));;
        filterBean.setWorkloads(new HashSet<>(Arrays.asList(workload1, workload2)));
        filterBean.setRecommendedTargetsIMS(new HashSet<>(Arrays.asList(recommendedTarget1, recommendedTarget2)));
        filterBean.setFlagsIMS(new HashSet<>(Arrays.asList(flag1, flag2)));
        filterBean.setComplexities(new HashSet<>(Arrays.asList(complexity1, complexity2)));

        verify(workloadInventoryReportService).findByAnalysisOwnerAndAnalysisId("mrizzi@redhat.com", one, pageBean, sortBean, filterBean);
        assertThat(response).isNotNull();
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestReportId_IdParamGiven_AndIdNotExists_ShouldReturnNotFound404Status() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        Long one = 1L;
        when(analysisService.findByOwnerAndId("mrizzi@redhat.com", one)).thenReturn(null);

        //When
        camelContext.start();
        TestUtil.startUsernameRoutes(camelContext);
        camelContext.startRoute("report-delete");
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", one);

        HttpHeaders headers = new HttpHeaders();
        headers.set(TestUtil.HEADER_RH_IDENTITY, TestUtil.getBase64RHIdentity());
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(camel_context + "report/{id}", HttpMethod.DELETE, entity, String.class, variables);

        //Then
        Assert.assertEquals(response.getStatusCodeValue(), HttpServletResponse.SC_NOT_FOUND);
        verify(analysisService).findByOwnerAndId("mrizzi@redhat.com", one);
        verify(analysisService, never()).deleteById(one);
        assertThat(response).isNotNull();
        assertThat(response.getBody()).contains("Analysis not found");
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestReportId_IdParamGiven_AndIdExists_ShouldCallDeleteById() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        Long one = 1L;
        when(analysisService.findByOwnerAndId("mrizzi@redhat.com",one)).thenReturn(new AnalysisModel());
        doNothing().when(analysisService).deleteById(one);

        //When
        camelContext.start();
        TestUtil.startUsernameRoutes(camelContext);
        camelContext.startRoute("report-delete");
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", one);

        HttpHeaders headers = new HttpHeaders();
        headers.set(TestUtil.HEADER_RH_IDENTITY, TestUtil.getBase64RHIdentity());
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(camel_context + "report/{id}", HttpMethod.DELETE, entity, String.class, variables);

        //Then
        Assert.assertEquals(response.getStatusCodeValue(), HttpServletResponse.SC_NO_CONTENT);
        Assert.assertNull(response.getBody());
        verify(analysisService).findByOwnerAndId("mrizzi@redhat.com",one);
        verify(analysisService).deleteById(one);
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNull();
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestReportIdWorkloadInventory_IdParamGiven_ShouldCallFindByAnalysisIdAndReturnCsv() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        TestUtil.startUsernameRoutes(camelContext);
        camelContext.startRoute("workload-inventory-report-get-details-as-csv");
        camelContext.startRoute("workload-inventory-report-model-to-csv");
        Map<String, Object> variables = new HashMap<>();
        Long one = 1L;
        variables.put("id", one);
        HttpHeaders headers = new HttpHeaders();
        headers.add("whatever", "this header should not be copied");
        headers.set(TestUtil.HEADER_RH_IDENTITY, TestUtil.getBase64RHIdentity());
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(camel_context + "report/{id}/workload-inventory/csv" , HttpMethod.GET, entity, String.class, variables);

        //Then
        verify(workloadInventoryReportService).findByAnalysisOwnerAndAnalysisId("mrizzi@redhat.com", one);
        Assert.assertTrue(response.getHeaders().get("Content-Type").contains("text/csv"));
        Assert.assertTrue(response.getHeaders().get("Content-Disposition").contains("attachment;filename=workloadInventory_1.csv"));
        Assert.assertNull(response.getHeaders().get("whatever"));
        assertThat(response).isNotNull();
        assertThat(response.getBody()).contains("Provider,Datacenter,Cluster,VM name,OS type,Operating system description,Disk space,Memory,CPU cores,Workload,Effort,Recommended targets,Flags IMS,Product,Version,HostName");
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestReportIdWorkloadInventory_IdParamGiven_ShouldCallFindByAnalysisIdAndReturnAvailableFilters() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);
        Long one = 1L;
        when(analysisService.findByOwnerAndId("mrizzi@redhat.com", one)).thenReturn(new AnalysisModel());

        //When
        camelContext.start();
        TestUtil.startUsernameRoutes(camelContext);
        camelContext.startRoute("workload-inventory-report-available-filters");
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", one);
        HttpHeaders headers = new HttpHeaders();
        headers.add("whatever", "this header should not be copied");
        headers.set(TestUtil.HEADER_RH_IDENTITY, TestUtil.getBase64RHIdentity());
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(camel_context + "report/{id}/workload-inventory/available-filters" , HttpMethod.GET, entity, String.class, variables);

        //Then
        verify(analysisService).findByOwnerAndId("mrizzi@redhat.com", one);
        verify(workloadInventoryReportService).findAvailableFiltersByAnalysisId(one);
        assertThat(response).isNotNull();
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestReportIdWorkloadSummary_IdParamGiven_ShouldCallFindByAnalysisOwnerAndAnalysisId() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        TestUtil.startUsernameRoutes(camelContext);
        camelContext.startRoute("workload-summary-report-get");
        Map<String, Object> variables = new HashMap<>();
        Long analysisId = 11L;
        variables.put("id", analysisId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("whatever", "this header should not be copied");
        headers.add(TestUtil.HEADER_RH_IDENTITY, TestUtil.getBase64RHIdentity());
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(camel_context + "report/{id}/workload-summary" , HttpMethod.GET, entity, String.class, variables);

        //Then
        verify(workloadSummaryReportService).findByAnalysisOwnerAndAnalysisId("mrizzi@redhat.com", analysisId);
        Assert.assertNull(response.getHeaders().get("whatever"));
        assertThat(response).isNotNull();
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestReportIdWorkloadSummaryWorkloads_IdParamGiven_ShouldCallFindByAnalysisId() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        TestUtil.startUsernameRoutes(camelContext);
        camelContext.startRoute("to-paginationBean");
        camelContext.startRoute("to-sortBean");
        camelContext.startRoute("workload-summary-workloads-report-get");
        Map<String, Object> variables = new HashMap<>();
        Long one = 1L;
        variables.put("id", one);

        HttpHeaders headers = new HttpHeaders();
        headers.set(TestUtil.HEADER_RH_IDENTITY, TestUtil.getBase64RHIdentity());
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(camel_context + "report/{id}/workload-summary/workloads", HttpMethod.GET, entity, String.class, variables);

        //Then
        PageBean pageBean = new PageBean(0, 10);
        SortBean sortBean = new SortBean("id", false);

        verify(workloadService).findByReportAnalysisOwnerAndReportAnalysisId("mrizzi@redhat.com", one, pageBean, sortBean);
        assertThat(response).isNotNull();
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestReportIdWorkloadSummaryWorkloads_IdParamGiven_PaginationGiven_ShouldCallFindByAnalysisId() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        TestUtil.startUsernameRoutes(camelContext);
        camelContext.startRoute("to-paginationBean");
        camelContext.startRoute("to-sortBean");
        camelContext.startRoute("workload-summary-workloads-report-get");
        Map<String, Object> variables = new HashMap<>();
        Long one = 1L;
        variables.put("id", one);
        int page = 2;
        variables.put("page", page);
        int size = 3;
        variables.put("size", size);

        HttpHeaders headers = new HttpHeaders();
        headers.set(TestUtil.HEADER_RH_IDENTITY, TestUtil.getBase64RHIdentity());
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(camel_context + "report/{id}/workload-summary/workloads?page={page}&size={size}", HttpMethod.GET, entity, String.class, variables);

        //Then
        PageBean pageBean = new PageBean(page, size);
        SortBean sortBean = new SortBean("id", false);

        verify(workloadService).findByReportAnalysisOwnerAndReportAnalysisId("mrizzi@redhat.com", one, pageBean, sortBean);
        assertThat(response).isNotNull();
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestReportIdWorkloadSummaryWorkloads_IdParamGiven_SortGiven_ShouldCallFindByAnalysisId() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        TestUtil.startUsernameRoutes(camelContext);
        camelContext.startRoute("to-paginationBean");
        camelContext.startRoute("to-sortBean");
        camelContext.startRoute("workload-summary-workloads-report-get");
        Map<String, Object> variables = new HashMap<>();
        Long one = 1L;
        variables.put("id", one);
        String orderBy = "workload";
        variables.put("orderBy", orderBy);
        Boolean orderAsc = true;
        variables.put("orderAsc", orderAsc);

        HttpHeaders headers = new HttpHeaders();
        headers.set(TestUtil.HEADER_RH_IDENTITY, TestUtil.getBase64RHIdentity());
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(camel_context + "report/{id}/workload-summary/workloads?orderBy={orderBy}&orderAsc={orderAsc}", HttpMethod.GET, entity, String.class, variables);

        //Then
        PageBean pageBean = new PageBean(0, 10);
        SortBean sortBean = new SortBean(orderBy, orderAsc);

        verify(workloadService).findByReportAnalysisOwnerAndReportAnalysisId("mrizzi@redhat.com", one, pageBean, sortBean);
        assertThat(response).isNotNull();
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestReportIdWorkloadSummaryFlags_IdParamGiven_ShouldCallFindByAnalysisId() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        TestUtil.startUsernameRoutes(camelContext);
        camelContext.startRoute("to-paginationBean");
        camelContext.startRoute("to-sortBean");
        camelContext.startRoute("workload-summary-flags-report-get");
        Map<String, Object> variables = new HashMap<>();
        Long one = 1L;
        variables.put("id", one);

        HttpHeaders headers = new HttpHeaders();
        headers.set(TestUtil.HEADER_RH_IDENTITY, TestUtil.getBase64RHIdentity());
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(camel_context + "report/{id}/workload-summary/flags", HttpMethod.GET, entity, String.class, variables);

        //Then
        PageBean pageBean = new PageBean(0, 10);
        SortBean sortBean = new SortBean("id", false);

        verify(flagService).findByReportAnalysisOwnerAndReportAnalysisId("mrizzi@redhat.com", one, pageBean, sortBean);
        assertThat(response).isNotNull();
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestReportIdWorkloadSummaryFlags_IdParamGiven_PaginationGiven_ShouldCallFindByAnalysisId() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        TestUtil.startUsernameRoutes(camelContext);
        camelContext.startRoute("to-paginationBean");
        camelContext.startRoute("to-sortBean");
        camelContext.startRoute("workload-summary-flags-report-get");
        Map<String, Object> variables = new HashMap<>();
        Long one = 1L;
        variables.put("id", one);
        int page = 2;
        variables.put("page", page);
        int size = 3;
        variables.put("size", size);

        HttpHeaders headers = new HttpHeaders();
        headers.set(TestUtil.HEADER_RH_IDENTITY, TestUtil.getBase64RHIdentity());
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(camel_context + "report/{id}/workload-summary/flags?page={page}&size={size}", HttpMethod.GET, entity, String.class, variables);

        //Then
        PageBean pageBean = new PageBean(page, size);
        SortBean sortBean = new SortBean("id", false);

        verify(flagService).findByReportAnalysisOwnerAndReportAnalysisId("mrizzi@redhat.com", one, pageBean, sortBean);
        assertThat(response).isNotNull();
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestReportIdWorkloadSummaryFlags_IdParamGiven_SortGiven_ShouldCallFindByAnalysisId() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        TestUtil.startUsernameRoutes(camelContext);
        camelContext.startRoute("to-paginationBean");
        camelContext.startRoute("to-sortBean");
        camelContext.startRoute("workload-summary-flags-report-get");
        Map<String, Object> variables = new HashMap<>();
        Long one = 1L;
        variables.put("id", one);
        String orderBy = "workload";
        variables.put("orderBy", orderBy);
        Boolean orderAsc = true;
        variables.put("orderAsc", orderAsc);

        HttpHeaders headers = new HttpHeaders();
        headers.set(TestUtil.HEADER_RH_IDENTITY, TestUtil.getBase64RHIdentity());
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(camel_context + "report/{id}/workload-summary/flags?orderBy={orderBy}&orderAsc={orderAsc}", HttpMethod.GET, entity, String.class, variables);

        //Then
        PageBean pageBean = new PageBean(0, 10);
        SortBean sortBean = new SortBean(orderBy, orderAsc);

        verify(flagService).findByReportAnalysisOwnerAndReportAnalysisId("mrizzi@redhat.com", one, pageBean, sortBean);
        assertThat(response).isNotNull();
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestEndpoints_NoRHIdentityGiven_ShouldReturnForbidden() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);
        int expectedRestEndpointsTested = 12;
        final AtomicInteger restEndpointsTested = new AtomicInteger(0);

        //When
        camelContext.start();
        TestUtil.startUsernameRoutes(camelContext);
        camelContext.getRoutes().stream()
                .filter(route -> route.getEndpoint() instanceof RestEndpoint)
                .forEach(route -> {
                    try {
                        camelContext.startRoute(route.getId());

                        Map<String, Object> variables = new HashMap<>();
                        Long one = 1L;
                        variables.put("id", one);

                        RestEndpoint restEndpoint = (RestEndpoint) route.getEndpoint();
                        String url = camel_context + restEndpoint.getPath();
                        if (restEndpoint.getUriTemplate() != null) url += restEndpoint.getUriTemplate();
                        ResponseEntity<String> result = restTemplate.exchange(
                                url,
                                HttpMethod.resolve(restEndpoint.getMethod().toUpperCase()),
                                new HttpEntity<>(null, null),
                                String.class,
                                variables);

                        //Then
                        assertThat(result).isNotNull();
                        assertThat(result.getStatusCodeValue()).isEqualByComparingTo(403);
                        assertThat(result.getBody()).isEqualTo("Forbidden");
                        verifyZeroInteractions(analysisService);
                        verifyZeroInteractions(initialSavingsEstimationReportService);
                        verifyZeroInteractions(workloadInventoryReportService);
                        verifyZeroInteractions(workloadSummaryReportService);
                        verifyZeroInteractions(workloadService);
                        verifyZeroInteractions(flagService);
                        restEndpointsTested.incrementAndGet();
                        camelContext.stopRoute(route.getId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        assertThat(restEndpointsTested.get()).isEqualTo(expectedRestEndpointsTested);
        camelContext.stop();
    }
}
