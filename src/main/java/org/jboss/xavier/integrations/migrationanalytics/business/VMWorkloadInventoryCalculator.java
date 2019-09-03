package org.jboss.xavier.integrations.migrationanalytics.business;

import com.jayway.jsonpath.JsonPath;
import org.apache.commons.lang3.StringUtils;
import org.jboss.xavier.analytics.pojo.input.workload.inventory.VMWorkloadInventoryModel;
import org.jboss.xavier.integrations.route.MainRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class VMWorkloadInventoryCalculator implements Calculator<Collection<VMWorkloadInventoryModel>> {
    private static final String VMPATH = "cloudforms.manifest.{version}.vmworkloadinventory.vmPath";
    private static final String CLUSTERPATH = "cloudforms.manifest.{version}.vmworkloadinventory.clusterPath";
    private static final String DATACENTERPATH = "cloudforms.manifest.{version}.vmworkloadinventory.datacenterPath";
    private static final String PROVIDERPATH = "cloudforms.manifest.{version}.vmworkloadinventory.providerPath";
    private static final String GUESTOSFULLNAMEPATH = "cloudforms.manifest.{version}.vmworkloadinventory.guestOSPath";
    private static final String GUESTOSFULLNAME_FALLBACKPATH = "cloudforms.manifest.{version}.vmworkloadinventory.guestOSFallbackPath";
    private static final String VMNAMEPATH = "cloudforms.manifest.{version}.vmworkloadinventory.vmNamePath";
    private static final String NUMCPUPATH = "cloudforms.manifest.{version}.vmworkloadinventory.numCpuPath";
    private static final String NUMCORESPERSOCKETPATH = "cloudforms.manifest.{version}.vmworkloadinventory.numCoresPerSocketPath";
    private static final String HASRDMDISKPATH = "cloudforms.manifest.{version}.vmworkloadinventory.hasRDMDiskPath";
    private static final String RAMSIZEINBYTES = "cloudforms.manifest.{version}.vmworkloadinventory.ramSizeInBytesPath";
    private static final String NICSPATH = "cloudforms.manifest.{version}.vmworkloadinventory.nicsPath";
    private static final String PRODUCTNAMEPATH = "cloudforms.manifest.{version}.vmworkloadinventory.productNamePath";
    private static final String PRODUCTNAME_FALLBACKPATH = "cloudforms.manifest.{version}.vmworkloadinventory.productNameFallbackPath";
    private static final String DISKSIZEPATH = "cloudforms.manifest.{version}.vmworkloadinventory.diskSizePath";
    private static final String EMSCLUSTERIDPATH = "cloudforms.manifest.{version}.vmworkloadinventory.emsClusterIdPath";
    private static final String VMEMSCLUSTERPATH = "cloudforms.manifest.{version}.vmworkloadinventory.vmEmsClusterPath";
    private static final String VMDISKSFILENAMESPATH = "cloudforms.manifest.{version}.vmworkloadinventory.vmDiskFileNamesPath";
    private static final String SYSTEMSERVICESNAMESPATH = "cloudforms.manifest.{version}.vmworkloadinventory.systemServicesNamesPath";
    private static final String FILESCONTENTPATH = "cloudforms.manifest.{version}.vmworkloadinventory.filesContentPath";
    private static final String FILESCONTENTPATH_FILENAME = "cloudforms.manifest.{version}.vmworkloadinventory.filesContentPathName";
    private static final String FILESCONTENTPATH_CONTENTS = "cloudforms.manifest.{version}.vmworkloadinventory.filesContentPathContents";
    private static final String PRODUCTPATH = "cloudforms.manifest.{version}.vmworkloadinventory.productPath";
    private static final String VERSIONPATH = "cloudforms.manifest.{version}.vmworkloadinventory.versionPath";
    private static final String HOSTNAMEPATH = "cloudforms.manifest.{version}.vmworkloadinventory.hostNamePath";

    @Autowired
    private Environment env;

    private String cloudFormsJson;
    private String manifestVersion;

    @Override
    public Collection<VMWorkloadInventoryModel> calculate(String cloudFormsJson, Map<String, Object> headers) {
        manifestVersion = getManifestVersion(cloudFormsJson);
        this.cloudFormsJson = cloudFormsJson;

        List<Map> vmList = readListValuesFromExpandedEnvVarPath(VMPATH, null);
        return vmList.stream().map(e -> createVMWorkloadInventoryModel(e, Long.parseLong(headers.get(MainRouteBuilder.ANALYSIS_ID).toString()))).collect(Collectors.toList());
    }

    private VMWorkloadInventoryModel createVMWorkloadInventoryModel(Map vmStructMap, Long analysisId) {
        VMWorkloadInventoryModel model = new VMWorkloadInventoryModel();
        model.setProvider(readValueFromExpandedEnvVarPath(PROVIDERPATH, vmStructMap));

        vmStructMap.put("vmEmsCluster", readValueFromExpandedEnvVarPath(VMEMSCLUSTERPATH, vmStructMap));
        vmStructMap.put("ems_cluster_id", readValueFromExpandedEnvVarPath(EMSCLUSTERIDPATH, vmStructMap));
        model.setDatacenter(readValueFromExpandedEnvVarPath(DATACENTERPATH, vmStructMap));

        model.setCluster(readValueFromExpandedEnvVarPath(CLUSTERPATH, vmStructMap));

        model.setVmName(readValueFromExpandedEnvVarPath(VMNAMEPATH, vmStructMap ));
        model.setMemory(readValueFromExpandedEnvVarPath(RAMSIZEINBYTES, vmStructMap, Long.class));
        model.setCpuCores(((Integer)readValueFromExpandedEnvVarPath(NUMCPUPATH, vmStructMap, Integer.class) / (Integer)readValueFromExpandedEnvVarPath(NUMCORESPERSOCKETPATH, vmStructMap, Integer.class)));
        model.setOsProductName(StringUtils.defaultIfEmpty(readValueFromExpandedEnvVarPath(PRODUCTNAMEPATH, vmStructMap), readValueFromExpandedEnvVarPath(PRODUCTNAME_FALLBACKPATH, vmStructMap )));
        model.setGuestOSFullName(StringUtils.defaultIfEmpty(readValueFromExpandedEnvVarPath(GUESTOSFULLNAMEPATH, vmStructMap ), readValueFromExpandedEnvVarPath(GUESTOSFULLNAME_FALLBACKPATH, vmStructMap )));
        model.setHasRdmDisk(readValueFromExpandedEnvVarPath(HASRDMDISKPATH, vmStructMap));

        List<Number> diskSpaceList = readListValuesFromExpandedEnvVarPath(DISKSIZEPATH, vmStructMap);
        model.setDiskSpace(diskSpaceList.stream().filter(e -> e != null).mapToLong(Number::longValue).sum());

        model.setNicsCount(readValueFromExpandedEnvVarPath(NICSPATH, vmStructMap, Integer.class));

        model.setFiles(readMapValuesFromExpandedEnvVarPath(FILESCONTENTPATH, vmStructMap, getExpandedPath(FILESCONTENTPATH_FILENAME, vmStructMap), getExpandedPath(FILESCONTENTPATH_CONTENTS, vmStructMap)));
        model.setSystemServicesNames(readListValuesFromExpandedEnvVarPath(SYSTEMSERVICESNAMESPATH, vmStructMap));
        model.setVmDiskFilenames(readListValuesFromExpandedEnvVarPath(VMDISKSFILENAMESPATH, vmStructMap));

        model.setProduct(readValueFromExpandedEnvVarPath(PRODUCTPATH, vmStructMap));
        model.setVersion(readValueFromExpandedEnvVarPath(VERSIONPATH, vmStructMap));
        model.setHost_name(readValueFromExpandedEnvVarPath(HOSTNAMEPATH, vmStructMap));

        model.setAnalysisId(analysisId);

        return model;
    }

    private Map<String, String> readMapValuesFromExpandedEnvVarPath(String envVarPath, Map vmStructMap, String keyfield, String valuefield) {
        String expandParamsInPath = getExpandedPath(envVarPath, vmStructMap);
        Map<String,String> files = new HashMap<>();
        try {
            List<List<Map>> value = JsonPath.parse(cloudFormsJson).read(expandParamsInPath);
            value.stream().flatMap(Collection::stream).collect(Collectors.toList()).forEach(e-> files.put((String) e.get(keyfield), (String) e.get(valuefield)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return files;
    }

    private <T> T readValueFromExpandedEnvVarPath(String envVarPath, Map vmStructMap, Class type) {
        String expandParamsInPath = getExpandedPath(envVarPath, vmStructMap);

        Object value = JsonPath.parse(cloudFormsJson).read(expandParamsInPath);
        if (value instanceof Collection) {
            value = ((List<T>) value).get(0);
        }
        if (Long.class.isAssignableFrom(type)) {
            value = Long.valueOf(((Number) value).longValue());
        } else if (Integer.class.isAssignableFrom(type)) {
            value = Integer.valueOf(((Number) value).intValue());
        }
        return (T) value;
    }

    private <T> T readValueFromExpandedEnvVarPath(String envVarPath, Map vmStructMap) {
        return readValueFromExpandedEnvVarPath(envVarPath, vmStructMap, Object.class);
    }

    private <T> List<T> readListValuesFromExpandedEnvVarPath(String envVarPath, Map vmStructMap) {
        String expandParamsInPath = getExpandedPath(envVarPath, vmStructMap);

        Object value = JsonPath.parse(cloudFormsJson).read(expandParamsInPath);
        if (value instanceof Collection) {
            return new ArrayList<>((List<T>) value);
        } else {
            return Collections.singletonList((T) value);
        }
    }

    private String getExpandedPath(String envVarPath, Map vmStructMap) {
        String envVarPathWithExpandedVersion = expandVersionInExpression(envVarPath);
        String path = env.getProperty(envVarPathWithExpandedVersion);
        return expandParamsInPath(path, vmStructMap);
    }

    private String expandVersionInExpression(String path) {
        String replace = path.replace("{version}", manifestVersion);
        return replace;
    }

    private String expandParamsInPath(String path, Map vmStructMap) {
        Pattern p = Pattern.compile("\\{[a-zA-Z1-9_]+\\}");
        Matcher m = p.matcher(path);
        while (m.find() && vmStructMap != null) {
            String key = m.group().substring(1, m.group().length() - 1);
            String value = vmStructMap.containsKey(key) ? vmStructMap.get(key).toString() : "";
            path = path.replace(m.group(), value);
        }

        return path;
    }
}
