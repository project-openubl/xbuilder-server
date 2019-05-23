
package org.jboss.xavier.integrations.route.model.cloudforms;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "power_state",
    "vmm_product",
    "vmm_version",
    "vmm_buildnumber",
    "num_cpu",
    "cpu_total_cores",
    "cpu_cores_per_socket",
    "hyperthreading",
    "ram_size",
    "v_total_vms",
    "v_total_miq_templates"
})
public class Host {

    @JsonProperty("name")
    private String name;
    @JsonProperty("power_state")
    private String powerState;
    @JsonProperty("vmm_product")
    private String vmmProduct;
    @JsonProperty("vmm_version")
    private String vmmVersion;
    @JsonProperty("vmm_buildnumber")
    private String vmmBuildnumber;
    @JsonProperty("num_cpu")
    private Long numCpu;
    @JsonProperty("cpu_total_cores")
    private Long cpuTotalCores;
    @JsonProperty("cpu_cores_per_socket")
    private Long cpuCoresPerSocket;
    @JsonProperty("hyperthreading")
    private Boolean hyperthreading;
    @JsonProperty("ram_size")
    private Long ramSize;
    @JsonProperty("v_total_vms")
    private Long vTotalVms;
    @JsonProperty("v_total_miq_templates")
    private Long vTotalMiqTemplates;
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

    @JsonProperty("power_state")
    public String getPowerState() {
        return powerState;
    }

    @JsonProperty("power_state")
    public void setPowerState(String powerState) {
        this.powerState = powerState;
    }

    @JsonProperty("vmm_product")
    public String getVmmProduct() {
        return vmmProduct;
    }

    @JsonProperty("vmm_product")
    public void setVmmProduct(String vmmProduct) {
        this.vmmProduct = vmmProduct;
    }

    @JsonProperty("vmm_version")
    public String getVmmVersion() {
        return vmmVersion;
    }

    @JsonProperty("vmm_version")
    public void setVmmVersion(String vmmVersion) {
        this.vmmVersion = vmmVersion;
    }

    @JsonProperty("vmm_buildnumber")
    public String getVmmBuildnumber() {
        return vmmBuildnumber;
    }

    @JsonProperty("vmm_buildnumber")
    public void setVmmBuildnumber(String vmmBuildnumber) {
        this.vmmBuildnumber = vmmBuildnumber;
    }

    @JsonProperty("num_cpu")
    public Long getNumCpu() {
        return numCpu;
    }

    @JsonProperty("num_cpu")
    public void setNumCpu(Long numCpu) {
        this.numCpu = numCpu;
    }

    @JsonProperty("cpu_total_cores")
    public Long getCpuTotalCores() {
        return cpuTotalCores;
    }

    @JsonProperty("cpu_total_cores")
    public void setCpuTotalCores(Long cpuTotalCores) {
        this.cpuTotalCores = cpuTotalCores;
    }

    @JsonProperty("cpu_cores_per_socket")
    public Long getCpuCoresPerSocket() {
        return cpuCoresPerSocket;
    }

    @JsonProperty("cpu_cores_per_socket")
    public void setCpuCoresPerSocket(Long cpuCoresPerSocket) {
        this.cpuCoresPerSocket = cpuCoresPerSocket;
    }

    @JsonProperty("hyperthreading")
    public Boolean getHyperthreading() {
        return hyperthreading;
    }

    @JsonProperty("hyperthreading")
    public void setHyperthreading(Boolean hyperthreading) {
        this.hyperthreading = hyperthreading;
    }

    @JsonProperty("ram_size")
    public Long getRamSize() {
        return ramSize;
    }

    @JsonProperty("ram_size")
    public void setRamSize(Long ramSize) {
        this.ramSize = ramSize;
    }

    @JsonProperty("v_total_vms")
    public Long getVTotalVms() {
        return vTotalVms;
    }

    @JsonProperty("v_total_vms")
    public void setVTotalVms(Long vTotalVms) {
        this.vTotalVms = vTotalVms;
    }

    @JsonProperty("v_total_miq_templates")
    public Long getVTotalMiqTemplates() {
        return vTotalMiqTemplates;
    }

    @JsonProperty("v_total_miq_templates")
    public void setVTotalMiqTemplates(Long vTotalMiqTemplates) {
        this.vTotalMiqTemplates = vTotalMiqTemplates;
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
