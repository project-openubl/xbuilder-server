package org.jboss.xavier.integrations.route;

import org.apache.camel.CamelContext;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.apache.camel.test.spring.UseAdviceWith;
import org.jboss.xavier.Application;
import org.jboss.xavier.analytics.pojo.output.AnalysisModel;
import org.jboss.xavier.integrations.jpa.service.AnalysisService;
import org.jboss.xavier.integrations.jpa.service.InitialSavingsEstimationReportService;
import org.jboss.xavier.integrations.jpa.service.WorkloadInventoryReportService;
import org.jboss.xavier.integrations.route.model.PageBean;
import org.jboss.xavier.integrations.route.model.SortBean;
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
import java.util.HashMap;
import java.util.Map;

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

    @SpyBean
    private AnalysisService analysisService;

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
        camelContext.startRoute("reports-get-all");
        restTemplate.getForEntity(camel_context + "report", String.class);

        //Then
        verify(analysisService).findReports(0, 10);
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestReport_PageAndSizeParamGiven_ShouldCallFindReports() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        camelContext.startRoute("reports-get-all");
        Map<String, Object> variables = new HashMap<>();
        int page = 2;
        variables.put("page", page);
        int size = 3;
        variables.put("size", size);
        restTemplate.getForEntity(camel_context + "report?page={page}&size={size}", String.class, variables);

        //Then
        verify(analysisService).findReports(page, size);
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestReport_FilterTextPageAndSizeParamGiven_ShouldCallFindReports() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        camelContext.startRoute("reports-get-all");
        Map<String, Object> variables = new HashMap<>();
        int page = 2;
        variables.put("page", page);
        int size = 3;
        variables.put("size", size);
        String filterText = "my report name which I'm searching";
        variables.put("filterText", filterText);
        restTemplate.getForEntity(camel_context + "report?page={page}&size={size}&filterText={filterText}", String.class, variables);

        //Then
        verify(analysisService).findReports(filterText, page, size);
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestReportId_IdParamGiven_ShouldCallFindById() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        camelContext.startRoute("report-get-details");
        Map<String, Long> variables = new HashMap<>();
        Long one = 1L;
        variables.put("id", one);
        restTemplate.getForEntity(camel_context + "report/{id}", String.class, variables);

        //Then
        verify(analysisService).findById(one);
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestReportIdInitialSavingsEstimation_IdParamGiven_ShouldCallFindOneByAnalysisId() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        camelContext.startRoute("reports-get-details");
        Map<String, Object> variables = new HashMap<>();
        Long one = 1L;
        variables.put("id", one);
        restTemplate.getForEntity(camel_context + "report/{id}/initial-saving-estimation", String.class, variables);

        //Then
        verify(initialSavingsEstimationReportService).findOneByAnalysisId(one);
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestReportIdWorkloadInventory_IdParamGiven_PageParamGiven_SizeParamGiven_ShouldCallFindByAnalysisId() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        camelContext.startRoute("to-paginationBean");
        camelContext.startRoute("to-sortBean");
        camelContext.startRoute("workload-inventory-report-get-details");
        Map<String, Object> variables = new HashMap<>();
        Long one = 1L;
        variables.put("id", one);
        int page = 2;
        variables.put("page", page);
        int size = 3;
        variables.put("size", size);
        restTemplate.getForEntity(camel_context + "report/{id}/workload-inventory?page={page}&size={size}", String.class, variables);

        //Then
        PageBean pageBean = new PageBean(page, size);
        SortBean sortBean = new SortBean("id", false);

        verify(workloadInventoryReportService).findByAnalysisId(one, pageBean, sortBean);
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestReportIdWorkloadInventory_IdParamGiven_ShouldCallFindByAnalysisId() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        camelContext.startRoute("to-paginationBean");
        camelContext.startRoute("to-sortBean");
        camelContext.startRoute("workload-inventory-report-get-details");
        Map<String, Object> variables = new HashMap<>();
        Long one = 1L;
        variables.put("id", one);
        restTemplate.getForEntity(camel_context + "report/{id}/workload-inventory", String.class, variables);

        //Then
        PageBean pageBean = new PageBean(0, 10);
        SortBean sortBean = new SortBean("id", false);

        verify(workloadInventoryReportService).findByAnalysisId(one, pageBean, sortBean);
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestReportIdWorkloadInventory_IdParamGiven_SortParamGiven_ShouldCallFindByAnalysisId() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        camelContext.startRoute("to-paginationBean");
        camelContext.startRoute("to-sortBean");
        camelContext.startRoute("workload-inventory-report-get-details");
        Map<String, Object> variables = new HashMap<>();
        Long one = 1L;
        variables.put("id", one);
        String orderBy = "vmName";
        variables.put("orderBy", orderBy);
        Boolean orderAsc = true;
        variables.put("orderAsc", orderAsc);
        restTemplate.getForEntity(camel_context + "report/{id}/workload-inventory?orderBy={orderBy}&orderAsc={orderAsc}", String.class, variables);

        //Then
        PageBean pageBean = new PageBean(0, 10);
        SortBean sortBean = new SortBean(orderBy, orderAsc);

        verify(workloadInventoryReportService).findByAnalysisId(one, pageBean, sortBean);
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestReportId_IdParamGiven_AndIdNotExists_ShouldReturnNotFount404Status() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        Long one = 1L;
        when(analysisService.findById(one)).thenReturn(null);

        //When
        camelContext.start();
        camelContext.startRoute("report-delete");
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", one);
        ResponseEntity<String> response = restTemplate.exchange(camel_context + "report/{id}" , HttpMethod.DELETE, null, String.class, variables);

        //Then
        Assert.assertEquals(response.getStatusCodeValue(), HttpServletResponse.SC_NOT_FOUND);
        verify(analysisService).findById(one);
        verify(analysisService, never()).deleteById(one);
        camelContext.stop();
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestReportId_IdParamGiven_AndIdExists_ShouldCallDeleteById() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        Long one = 1L;
        when(analysisService.findById(one)).thenReturn(new AnalysisModel());
        doNothing().when(analysisService).deleteById(one);

        //When
        camelContext.start();
        camelContext.startRoute("report-delete");
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", one);
        ResponseEntity<String> response = restTemplate.exchange(camel_context + "report/{id}" , HttpMethod.DELETE, null, String.class, variables);

        //Then
        Assert.assertEquals(response.getStatusCodeValue(), HttpServletResponse.SC_NO_CONTENT);
        Assert.assertNull(response.getBody());
        verify(analysisService).findById(one);
        verify(analysisService).deleteById(one);
        camelContext.stop();
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestReportIdWorkloadInventory_IdParamGiven_ShouldCallFindByAnalysisIdAndReturnCsv() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        camelContext.startRoute("workload-inventory-report-get-details-as-csv");
        camelContext.startRoute("workload-inventory-report-model-to-csv");
        Map<String, Object> variables = new HashMap<>();
        Long one = 1L;
        variables.put("id", one);
        HttpHeaders headers = new HttpHeaders();
        headers.add("whatever", "this header should not be copied");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(camel_context + "report/{id}/workload-inventory/csv" , HttpMethod.GET, entity, String.class, variables);

        //Then
        verify(workloadInventoryReportService).findByAnalysisId(one);
        Assert.assertTrue(response.getHeaders().get("Content-Type").contains("text/csv"));
        Assert.assertTrue(response.getHeaders().get("Content-Disposition").contains("attachment;filename=workloadInventory_1.csv"));
        Assert.assertNull(response.getHeaders().get("whatever"));
        Assert.assertNotNull(response.getBody());
        camelContext.stop();
    }

}
