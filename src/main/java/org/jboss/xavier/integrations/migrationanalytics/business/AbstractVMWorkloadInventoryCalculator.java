package org.jboss.xavier.integrations.migrationanalytics.business;

import com.jayway.jsonpath.DocumentContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AbstractVMWorkloadInventoryCalculator {
    public static final String VMPATH = "cloudforms.manifest.{version}.vmworkloadinventory.vmPath";
    public static final String CLUSTERPATH = "cloudforms.manifest.{version}.vmworkloadinventory.clusterPath";
    public static final String DATACENTERPATH = "cloudforms.manifest.{version}.vmworkloadinventory.datacenterPath";
    public static final String PROVIDERPATH = "cloudforms.manifest.{version}.vmworkloadinventory.providerPath";
    public static final String GUESTOSFULLNAMEPATH = "cloudforms.manifest.{version}.vmworkloadinventory.guestOSPath";
    public static final String GUESTOSFULLNAME_FALLBACKPATH = "cloudforms.manifest.{version}.vmworkloadinventory.guestOSFallbackPath";
    public static final String VMNAMEPATH = "cloudforms.manifest.{version}.vmworkloadinventory.vmNamePath";
    public static final String NUMCPUPATH = "cloudforms.manifest.{version}.vmworkloadinventory.numCpuPath";
    public static final String NUMCORESPERSOCKETPATH = "cloudforms.manifest.{version}.vmworkloadinventory.numCoresPerSocketPath";
    public static final String HASRDMDISKPATH = "cloudforms.manifest.{version}.vmworkloadinventory.hasRDMDiskPath";
    public static final String RAMSIZEINBYTES = "cloudforms.manifest.{version}.vmworkloadinventory.ramSizeInBytesPath";
    public static final String NICSPATH = "cloudforms.manifest.{version}.vmworkloadinventory.nicsPath";
    public static final String PRODUCTNAMEPATH = "cloudforms.manifest.{version}.vmworkloadinventory.productNamePath";
    public static final String PRODUCTNAME_FALLBACKPATH = "cloudforms.manifest.{version}.vmworkloadinventory.productNameFallbackPath";
    public static final String DISKSIZEPATH = "cloudforms.manifest.{version}.vmworkloadinventory.diskSizePath";
    public static final String EMSCLUSTERIDPATH = "cloudforms.manifest.{version}.vmworkloadinventory.emsClusterIdPath";
    public static final String VMEMSCLUSTERPATH = "cloudforms.manifest.{version}.vmworkloadinventory.vmEmsClusterPath";
    public static final String VMDISKSFILENAMESPATH = "cloudforms.manifest.{version}.vmworkloadinventory.vmDiskFileNamesPath";
    public static final String SYSTEMSERVICESNAMESPATH = "cloudforms.manifest.{version}.vmworkloadinventory.systemServicesNamesPath";
    public static final String FILESCONTENTPATH = "cloudforms.manifest.{version}.vmworkloadinventory.filesContentPath";
    public static final String FILESCONTENTPATH_FILENAME = "cloudforms.manifest.{version}.vmworkloadinventory.filesContentPathName";
    public static final String FILESCONTENTPATH_CONTENTS = "cloudforms.manifest.{version}.vmworkloadinventory.filesContentPathContents";
    public static final String PRODUCTPATH = "cloudforms.manifest.{version}.vmworkloadinventory.productPath";
    public static final String VERSIONPATH = "cloudforms.manifest.{version}.vmworkloadinventory.versionPath";
    public static final String HOSTNAMEPATH = "cloudforms.manifest.{version}.vmworkloadinventory.hostNamePath";
    public static final String VMDISKSPATH = "cloudforms.manifest.{version}.vmworkloadinventory.vmDisksPath";

    @Autowired
    protected Environment env;

    protected DocumentContext jsonParsed;
    protected String manifestVersion;

    protected Map<String, String> readMapValuesFromExpandedEnvVarPath(String envVarPath, Map vmStructMap, String keyfield, String valuefield) {
        String expandParamsInPath = getExpandedPath(envVarPath, vmStructMap);
        Map<String,String> files = new HashMap<>();
        try {
            List<List<Map>> value = jsonParsed.read(expandParamsInPath);
            value.stream().flatMap(Collection::stream).collect(Collectors.toList()).forEach(e-> files.put((String) e.get(keyfield), (String) e.get(valuefield)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return files;
    }

    protected <T> T readValueFromExpandedEnvVarPath(String envVarPath, Map vmStructMap, Class type) {
        String expandParamsInPath = getExpandedPath(envVarPath, vmStructMap);

        Object value = jsonParsed.read(expandParamsInPath);
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

    protected <T> T readValueFromExpandedEnvVarPath(String envVarPath, Map vmStructMap) {
        return readValueFromExpandedEnvVarPath(envVarPath, vmStructMap, Object.class);
    }

    protected <T> List<T> readListValuesFromExpandedEnvVarPath(String envVarPath, Map vmStructMap) {
        String expandParamsInPath = getExpandedPath(envVarPath, vmStructMap);

        Object value = jsonParsed.read(expandParamsInPath);
        if (value instanceof Collection) {
            return new ArrayList<>((List<T>) value);
        } else {
            return Collections.singletonList((T) value);
        }
    }

    protected String getExpandedPath(String envVarPath, Map vmStructMap) {
        String envVarPathWithExpandedVersion = expandVersionInExpression(envVarPath);
        String path = env.getProperty(envVarPathWithExpandedVersion);
        return expandParamsInPath(path, vmStructMap);
    }

    protected String expandVersionInExpression(String path) {
        String replace = path.replace("{version}", manifestVersion);
        return replace;
    }

    protected String expandParamsInPath(String path, Map vmStructMap) {
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
