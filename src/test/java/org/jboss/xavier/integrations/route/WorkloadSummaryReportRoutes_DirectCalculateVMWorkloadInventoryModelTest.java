package org.jboss.xavier.integrations.route;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.UseAdviceWith;
import org.jboss.xavier.Application;
import org.jboss.xavier.analytics.pojo.output.AnalysisModel;
import org.jboss.xavier.analytics.pojo.output.workload.inventory.WorkloadInventoryReportModel;
import org.jboss.xavier.analytics.pojo.output.workload.summary.ComplexityModel;
import org.jboss.xavier.analytics.pojo.output.workload.summary.FlagModel;
import org.jboss.xavier.analytics.pojo.output.workload.summary.RecommendedTargetsIMSModel;
import org.jboss.xavier.analytics.pojo.output.workload.summary.ScanRunModel;
import org.jboss.xavier.analytics.pojo.output.workload.summary.SummaryModel;
import org.jboss.xavier.analytics.pojo.output.workload.summary.WorkloadModel;
import org.jboss.xavier.analytics.pojo.output.workload.summary.WorkloadSummaryReportModel;
import org.jboss.xavier.analytics.pojo.output.workload.summary.WorkloadsDetectedOSTypeModel;
import org.jboss.xavier.integrations.jpa.repository.AnalysisRepository;
import org.jboss.xavier.integrations.jpa.repository.FlagRepository;
import org.jboss.xavier.integrations.jpa.repository.ScanRunRepository;
import org.jboss.xavier.integrations.jpa.repository.WorkloadInventoryReportRepository;
import org.jboss.xavier.integrations.jpa.repository.WorkloadRepository;
import org.jboss.xavier.integrations.jpa.repository.WorkloadSummaryReportRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@UseAdviceWith // Disables automatic start of Camel context
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
public class WorkloadSummaryReportRoutes_DirectCalculateVMWorkloadInventoryModelTest {
    @Inject
    CamelContext camelContext;

    @Autowired
    WorkloadInventoryReportRepository workloadInventoryReportRepository;

    @Autowired
    AnalysisRepository analysisRepository;

    @Autowired
    WorkloadRepository workloadRepository;

    @Autowired
    FlagRepository flagRepository;

    @Autowired
    ScanRunRepository scanRunRepository;

    @Autowired
    WorkloadSummaryReportRepository workloadSummaryReportRepository;

    private Long analysisId;
    private int collectionSize = 6;

    @Before
    public void setup()
    {
        String[] complexities = new String[]{"Easy", "Easy", "Medium", "Hard", "Unknown", null};

        List<Set<String>> recommendedTargetsIMS = new ArrayList<>(Arrays.asList(
                new HashSet<>(Arrays.asList("rhv", "osp", "convert2rhel")),
                new HashSet<>(Arrays.asList("rhv", "osp")),
                new HashSet<>(Arrays.asList("osp", "convert2rhel")),
                new HashSet<>(Collections.singletonList("convert2rhel")),
                new HashSet<>(Collections.singletonList("other")),
                new HashSet<>()
        ));

        AnalysisModel initialModel = new AnalysisModel();
        initialModel.setOwner("user@name");
        final AnalysisModel analysisModel = analysisRepository.save(initialModel);
        analysisId = analysisModel.getId();
        IntStream.range(0, collectionSize).forEach(value -> {
            WorkloadInventoryReportModel workloadInventoryReportModel = new WorkloadInventoryReportModel();
            workloadInventoryReportModel.setAnalysis(analysisModel);
            workloadInventoryReportModel.setProvider("Provider" + (value % 2));
            workloadInventoryReportModel.setProduct("Product" + (value % 2));
            workloadInventoryReportModel.setVersion("Version" + (value % 2));
            workloadInventoryReportModel.setHost_name("HostName" + (value % 3));
            workloadInventoryReportModel.setCluster("Cluster" + (value % 3));
            workloadInventoryReportModel.setCpuCores(value % 4);
            workloadInventoryReportModel.setComplexity(complexities[value]);
            workloadInventoryReportModel.setRecommendedTargetsIMS(recommendedTargetsIMS.get(value));
            workloadInventoryReportModel.setOsName("OSName" + (value % 2));
            workloadInventoryReportModel.setWorkloads(new HashSet<>(Arrays.asList("Workload" + (value % 2), "Workload" + (value % 3))));
            workloadInventoryReportModel.setFlagsIMS(new HashSet<>(Arrays.asList("Flag" + (value % 2), "Flag" + (value % 3))));
            workloadInventoryReportModel.setSsaEnabled(value % 2 == 0);

            System.out.println("Saved WorkloadInventoryReportModel with ID #" + workloadInventoryReportRepository.save(workloadInventoryReportModel).getId());
        });
    }

    @Test
    public void DirectCalculateVMWorkloadInventoryModel_ShouldPersistWorkloadSummaryReportModel() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);
        String fileName = "cloudforms-export-v1-multiple-files.tar.gz";

        //When
        camelContext.start();
        camelContext.startRoute("calculate-workloadsummaryreportmodel");

        Map<String, String> metadata = new HashMap<>();
        metadata.put(MainRouteBuilder.ANALYSIS_ID, analysisId.toString());
        Map<String, Object> headers = new HashMap<>();
        headers.put(MainRouteBuilder.MA_METADATA, metadata);
        headers.put(MainRouteBuilder.USERNAME, "user@name");

        Exchange message = camelContext.createProducerTemplate().request("direct:calculate-workloadsummaryreportmodel", exchange -> {
            exchange.getIn().setBody(getClass().getClassLoader().getResourceAsStream(fileName));
            exchange.getIn().setHeaders(headers);
        });

        //Then
        AnalysisModel analysisModel = analysisRepository.findOne(analysisId);
        Assert.assertNotNull(analysisModel);
        WorkloadSummaryReportModel workloadSummaryReportModel = analysisModel.getWorkloadSummaryReportModels();
        Assert.assertNotNull(workloadSummaryReportModel);
        workloadSummaryReportModel = workloadSummaryReportRepository.findOne(workloadSummaryReportModel.getId());
        List<SummaryModel> summaryModels = new ArrayList<>(workloadSummaryReportModel.getSummaryModels());

        Assert.assertNotNull(summaryModels);
        Assert.assertEquals(2, summaryModels.size());

        Map<Long, SummaryModel> summaryModelMap = summaryModels.stream().collect(Collectors.toMap(SummaryModel::getId, s -> s));
        Assert.assertEquals("Provider0", summaryModelMap.get(1L).getProvider());
        Assert.assertEquals("Provider1", summaryModelMap.get(2L).getProvider());
        Assert.assertEquals("Product0", summaryModelMap.get(1L).getProduct());
        Assert.assertEquals("Product1", summaryModelMap.get(2L).getProduct());
        Assert.assertEquals("Version0", summaryModelMap.get(1L).getVersion());
        Assert.assertEquals("Version1", summaryModelMap.get(2L).getVersion());
        Assert.assertEquals(3, summaryModelMap.get(1L).getHosts(), 0);
        Assert.assertEquals(3, summaryModelMap.get(2L).getHosts(), 0);
        Assert.assertEquals(3, summaryModelMap.get(1L).getClusters(), 0);
        Assert.assertEquals(3, summaryModelMap.get(2L).getClusters(), 0);
        Assert.assertEquals(2L, summaryModelMap.get(1L).getSockets(), 0);
        Assert.assertEquals(5L, summaryModelMap.get(2L).getSockets(), 0);
        Assert.assertEquals(3, summaryModelMap.get(1L).getVms(), 0);
        Assert.assertEquals(3, summaryModelMap.get(2L).getVms(), 0);

        camelContext.stop();
    }

    @Test
    public void DirectCalculateVMWorkloadInventoryModel_ShouldPersistWorkloadComplexityReportModel() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);
        String fileName = "cloudforms-export-v1-multiple-files.tar.gz";

        //When
        camelContext.start();
        camelContext.startRoute("calculate-workloadsummaryreportmodel");

        Map<String, String> metadata = new HashMap<>();
        metadata.put(MainRouteBuilder.ANALYSIS_ID, analysisId.toString());
        Map<String, Object> headers = new HashMap<>();
        headers.put(MainRouteBuilder.MA_METADATA, metadata);
        headers.put(MainRouteBuilder.USERNAME, "user@name");

        Exchange message = camelContext.createProducerTemplate().request("direct:calculate-workloadsummaryreportmodel", exchange -> {
            exchange.getIn().setBody(getClass().getClassLoader().getResourceAsStream(fileName));
            exchange.getIn().setHeaders(headers);
        });

        //Then
        AnalysisModel analysisModel = analysisRepository.findOne(analysisId);
        Assert.assertNotNull(analysisModel);
        WorkloadSummaryReportModel workloadSummaryReportModel = analysisModel.getWorkloadSummaryReportModels();
        Assert.assertNotNull(workloadSummaryReportModel);
        workloadSummaryReportModel = workloadSummaryReportRepository.findOne(workloadSummaryReportModel.getId());
        ComplexityModel complexityModel = workloadSummaryReportModel.getComplexityModel();
        Assert.assertNotNull(complexityModel);

        Assert.assertEquals(2, (int) complexityModel.getEasy());
        Assert.assertEquals(1, (int) complexityModel.getMedium());
        Assert.assertEquals(1, (int) complexityModel.getHard());
        Assert.assertEquals(2, (int) complexityModel.getUnknown());

        camelContext.stop();
    }

    @Test
    public void DirectCalculateVMWorkloadInventoryModel_ShouldPersistWorkloadRecommendedTargetIMSReportModel() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);
        String fileName = "cloudforms-export-v1-multiple-files.tar.gz";

        //When
        camelContext.start();
        camelContext.startRoute("calculate-workloadsummaryreportmodel");

        Map<String, String> metadata = new HashMap<>();
        metadata.put(MainRouteBuilder.ANALYSIS_ID, analysisId.toString());
        Map<String, Object> headers = new HashMap<>();
        headers.put(MainRouteBuilder.MA_METADATA, metadata);
        headers.put(MainRouteBuilder.USERNAME, "user@name");

        Exchange message = camelContext.createProducerTemplate().request("direct:calculate-workloadsummaryreportmodel", exchange -> {
            exchange.getIn().setBody(getClass().getClassLoader().getResourceAsStream(fileName));
            exchange.getIn().setHeaders(headers);
        });

        //Then
        AnalysisModel analysisModel = analysisRepository.findOne(analysisId);
        Assert.assertNotNull(analysisModel);
        WorkloadSummaryReportModel workloadSummaryReportModel = analysisModel.getWorkloadSummaryReportModels();
        Assert.assertNotNull(workloadSummaryReportModel);
        workloadSummaryReportModel = workloadSummaryReportRepository.findOne(workloadSummaryReportModel.getId());
        RecommendedTargetsIMSModel recommendedTargetsIMS = workloadSummaryReportModel.getRecommendedTargetsIMSModel();
        Assert.assertNotNull(recommendedTargetsIMS);
        Assert.assertEquals(6, (int) recommendedTargetsIMS.getTotal());
        Assert.assertEquals(2, (int) recommendedTargetsIMS.getRhv());
        Assert.assertEquals(3, (int) recommendedTargetsIMS.getOsp());
        Assert.assertEquals(3, (int) recommendedTargetsIMS.getRhel());

        camelContext.stop();
    }

    @Test
    public void DirectCalculateVMWorkloadInventoryModel_ShouldPersistWorkloadReportModel() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);
        String fileName = "cloudforms-export-v1-multiple-files.tar.gz";

        //When
        camelContext.start();
        camelContext.startRoute("calculate-workloadsummaryreportmodel");

        Map<String, String> metadata = new HashMap<>();
        metadata.put(MainRouteBuilder.ANALYSIS_ID, analysisId.toString());
        Map<String, Object> headers = new HashMap<>();
        headers.put(MainRouteBuilder.MA_METADATA, metadata);
        headers.put(MainRouteBuilder.USERNAME, "user@name");

        Exchange message = camelContext.createProducerTemplate().request("direct:calculate-workloadsummaryreportmodel", exchange -> {
            exchange.getIn().setBody(getClass().getClassLoader().getResourceAsStream(fileName));
            exchange.getIn().setHeaders(headers);
        });

        //Then
        AnalysisModel analysisModel = analysisRepository.findOne(analysisId);
        Assert.assertNotNull(analysisModel);
        WorkloadSummaryReportModel workloadSummaryReportModel = analysisModel.getWorkloadSummaryReportModels();
        Assert.assertNotNull(workloadSummaryReportModel);

        List<WorkloadModel> workloads = workloadRepository.findByReportAnalysisOwnerAndReportAnalysisId("user@name", analysisId, new PageRequest(0, 100)).getContent();
        Assert.assertNotNull(workloads);
        Assert.assertEquals(6, workloads.size());
        Assert.assertEquals("Workload0", workloads.get(0).getWorkload());
        Assert.assertEquals("OSName0", workloads.get(0).getOsName());
        Assert.assertEquals(3, (int) workloads.get(0).getClusters());
        Assert.assertEquals(3, (int) workloads.get(0).getVms());

        workloads = workloadRepository.findByReportAnalysisOwnerAndReportAnalysisId("whatever", analysisId, new PageRequest(0, 100)).getContent();
        Assert.assertNotNull(workloads);
        Assert.assertEquals(0, workloads.size());
        camelContext.stop();
    }

    @Test
    public void DirectCalculateVMWorkloadInventoryModel_ShouldPersistWorkloadFlagReportModel() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);
        String fileName = "cloudforms-export-v1-multiple-files.tar.gz";

        //When
        camelContext.start();
        camelContext.startRoute("calculate-workloadsummaryreportmodel");

        Map<String, String> metadata = new HashMap<>();
        metadata.put(MainRouteBuilder.ANALYSIS_ID, analysisId.toString());
        Map<String, Object> headers = new HashMap<>();
        headers.put(MainRouteBuilder.MA_METADATA, metadata);
        headers.put(MainRouteBuilder.USERNAME, "user@name");

        Exchange message = camelContext.createProducerTemplate().request("direct:calculate-workloadsummaryreportmodel", exchange -> {
            exchange.getIn().setBody(getClass().getClassLoader().getResourceAsStream(fileName));
            exchange.getIn().setHeaders(headers);
        });

        //Then
        AnalysisModel analysisModel = analysisRepository.findOne(analysisId);
        Assert.assertNotNull(analysisModel);
        WorkloadSummaryReportModel workloadSummaryReportModel = analysisModel.getWorkloadSummaryReportModels();
        Assert.assertNotNull(workloadSummaryReportModel);

        List<FlagModel> flags = flagRepository.findByReportAnalysisOwnerAndReportAnalysisId("user@name", analysisId, new PageRequest(0, 100)).getContent();
        Assert.assertNotNull(flags);
        Assert.assertEquals(6, flags.size());
        Assert.assertEquals("Flag0", flags.get(0).getFlag());
        Assert.assertEquals("OSName0", flags.get(0).getOsName());
        Assert.assertEquals(3, (int) flags.get(0).getClusters());
        Assert.assertEquals(3, (int) flags.get(0).getVms());

        flags = flagRepository.findByReportAnalysisOwnerAndReportAnalysisId("whatever", analysisId, new PageRequest(0, 100)).getContent();
        Assert.assertNotNull(flags);
        Assert.assertEquals(0, flags.size());

        camelContext.stop();
    }

    @Test
    public void DirectCalculateVMWorkloadInventoryModel_ShouldPersistWorkloadOSTypeModel() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);
        String fileName = "cloudforms-export-v1-multiple-files.tar.gz";

        //When
        camelContext.start();
        camelContext.startRoute("calculate-workloadsummaryreportmodel");

        Map<String, String> metadata = new HashMap<>();
        metadata.put(MainRouteBuilder.ANALYSIS_ID, analysisId.toString());
        Map<String, Object> headers = new HashMap<>();
        headers.put(MainRouteBuilder.MA_METADATA, metadata);
        headers.put(MainRouteBuilder.USERNAME, "user@name");

        Exchange message = camelContext.createProducerTemplate().request("direct:calculate-workloadsummaryreportmodel", exchange -> {
            exchange.getIn().setBody(getClass().getClassLoader().getResourceAsStream(fileName));
            exchange.getIn().setHeaders(headers);
        });

        //Then
        AnalysisModel analysisModel = analysisRepository.findOne(analysisId);
        Assert.assertNotNull(analysisModel);
        WorkloadSummaryReportModel workloadSummaryReportModel = analysisModel.getWorkloadSummaryReportModels();
        Assert.assertNotNull(workloadSummaryReportModel);
        workloadSummaryReportModel = workloadSummaryReportRepository.findOne(workloadSummaryReportModel.getId());
        Set<WorkloadsDetectedOSTypeModel> workloadsDetectedOSTypeModels = workloadSummaryReportModel.getWorkloadsDetectedOSTypeModels();

        Assert.assertNotNull(workloadsDetectedOSTypeModels);
        Assert.assertEquals(2, workloadsDetectedOSTypeModels.size());

        Integer totalSum = workloadsDetectedOSTypeModels.stream().map(WorkloadsDetectedOSTypeModel::getTotal).reduce(0, (a, b) -> a + b);
        Assert.assertEquals(Integer.valueOf(10), totalSum);

        Map<Long, WorkloadsDetectedOSTypeModel> workloadsDetectedOSTypeMap = workloadsDetectedOSTypeModels.stream().collect(Collectors.toMap(WorkloadsDetectedOSTypeModel::getId, s -> s));
        Assert.assertEquals("OSName0", workloadsDetectedOSTypeMap.get(1L).getOsName());
        Assert.assertEquals("OSName1", workloadsDetectedOSTypeMap.get(2L).getOsName());
        Assert.assertEquals(Integer.valueOf(5), workloadsDetectedOSTypeMap.get(1L).getTotal());
        Assert.assertEquals(Integer.valueOf(5), workloadsDetectedOSTypeMap.get(2L).getTotal());

        camelContext.stop();
    }

    @Test
    public void DirectCalculateVMWorkloadInventoryModel_ShouldPersistScanRunModel() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);
        String fileName = "cloudforms-export-v1-multiple-files.tar.gz";

        //When
        camelContext.start();
        camelContext.startRoute("calculate-workloadsummaryreportmodel");

        Map<String, String> metadata = new HashMap<>();
        metadata.put(MainRouteBuilder.ANALYSIS_ID, analysisId.toString());
        Map<String, Object> headers = new HashMap<>();
        headers.put(MainRouteBuilder.MA_METADATA, metadata);
        headers.put(MainRouteBuilder.USERNAME, "user@name");

        Exchange message = camelContext.createProducerTemplate().request("direct:calculate-workloadsummaryreportmodel", exchange -> {
            exchange.getIn().setBody(getClass().getClassLoader().getResourceAsStream(fileName));
            exchange.getIn().setHeaders(headers);
        });

        //Then
        AnalysisModel analysisModel = analysisRepository.findOne(analysisId);
        Assert.assertNotNull(analysisModel);
        WorkloadSummaryReportModel workloadSummaryReportModel = analysisModel.getWorkloadSummaryReportModels();
        Assert.assertNotNull(workloadSummaryReportModel);
        workloadSummaryReportModel = workloadSummaryReportRepository.findOne(workloadSummaryReportModel.getId());
        Set<ScanRunModel> scanRunModels = workloadSummaryReportModel.getScanRunModels();

        Assert.assertNotNull(scanRunModels);
        Assert.assertEquals(2, scanRunModels.size());

        scanRunModels.stream().filter(model -> model.getId() % 2 == 0).forEach(srm ->
                {
                    Assert.assertEquals("Virt Platform", srm.getType());
                });

        scanRunModels.stream().filter(model -> model.getId() % 2 != 0).forEach(srm ->
        {
            Assert.assertEquals("Virt Platform + SmartState", srm.getType());
        });

        camelContext.stop();
    }
}
