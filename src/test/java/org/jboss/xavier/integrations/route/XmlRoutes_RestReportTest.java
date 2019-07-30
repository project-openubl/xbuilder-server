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
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.anyInt;
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
    public void xmlRouteBuilder_RestReport_SummaryParamGiven_ShouldCallFindReportSummary() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        camelContext.startRoute("reports-get-all");
        Map<String, String> variables = new HashMap<>();
        variables.put("summary", "true");
        restTemplate.getForEntity(camel_context + "report?summary={summary}", String.class, variables);

        //Then
        verify(initialSavingsEstimationReportService).findReportSummary(anyInt(), anyInt());
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestReport_NotSummaryParamGiven_ShouldCallFindReports() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        camelContext.startRoute("reports-get-all");
        Map<String, String> variables = new HashMap<>();
        variables.put("summary", "false");
        restTemplate.getForEntity(camel_context + "report?summary={summary}", String.class, variables);

        //Then
        verify(initialSavingsEstimationReportService).findReports();
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestReportId_IdParamGiven_ShouldCallFindReportSummaryById() throws Exception {
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
        verify(initialSavingsEstimationReportService).findReportSummaryById(one);
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestReportIdInitialSavingsEstimation_IdParamGiven_ShouldCallFindByAnalysisId() throws Exception {
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
        verify(initialSavingsEstimationReportService).findReportDetails(one);
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestReportIdWorkloadInventory_IdParamGiven_PageParamGiven_SizeParamGiven_ShouldCallFindByAnalysisId() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
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
        verify(workloadInventoryReportService).findByAnalysisId(one, page, size);
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_RestReportIdWorkloadInventory_IdParamGiven_ShouldCallFindByAnalysisId() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        camelContext.startRoute("workload-inventory-report-get-details");
        Map<String, Object> variables = new HashMap<>();
        Long one = 1L;
        variables.put("id", one);
        restTemplate.getForEntity(camel_context + "report/{id}/workload-inventory", String.class, variables);

        //Then
        verify(workloadInventoryReportService).findByAnalysisId(one, 0, 10);
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

}
