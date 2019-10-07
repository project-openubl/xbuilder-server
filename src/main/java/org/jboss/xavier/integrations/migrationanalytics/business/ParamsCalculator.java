package org.jboss.xavier.integrations.migrationanalytics.business;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.jboss.xavier.analytics.pojo.input.UploadFormInputDataModel;
import org.jboss.xavier.integrations.migrationanalytics.business.versioning.ManifestVersionService;
import org.jboss.xavier.integrations.route.RouteBuilderExceptionHandler;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Named("calculator")
public class ParamsCalculator implements Calculator<UploadFormInputDataModel> {
    @Inject
    ManifestVersionService manifestVersionService;

    private static Integer calculateHypervisors(Object e, String cpuTotalCoresPath, String cpuCoresPerSocketPath) {
        Map mapa = (Map) e;
        Integer cputotalcores = (Integer) mapa.get(cpuTotalCoresPath);
        Integer cpucorespersocket = (Integer) mapa.get(cpuCoresPerSocketPath);
        return (int) Math.ceil(cputotalcores / (cpucorespersocket * 2.0));
    }

    @Override
    public UploadFormInputDataModel calculate(String cloudFormsJson, Map<String, Object> headers) {
        String payloadVersion = getManifestVersion(cloudFormsJson);

        String hypervisorPath = manifestVersionService.getPropertyWithFallbackVersion(payloadVersion, "hypervisor");
        String cpuTotalCoresPath = manifestVersionService.getPropertyWithFallbackVersion(payloadVersion, "hypervisor.cpuTotalCoresPath");
        String cpuCoresPerSocketPath = manifestVersionService.getPropertyWithFallbackVersion(payloadVersion, "hypervisor.cpuCoresPerSocketPath");
        String totalSpacePath = manifestVersionService.getPropertyWithFallbackVersion(payloadVersion, "totalSpacePath");

        // Calculations
        Integer numberofhypervisors = ((JSONArray) JsonPath.read(cloudFormsJson, hypervisorPath)).stream().map(e -> calculateHypervisors(e, cpuTotalCoresPath, cpuCoresPerSocketPath)).mapToInt(Integer::intValue).sum();
        Long totalspace = ((List<Number>) JsonPath.parse(cloudFormsJson).read(totalSpacePath)).stream().mapToLong(Number::longValue).sum();


        // User properties
        String customerid = StringUtils.defaultString((String) headers.get(Calculator.CUSTOMERID));
        String filename = headers.get(Calculator.FILENAME).toString();
//        int sourceproductindicator = Integer.parseInt(headers.get(Calculator.SOURCEPRODUCTINDICATOR) != null ? headers.get(Calculator.SOURCEPRODUCTINDICATOR).toString() : "0");
        double year1hypervisorpercentage = Double.parseDouble(headers.get(Calculator.YEAR_1_HYPERVISORPERCENTAGE) != null ? headers.get(Calculator.YEAR_1_HYPERVISORPERCENTAGE).toString() : "0") / 100;
        double year2hypervisorpercentage = Double.parseDouble(headers.get(Calculator.YEAR_2_HYPERVISORPERCENTAGE) != null ? headers.get(Calculator.YEAR_2_HYPERVISORPERCENTAGE).toString() : "0") / 100;
        double year3hypervisorpercentage = Double.parseDouble(headers.get(Calculator.YEAR_3_HYPERVISORPERCENTAGE) != null ? headers.get(Calculator.YEAR_3_HYPERVISORPERCENTAGE).toString() : "0") / 100;
        double growthratepercentage = Double.parseDouble(headers.get(Calculator.GROWTHRATEPERCENTAGE) != null ? headers.get(Calculator.GROWTHRATEPERCENTAGE).toString() : "0") / 100;

        // Calculated and enriched model
        return new UploadFormInputDataModel(customerid, filename, numberofhypervisors, totalspace,
                  null, year1hypervisorpercentage,
                  year2hypervisorpercentage,
                  year3hypervisorpercentage, growthratepercentage,
                  Long.parseLong(headers.get(RouteBuilderExceptionHandler.ANALYSIS_ID).toString()));
    }
}
