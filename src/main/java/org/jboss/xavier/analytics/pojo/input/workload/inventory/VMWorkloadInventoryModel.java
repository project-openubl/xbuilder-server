package org.jboss.xavier.analytics.pojo.input.workload.inventory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class VMWorkloadInventoryModel implements Serializable {
    //common/name
    private String provider;
    //common/ems_clusters/v_parent_datacenter
    private String datacenter;
    //common/ems_clusters/name
    private String cluster;
    //vms/name
    private String vmName;
    //sum of vms/hardware/disks/size_on_disk
    private Long diskSpace;
    //vms/ram_size_in_bytes
    private Long memory;
    //vms/num_cpu
    private Integer cpuCores;
    //vms/operating_system/product_name
    private String osProductName;
    //hardware/guest_os_full_name
    private String guestOSFullName;
    //vms/has_rdm_disk
    private boolean hasRdmDisk;
    //count of nics object within the vms/hardware
    private Integer nicsCount;
    //hardware/disks/filename
    private Collection<String> vmDiskFilenames;
    private Collection<String> systemServicesNames;
    private Map<String,String> files;

    public VMWorkloadInventoryModel()
    {
        this.systemServicesNames = new ArrayList<> ();
        this.files = new HashMap<>();
        this.vmDiskFilenames = new ArrayList<>();
        nicsCount = 0;
        diskSpace = 0L;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getDatacenter() {
        return datacenter;
    }

    public void setDatacenter(String datacenter) {
        this.datacenter = datacenter;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public Long getDiskSpace() {
        return diskSpace;
    }

    public void setDiskSpace(Long diskSpace) {
        this.diskSpace = diskSpace;
    }

    public void addDiskSpace(Long nextDiskSpace) {
        this.diskSpace += nextDiskSpace;
    }

    public Long getMemory() {
        return memory;
    }

    public void setMemory(Long memory) {
        this.memory = memory;
    }

    public Integer getCpuCores() {
        return cpuCores;
    }

    public void setCpuCores(Integer cpuCores) {
        this.cpuCores = cpuCores;
    }

    public String getOsProductName() {
        return osProductName;
    }

    public void setOsProductName(String osProductName) {
        this.osProductName = osProductName;
    }

    public String getGuestOSFullName() {
        return guestOSFullName;
    }

    public void setGuestOSFullName(String guestOSFullName) {
        this.guestOSFullName = guestOSFullName;
    }

    public boolean isHasRdmDisk() {
        return hasRdmDisk;
    }

    public void setHasRdmDisk(boolean hasRdmDisk) {
        this.hasRdmDisk = hasRdmDisk;
    }

    public Integer getNicsCount() {
        return nicsCount;
    }

    public void setNicsCount(Integer nicsCount) {
        this.nicsCount = nicsCount;
    }

    public void addNicsCount() {
        this.nicsCount++;
    }

    public Collection<String> getVmDiskFilenames() {
        return vmDiskFilenames;
    }

    public void setVmDiskFilenames(Collection<String> vmDiskFilenames) {
        this.vmDiskFilenames = vmDiskFilenames;
    }

    public void addVmDiskFilename(String vmDiskFilename) {
        this.vmDiskFilenames.add(vmDiskFilename);
    }

    public Collection<String> getSystemServicesNames() {
        return systemServicesNames;
    }

    public void setSystemServicesNames(Collection<String> systemServicesNames) {
        this.systemServicesNames = systemServicesNames;
    }

    public void addSystemService(String systemServiceName) {
        this.systemServicesNames.add(systemServiceName);
    }

    public Map<String, String> getFiles() {
        return files;
    }


    public void setFiles(Map<String, String> files) {
        this.files = files;
    }

    public void addFile(String name, String contents) {
        this.files.put(name,contents);
    }
}
