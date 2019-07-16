package org.jboss.xavier.integrations.migrationanalytics.business;

import org.jboss.xavier.analytics.pojo.input.UploadFormInputDataModel;

import java.util.Map;

public interface Calculator {
    String CUSTOMERID = "customerid";
    String FILENAME = "filename";
    String SOURCEPRODUCTINDICATOR = "sourceproductindicator";
    String YEAR_1_HYPERVISORPERCENTAGE = "year1hypervisorpercentage";
    String YEAR_2_HYPERVISORPERCENTAGE = "year2hypervisorpercentage";
    String YEAR_3_HYPERVISORPERCENTAGE = "year3hypervisorpercentage";
    String GROWTHRATEPERCENTAGE = "growthratepercentage";

    UploadFormInputDataModel calculate(String cloudFormsJson, Map<String, Object> headers);

    // It will try to extract the version of the payload from the JSON file, falling back to v1
    String getManifestVersion(String cloudFormsJson);
}
