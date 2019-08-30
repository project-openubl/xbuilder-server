package org.jboss.xavier.integrations.route;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.UseAdviceWith;
import org.jboss.xavier.Application;
import org.jboss.xavier.analytics.pojo.input.workload.inventory.VMWorkloadInventoryModel;
import org.jboss.xavier.analytics.pojo.output.AnalysisModel;
import org.jboss.xavier.analytics.pojo.output.workload.inventory.WorkloadInventoryReportModel;
import org.jboss.xavier.analytics.pojo.output.workload.summary.*;
import org.jboss.xavier.integrations.jpa.repository.AnalysisRepository;
import org.jboss.xavier.integrations.jpa.repository.FlagRepository;
import org.jboss.xavier.integrations.jpa.repository.WorkloadInventoryReportRepository;
import org.jboss.xavier.integrations.jpa.repository.WorkloadRepository;
import org.jboss.xavier.integrations.jpa.repository.WorkloadSummaryReportRepository;
import org.jboss.xavier.integrations.route.model.PageBean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.inject.Inject;
import java.util.*;
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

        final AnalysisModel analysisModel = analysisRepository.save(new AnalysisModel());
        analysisId = analysisModel.getId();
        IntStream.range(0, collectionSize).forEach(value -> {
            WorkloadInventoryReportModel workloadInventoryReportModel = new WorkloadInventoryReportModel();
            workloadInventoryReportModel.setAnalysis(analysisModel);
            workloadInventoryReportModel.setProvider("Provider" + (value % 2));
            workloadInventoryReportModel.setCluster("Cluster" + (value % 3));
            workloadInventoryReportModel.setCpuCores(value % 4);
            workloadInventoryReportModel.setComplexity(complexities[value]);
            workloadInventoryReportModel.setRecommendedTargetsIMS(recommendedTargetsIMS.get(value));
            workloadInventoryReportModel.setOsName("OSName" + (value % 2));
            workloadInventoryReportModel.setWorkloads(new HashSet<>(Arrays.asList("Workload" + (value % 2), "Workload" + (value % 3))));
            workloadInventoryReportModel.setFlagsIMS(new HashSet<>(Arrays.asList("Flag" + (value % 2), "Flag" + (value % 3))));

            System.out.println("Saved WorkloadInventoryReportModel with ID #" + workloadInventoryReportRepository.save(workloadInventoryReportModel).getId());
        });
    }

    @Test
    public void DirectCalculateVMWorkloadInventoryModel_ShouldPersistWorkloadSummaryReportModel() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        camelContext.startRoute("calculate-workloadsummaryreportmodel");

        Collection<VMWorkloadInventoryModel> vmWorkloadInventoryModels = new ArrayList<>(collectionSize);
        IntStream.range(0, collectionSize).forEach(value -> vmWorkloadInventoryModels.add(new VMWorkloadInventoryModel()));

        Map<String, String> metadata = new HashMap<>();
        metadata.put(MainRouteBuilder.ANALYSIS_ID, analysisId.toString());
        Map<String, Object> headers = new HashMap<>();
        headers.put("MA_metadata", metadata);

        Exchange message = camelContext.createProducerTemplate().request("direct:calculate-workloadsummaryreportmodel", exchange -> {
            exchange.getIn().setBody(vmWorkloadInventoryModels);
            exchange.getIn().setHeaders(headers);
        });

        //Then
        AnalysisModel analysisModel = analysisRepository.findOne(analysisId);
        Assert.assertNotNull(analysisModel);
        WorkloadSummaryReportModel workloadSummaryReportModel = analysisModel.getWorkloadSummaryReportModels();
        Assert.assertNotNull(workloadSummaryReportModel);
        workloadSummaryReportModel = workloadSummaryReportRepository.findOne(workloadSummaryReportModel.getId());
        List<SummaryModel> summaryModels = workloadSummaryReportModel.getSummaryModels();
        Assert.assertNotNull(summaryModels);
        Assert.assertEquals("Provider0", summaryModels.get(0).getProvider());
        Assert.assertEquals("Provider1", summaryModels.get(1).getProvider());
        Assert.assertEquals(3, summaryModels.get(0).getClusters(), 0);
        Assert.assertEquals(3, summaryModels.get(1).getClusters(), 0);
        Assert.assertEquals(4L, summaryModels.get(0).getSockets(), 0);
        Assert.assertEquals(10L, summaryModels.get(1).getSockets(), 0);
        Assert.assertEquals(3, summaryModels.get(0).getVms(), 0);
        Assert.assertEquals(3, summaryModels.get(1).getVms(), 0);

        camelContext.stop();
    }

    @Test
    public void DirectCalculateVMWorkloadInventoryModel_ShouldPersistWorkloadComplexityReportModel() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        camelContext.startRoute("calculate-workloadsummaryreportmodel");

        Collection<VMWorkloadInventoryModel> vmWorkloadInventoryModels = new ArrayList<>(collectionSize);
        IntStream.range(0, collectionSize).forEach(value -> vmWorkloadInventoryModels.add(new VMWorkloadInventoryModel()));

        Map<String, String> metadata = new HashMap<>();
        metadata.put(MainRouteBuilder.ANALYSIS_ID, analysisId.toString());
        Map<String, Object> headers = new HashMap<>();
        headers.put("MA_metadata", metadata);

        Exchange message = camelContext.createProducerTemplate().request("direct:calculate-workloadsummaryreportmodel", exchange -> {
            exchange.getIn().setBody(vmWorkloadInventoryModels);
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

        //When
        camelContext.start();
        camelContext.startRoute("calculate-workloadsummaryreportmodel");

        Collection<VMWorkloadInventoryModel> vmWorkloadInventoryModels = new ArrayList<>(collectionSize);
        IntStream.range(0, collectionSize).forEach(value -> vmWorkloadInventoryModels.add(new VMWorkloadInventoryModel()));

        Map<String, String> metadata = new HashMap<>();
        metadata.put(MainRouteBuilder.ANALYSIS_ID, analysisId.toString());
        Map<String, Object> headers = new HashMap<>();
        headers.put("MA_metadata", metadata);

        Exchange message = camelContext.createProducerTemplate().request("direct:calculate-workloadsummaryreportmodel", exchange -> {
            exchange.getIn().setBody(vmWorkloadInventoryModels);
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

        //When
        camelContext.start();
        camelContext.startRoute("calculate-workloadsummaryreportmodel");

        Collection<VMWorkloadInventoryModel> vmWorkloadInventoryModels = new ArrayList<>(collectionSize);
        IntStream.range(0, collectionSize).forEach(value -> vmWorkloadInventoryModels.add(new VMWorkloadInventoryModel()));

        Map<String, String> metadata = new HashMap<>();
        metadata.put(MainRouteBuilder.ANALYSIS_ID, analysisId.toString());
        Map<String, Object> headers = new HashMap<>();
        headers.put("MA_metadata", metadata);

        Exchange message = camelContext.createProducerTemplate().request("direct:calculate-workloadsummaryreportmodel", exchange -> {
            exchange.getIn().setBody(vmWorkloadInventoryModels);
            exchange.getIn().setHeaders(headers);
        });

        //Then
        AnalysisModel analysisModel = analysisRepository.findOne(analysisId);
        Assert.assertNotNull(analysisModel);
        WorkloadSummaryReportModel workloadSummaryReportModel = analysisModel.getWorkloadSummaryReportModels();
        Assert.assertNotNull(workloadSummaryReportModel);

        List<WorkloadModel> workloads = workloadRepository.findByReportAnalysisId(analysisId, new PageRequest(0, 100)).getContent();
        Assert.assertNotNull(workloads);
        Assert.assertEquals(6, workloads.size());
        Assert.assertEquals("Workload0", workloads.get(0).getWorkload());
        Assert.assertEquals("OSName0", workloads.get(0).getOsName());
        Assert.assertEquals(3, (int) workloads.get(0).getClusters());
        Assert.assertEquals(3, (int) workloads.get(0).getVms());

        camelContext.stop();
    }

    @Test
    public void DirectCalculateVMWorkloadInventoryModel_ShouldPersistWorkloadFlagReportModel() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        camelContext.startRoute("calculate-workloadsummaryreportmodel");

        Collection<VMWorkloadInventoryModel> vmWorkloadInventoryModels = new ArrayList<>(collectionSize);
        IntStream.range(0, collectionSize).forEach(value -> vmWorkloadInventoryModels.add(new VMWorkloadInventoryModel()));

        Map<String, String> metadata = new HashMap<>();
        metadata.put(MainRouteBuilder.ANALYSIS_ID, analysisId.toString());
        Map<String, Object> headers = new HashMap<>();
        headers.put("MA_metadata", metadata);

        Exchange message = camelContext.createProducerTemplate().request("direct:calculate-workloadsummaryreportmodel", exchange -> {
            exchange.getIn().setBody(vmWorkloadInventoryModels);
            exchange.getIn().setHeaders(headers);
        });

        //Then
        AnalysisModel analysisModel = analysisRepository.findOne(analysisId);
        Assert.assertNotNull(analysisModel);
        WorkloadSummaryReportModel workloadSummaryReportModel = analysisModel.getWorkloadSummaryReportModels();
        Assert.assertNotNull(workloadSummaryReportModel);

        List<FlagModel> flags = flagRepository.findByReportAnalysisId(analysisId, new PageRequest(0, 100)).getContent();
        Assert.assertNotNull(flags);
        Assert.assertEquals(6, flags.size());
        Assert.assertEquals("Flag0", flags.get(0).getFlag());
        Assert.assertEquals("OSName0", flags.get(0).getOsName());
        Assert.assertEquals(3, (int) flags.get(0).getClusters());
        Assert.assertEquals(3, (int) flags.get(0).getVms());

        camelContext.stop();
    }
}
