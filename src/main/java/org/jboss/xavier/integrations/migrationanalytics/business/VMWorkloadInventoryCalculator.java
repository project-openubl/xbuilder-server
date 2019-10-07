package org.jboss.xavier.integrations.migrationanalytics.business;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jboss.xavier.analytics.pojo.input.workload.inventory.VMWorkloadInventoryModel;
import org.jboss.xavier.integrations.route.MainRouteBuilder;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class VMWorkloadInventoryCalculator extends AbstractVMWorkloadInventoryCalculator implements Calculator<Collection<VMWorkloadInventoryModel>> {

    @Override
    public Collection<VMWorkloadInventoryModel> calculate(String cloudFormsJson, Map<String, Object> headers) {
        manifestVersion = getManifestVersion(cloudFormsJson);
        jsonParsed = JsonPath.parse(cloudFormsJson);
        scanRunDate = getScanRunDate();

        List<Map> vmList = readListValuesFromExpandedEnvVarPath(VMPATH, null);
        return vmList.stream().map(e -> createVMWorkloadInventoryModel(e, Long.parseLong(headers.get(MainRouteBuilder.ANALYSIS_ID).toString()))).collect(Collectors.toList());
    }

    private Date getScanRunDate() {
        Date scanrundate;
        try {
            scanrundate = new SimpleDateFormat("yyyy-M-dd'T'hh:mm:ss.S").parse(readValueFromExpandedEnvVarPath(DATACOLLECTEDON, null));
        } catch (Exception e) {
            scanrundate = new Date();
            log.warn("Using now date as fallback for Scan Run Date");
        }
        return scanrundate;
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
        model.setDiskSpace(diskSpaceList.stream().filter(Objects::nonNull).mapToLong(Number::longValue).sum());

        model.setNicsCount(readValueFromExpandedEnvVarPath(NICSPATH, vmStructMap, Integer.class));

        model.setFiles(readMapValuesFromExpandedEnvVarPath(FILESCONTENTPATH, vmStructMap, getExpandedPath(FILESCONTENTPATH_FILENAME, vmStructMap), getExpandedPath(FILESCONTENTPATH_CONTENTS, vmStructMap)));
        model.setSystemServicesNames(readListValuesFromExpandedEnvVarPath(SYSTEMSERVICESNAMESPATH, vmStructMap));
        model.setVmDiskFilenames(readListValuesFromExpandedEnvVarPath(VMDISKSFILENAMESPATH, vmStructMap));

        model.setProduct(readValueFromExpandedEnvVarPath(PRODUCTPATH, vmStructMap));
        model.setVersion(readValueFromExpandedEnvVarPath(VERSIONPATH, vmStructMap));
        model.setHost_name(readValueFromExpandedEnvVarPath(HOSTNAMEPATH, vmStructMap));

        model.setScanRunDate(scanRunDate);

        model.setAnalysisId(analysisId);

        return model;
    }

}
