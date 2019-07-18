package org.jboss.xavier.integrations.migrationanalytics.business;

import org.jboss.xavier.analytics.pojo.input.UploadFormInputDataModel;

import java.util.Map;

public interface Calculator {
    String CUSTOMERID = "org_id";
    String FILENAME = "filename";
    String SOURCEPRODUCTINDICATOR = "sourceproductindicator";
    String YEAR_1_HYPERVISORPERCENTAGE = "percentageOfHypervisorsMigratedOnYear1";
    String YEAR_2_HYPERVISORPERCENTAGE = "percentageOfHypervisorsMigratedOnYear2";
    String YEAR_3_HYPERVISORPERCENTAGE = "percentageOfHypervisorsMigratedOnYear3";
    String GROWTHRATEPERCENTAGE = "yearOverYearGrowthRatePercentage";

    UploadFormInputDataModel calculate(String cloudFormsJson, Map<String, Object> headers);

    // It will try to extract the version of the payload from the JSON file, falling back to v1
    String getManifestVersion(String cloudFormsJson);
}
