package org.jboss.xavier.integrations.migrationanalytics.business;

import com.jayway.jsonpath.JsonPath;
import org.jboss.xavier.integrations.migrationanalytics.business.versioning.ManifestVersionService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FlagSharedDisksCalculator extends AbstractVMWorkloadInventoryCalculator implements Calculator<Set<String>> {
    @Inject
    ManifestVersionService manifestVersionService;

    @Override
    public Set<String> calculate(String cloudFormsJson, Map<String, Object> headers) {
        manifestVersion = getManifestVersion(cloudFormsJson);
        jsonParsed = JsonPath.parse(cloudFormsJson);
        List<Map> vms = readListValuesFromExpandedEnvVarPath(VMPATH, null);
        // This fileNamesInVms map has the 'filename' field as key and
        // the value is the Set (i.e. no duplicates) of the VM's names that use the filename as disk.
        // The filename with an associate Set with more than 1 VM name, will be a shared disk and
        // all the VM in the Set will have the "Shared Disk" flag.
        final Map<String, Set<String>> fileNamesInVms = new HashMap<>();
        String deviceTypeProperty = manifestVersionService.getPropertyWithFallbackVersion(manifestVersion,"vmworkloadinventory.vmDisksDeviceTypeProperty");
        String fileNameProperty = manifestVersionService.getPropertyWithFallbackVersion(manifestVersion, "vmworkloadinventory.vmDisksFileNameProperty");
        vms.stream().forEach(vm -> {
            String vmName = readValueFromExpandedEnvVarPath(VMNAMEPATH, vm);
            List<Map<String, String>> disks = readListValuesFromExpandedEnvVarPath(VMDISKSPATH, vm);
            disks.stream()
                .filter(disk -> disk.getOrDefault(deviceTypeProperty, "").equals("disk"))
                .map(disk -> disk.getOrDefault(fileNameProperty, ""))
                .filter(fileName -> fileName != null && !fileName.isEmpty())
                .forEach(fileName -> {
                    Set<String> vmsAssociatedWithFilename = fileNamesInVms.getOrDefault(fileName, new HashSet<>());
                    vmsAssociatedWithFilename.add(vmName);
                    fileNamesInVms.put(fileName, vmsAssociatedWithFilename);
                });
        });
        final Set<String> vmNamesWithSharedDisk = new HashSet();
        fileNamesInVms.values().stream().filter(set -> set.size() > 1).forEach(set -> vmNamesWithSharedDisk.addAll(set));
        return vmNamesWithSharedDisk;
    }

}
