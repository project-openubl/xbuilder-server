package org.jboss.xavier.integrations.migrationanalytics.business;

import com.jayway.jsonpath.JsonPath;

import java.util.Map;

public interface Calculator<T> {
    String CUSTOMERID = "org_id";
    String FILENAME = "filename";
    String SOURCEPRODUCTINDICATOR = "sourceproductindicator";
    String YEAR_1_HYPERVISORPERCENTAGE = "percentageOfHypervisorsMigratedOnYear1";
    String YEAR_2_HYPERVISORPERCENTAGE = "percentageOfHypervisorsMigratedOnYear2";
    String YEAR_3_HYPERVISORPERCENTAGE = "percentageOfHypervisorsMigratedOnYear3";
    String GROWTHRATEPERCENTAGE = "yearOverYearGrowthRatePercentage";

    T calculate(String cloudFormsJson, Map<String, Object> headers);

    // It will try to extract the version of the payload from the JSON file, falling back to v1
    default String getManifestVersion(String cloudFormsJson) {
        String versionJsonpath = "$.manifest.manifest.version";

        try {
            return JsonPath.parse(cloudFormsJson).read(versionJsonpath, String.class).replace(".", "_");
        } catch (Exception e) {
            return "0";
        }
    }
}
