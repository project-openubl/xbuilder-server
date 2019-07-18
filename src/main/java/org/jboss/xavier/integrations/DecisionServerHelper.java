/**
 *  Copyright 2005-2016 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package org.jboss.xavier.integrations;

import org.jboss.xavier.analytics.pojo.input.UploadFormInputDataModel;
import org.jboss.xavier.analytics.pojo.output.AnalysisModel;
import org.jboss.xavier.analytics.pojo.output.InitialSavingsEstimationReportModel;
import org.jboss.xavier.integrations.migrationanalytics.output.ReportDataModel;
import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.kie.server.api.model.KieServiceResponse;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
public class DecisionServerHelper {

    /** The random. */
    private final Random random = new Random();

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public BatchExecutionCommand createMigrationAnalyticsCommand(Object inputDataModel) {
        return generateCommands(inputDataModel, "get InitialSavingsEstimationReports", "kiesession0");
    }

    public UploadFormInputDataModel createSampleUploadFormInputDataModel()
    {
        UploadFormInputDataModel uploadFormInputDataModel = new UploadFormInputDataModel();
        String customerId = Integer.toString(random.nextInt(99999999));
        uploadFormInputDataModel.setCustomerId(customerId);
        uploadFormInputDataModel.setFileName(format.format(new Date()) + "-" + customerId + "-payload.json");
        uploadFormInputDataModel.setHypervisor(random.nextInt(99999));
        uploadFormInputDataModel.setGrowthRatePercentage(0.05);
        uploadFormInputDataModel.setYear1HypervisorPercentage(0.5);
        uploadFormInputDataModel.setYear2HypervisorPercentage(0.3);
        uploadFormInputDataModel.setYear3HypervisorPercentage(0.15);
        return uploadFormInputDataModel;
    }

    private BatchExecutionCommand generateCommands(Object insert, String retrieveQueryId, String kiseSessionId)
    {
        List<Command<?>> cmds = new ArrayList<Command<?>>();
        KieCommands commands = KieServices.Factory.get().getCommands();
        cmds.add(commands.newInsert(insert));
        cmds.add(commands.newFireAllRules());
        cmds.add(commands.newQuery("output", retrieveQueryId));
        return commands.newBatchExecution(cmds, kiseSessionId);
    }

    public ReportDataModel extractReports(KieServiceResponse<ExecutionResults> response) {
        ExecutionResults res = response.getResult();
        ReportDataModel report = null;
        if (res != null) {
            QueryResults queryResults = (QueryResults) res.getValue("output");
            for (QueryResultsRow queryResult : queryResults) {
                report = (ReportDataModel) queryResult.get("report");
                break;
            }
        }

        return report;
    }

    public InitialSavingsEstimationReportModel extractInitialSavingsEstimationReportModel(KieServiceResponse<ExecutionResults> response) {
        ExecutionResults res = response.getResult();
        InitialSavingsEstimationReportModel report = null;
        if (res != null) {
            QueryResults queryResults = (QueryResults) res.getValue("output");
            for (QueryResultsRow queryResult : queryResults) {
                report = (InitialSavingsEstimationReportModel) queryResult.get("report");
                break;
            }
        }
        report.getEnvironmentModel().setReport(report);
        report.getSourceCostsModel().setReport(report);
        report.getSourceRampDownCostsModel().setReport(report);
        report.getRhvRampUpCostsModel().setReport(report);
        report.getRhvYearByYearCostsModel().setReport(report);
        report.getRhvSavingsModel().setReport(report);
        /*report.getRhvAdditionalContainerCapacityModel().setReport(report);
        report.getRhvOrderFormModel().setReport(report);*/
        return report;
    }

    public AnalysisModel createSampleAnalysisModel(KieServiceResponse<ExecutionResults> response)
    {
        AnalysisModel analysis = new AnalysisModel();
        analysis.setStatus("CREATED");
        InitialSavingsEstimationReportModel initialSavingsEstimationReport = extractInitialSavingsEstimationReportModel(response);
        analysis.setInitialSavingsEstimationReportModel(initialSavingsEstimationReport);
        initialSavingsEstimationReport.setAnalysis(analysis);
        analysis.setPayloadName(initialSavingsEstimationReport.getFileName());
        analysis.setReportName("Report Name");
        analysis.setReportDescription("Report Description");
        return analysis;
    }

}
