
package org.jboss.xavier.integrations.route.model.cloudforms;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "store_type",
    "raw_disk_mappings_supported",
    "thin_provisioning_supported",
    "directory_hierarchy_supported",
    "multiplehostaccess",
    "total_space",
    "uncommitted",
    "free_space",
    "v_total_vms",
    "hosts",
    "storage_profiles"
})
public class Datastore {

    @JsonProperty("name")
    private String name;
    @JsonProperty("store_type")
    private String storeType;
    @JsonProperty("raw_disk_mappings_supported")
    private Boolean rawDiskMappingsSupported;
    @JsonProperty("thin_provisioning_supported")
    private Boolean thinProvisioningSupported;
    @JsonProperty("directory_hierarchy_supported")
    private Boolean directoryHierarchySupported;
    @JsonProperty("multiplehostaccess")
    private Long multiplehostaccess;
    @JsonProperty("total_space")
    private Long totalSpace;
    @JsonProperty("uncommitted")
    private Long uncommitted;
    @JsonProperty("free_space")
    private Long freeSpace;
    @JsonProperty("v_total_vms")
    private Long vTotalVms;
    @JsonProperty("hosts")
    private List<String> hosts = null;
    @JsonProperty("storage_profiles")
    private List<String> storageProfiles = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("store_type")
    public String getStoreType() {
        return storeType;
    }

    @JsonProperty("store_type")
    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    @JsonProperty("raw_disk_mappings_supported")
    public Boolean getRawDiskMappingsSupported() {
        return rawDiskMappingsSupported;
    }

    @JsonProperty("raw_disk_mappings_supported")
    public void setRawDiskMappingsSupported(Boolean rawDiskMappingsSupported) {
        this.rawDiskMappingsSupported = rawDiskMappingsSupported;
    }

    @JsonProperty("thin_provisioning_supported")
    public Boolean getThinProvisioningSupported() {
        return thinProvisioningSupported;
    }

    @JsonProperty("thin_provisioning_supported")
    public void setThinProvisioningSupported(Boolean thinProvisioningSupported) {
        this.thinProvisioningSupported = thinProvisioningSupported;
    }

    @JsonProperty("directory_hierarchy_supported")
    public Boolean getDirectoryHierarchySupported() {
        return directoryHierarchySupported;
    }

    @JsonProperty("directory_hierarchy_supported")
    public void setDirectoryHierarchySupported(Boolean directoryHierarchySupported) {
        this.directoryHierarchySupported = directoryHierarchySupported;
    }

    @JsonProperty("multiplehostaccess")
    public Long getMultiplehostaccess() {
        return multiplehostaccess;
    }

    @JsonProperty("multiplehostaccess")
    public void setMultiplehostaccess(Long multiplehostaccess) {
        this.multiplehostaccess = multiplehostaccess;
    }

    @JsonProperty("total_space")
    public Long getTotalSpace() {
        return totalSpace;
    }

    @JsonProperty("total_space")
    public void setTotalSpace(Long totalSpace) {
        this.totalSpace = totalSpace;
    }

    @JsonProperty("uncommitted")
    public Long getUncommitted() {
        return uncommitted;
    }

    @JsonProperty("uncommitted")
    public void setUncommitted(Long uncommitted) {
        this.uncommitted = uncommitted;
    }

    @JsonProperty("free_space")
    public Long getFreeSpace() {
        return freeSpace;
    }

    @JsonProperty("free_space")
    public void setFreeSpace(Long freeSpace) {
        this.freeSpace = freeSpace;
    }

    @JsonProperty("v_total_vms")
    public Long getVTotalVms() {
        return vTotalVms;
    }

    @JsonProperty("v_total_vms")
    public void setVTotalVms(Long vTotalVms) {
        this.vTotalVms = vTotalVms;
    }

    @JsonProperty("hosts")
    public List<String> getHosts() {
        return hosts;
    }

    @JsonProperty("hosts")
    public void setHosts(List<String> hosts) {
        this.hosts = hosts;
    }

    @JsonProperty("storage_profiles")
    public List<String> getStorageProfiles() {
        return storageProfiles;
    }

    @JsonProperty("storage_profiles")
    public void setStorageProfiles(List<String> storageProfiles) {
        this.storageProfiles = storageProfiles;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
