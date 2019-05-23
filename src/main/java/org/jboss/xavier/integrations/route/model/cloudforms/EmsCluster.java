
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
    "aggregate_physical_cpus",
    "aggregate_cpu_total_cores",
    "aggregate_cpu_speed",
    "aggregate_memory",
    "effective_cpu",
    "effective_memory",
    "aggregate_vm_cpus",
    "aggregate_vm_memory",
    "drs_enabled",
    "drs_automation_level",
    "drs_migration_threshold",
    "ha_enabled",
    "ha_admit_control",
    "ha_max_failures",
    "total_direct_vms",
    "total_direct_miq_templates",
    "v_cpu_vr_ratio",
    "v_ram_vr_ratio",
    "hosts"
})
public class EmsCluster {

    @JsonProperty("name")
    private String name;
    @JsonProperty("aggregate_physical_cpus")
    private Long aggregatePhysicalCpus;
    @JsonProperty("aggregate_cpu_total_cores")
    private Long aggregateCpuTotalCores;
    @JsonProperty("aggregate_cpu_speed")
    private Long aggregateCpuSpeed;
    @JsonProperty("aggregate_memory")
    private Long aggregateMemory;
    @JsonProperty("effective_cpu")
    private Long effectiveCpu;
    @JsonProperty("effective_memory")
    private Long effectiveMemory;
    @JsonProperty("aggregate_vm_cpus")
    private Long aggregateVmCpus;
    @JsonProperty("aggregate_vm_memory")
    private Long aggregateVmMemory;
    @JsonProperty("drs_enabled")
    private Boolean drsEnabled;
    @JsonProperty("drs_automation_level")
    private String drsAutomationLevel;
    @JsonProperty("drs_migration_threshold")
    private Long drsMigrationThreshold;
    @JsonProperty("ha_enabled")
    private Boolean haEnabled;
    @JsonProperty("ha_admit_control")
    private Boolean haAdmitControl;
    @JsonProperty("ha_max_failures")
    private Long haMaxFailures;
    @JsonProperty("total_direct_vms")
    private Long totalDirectVms;
    @JsonProperty("total_direct_miq_templates")
    private Long totalDirectMiqTemplates;
    @JsonProperty("v_cpu_vr_ratio")
    private Double vCpuVrRatio;
    @JsonProperty("v_ram_vr_ratio")
    private Double vRamVrRatio;
    @JsonProperty("hosts")
    private List<Host> hosts = null;
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

    @JsonProperty("aggregate_physical_cpus")
    public Long getAggregatePhysicalCpus() {
        return aggregatePhysicalCpus;
    }

    @JsonProperty("aggregate_physical_cpus")
    public void setAggregatePhysicalCpus(Long aggregatePhysicalCpus) {
        this.aggregatePhysicalCpus = aggregatePhysicalCpus;
    }

    @JsonProperty("aggregate_cpu_total_cores")
    public Long getAggregateCpuTotalCores() {
        return aggregateCpuTotalCores;
    }

    @JsonProperty("aggregate_cpu_total_cores")
    public void setAggregateCpuTotalCores(Long aggregateCpuTotalCores) {
        this.aggregateCpuTotalCores = aggregateCpuTotalCores;
    }

    @JsonProperty("aggregate_cpu_speed")
    public Long getAggregateCpuSpeed() {
        return aggregateCpuSpeed;
    }

    @JsonProperty("aggregate_cpu_speed")
    public void setAggregateCpuSpeed(Long aggregateCpuSpeed) {
        this.aggregateCpuSpeed = aggregateCpuSpeed;
    }

    @JsonProperty("aggregate_memory")
    public Long getAggregateMemory() {
        return aggregateMemory;
    }

    @JsonProperty("aggregate_memory")
    public void setAggregateMemory(Long aggregateMemory) {
        this.aggregateMemory = aggregateMemory;
    }

    @JsonProperty("effective_cpu")
    public Long getEffectiveCpu() {
        return effectiveCpu;
    }

    @JsonProperty("effective_cpu")
    public void setEffectiveCpu(Long effectiveCpu) {
        this.effectiveCpu = effectiveCpu;
    }

    @JsonProperty("effective_memory")
    public Long getEffectiveMemory() {
        return effectiveMemory;
    }

    @JsonProperty("effective_memory")
    public void setEffectiveMemory(Long effectiveMemory) {
        this.effectiveMemory = effectiveMemory;
    }

    @JsonProperty("aggregate_vm_cpus")
    public Long getAggregateVmCpus() {
        return aggregateVmCpus;
    }

    @JsonProperty("aggregate_vm_cpus")
    public void setAggregateVmCpus(Long aggregateVmCpus) {
        this.aggregateVmCpus = aggregateVmCpus;
    }

    @JsonProperty("aggregate_vm_memory")
    public Long getAggregateVmMemory() {
        return aggregateVmMemory;
    }

    @JsonProperty("aggregate_vm_memory")
    public void setAggregateVmMemory(Long aggregateVmMemory) {
        this.aggregateVmMemory = aggregateVmMemory;
    }

    @JsonProperty("drs_enabled")
    public Boolean getDrsEnabled() {
        return drsEnabled;
    }

    @JsonProperty("drs_enabled")
    public void setDrsEnabled(Boolean drsEnabled) {
        this.drsEnabled = drsEnabled;
    }

    @JsonProperty("drs_automation_level")
    public String getDrsAutomationLevel() {
        return drsAutomationLevel;
    }

    @JsonProperty("drs_automation_level")
    public void setDrsAutomationLevel(String drsAutomationLevel) {
        this.drsAutomationLevel = drsAutomationLevel;
    }

    @JsonProperty("drs_migration_threshold")
    public Long getDrsMigrationThreshold() {
        return drsMigrationThreshold;
    }

    @JsonProperty("drs_migration_threshold")
    public void setDrsMigrationThreshold(Long drsMigrationThreshold) {
        this.drsMigrationThreshold = drsMigrationThreshold;
    }

    @JsonProperty("ha_enabled")
    public Boolean getHaEnabled() {
        return haEnabled;
    }

    @JsonProperty("ha_enabled")
    public void setHaEnabled(Boolean haEnabled) {
        this.haEnabled = haEnabled;
    }

    @JsonProperty("ha_admit_control")
    public Boolean getHaAdmitControl() {
        return haAdmitControl;
    }

    @JsonProperty("ha_admit_control")
    public void setHaAdmitControl(Boolean haAdmitControl) {
        this.haAdmitControl = haAdmitControl;
    }

    @JsonProperty("ha_max_failures")
    public Long getHaMaxFailures() {
        return haMaxFailures;
    }

    @JsonProperty("ha_max_failures")
    public void setHaMaxFailures(Long haMaxFailures) {
        this.haMaxFailures = haMaxFailures;
    }

    @JsonProperty("total_direct_vms")
    public Long getTotalDirectVms() {
        return totalDirectVms;
    }

    @JsonProperty("total_direct_vms")
    public void setTotalDirectVms(Long totalDirectVms) {
        this.totalDirectVms = totalDirectVms;
    }

    @JsonProperty("total_direct_miq_templates")
    public Long getTotalDirectMiqTemplates() {
        return totalDirectMiqTemplates;
    }

    @JsonProperty("total_direct_miq_templates")
    public void setTotalDirectMiqTemplates(Long totalDirectMiqTemplates) {
        this.totalDirectMiqTemplates = totalDirectMiqTemplates;
    }

    @JsonProperty("v_cpu_vr_ratio")
    public Double getVCpuVrRatio() {
        return vCpuVrRatio;
    }

    @JsonProperty("v_cpu_vr_ratio")
    public void setVCpuVrRatio(Double vCpuVrRatio) {
        this.vCpuVrRatio = vCpuVrRatio;
    }

    @JsonProperty("v_ram_vr_ratio")
    public Double getVRamVrRatio() {
        return vRamVrRatio;
    }

    @JsonProperty("v_ram_vr_ratio")
    public void setVRamVrRatio(Double vRamVrRatio) {
        this.vRamVrRatio = vRamVrRatio;
    }

    @JsonProperty("hosts")
    public List<Host> getHosts() {
        return hosts;
    }

    @JsonProperty("hosts")
    public void setHosts(List<Host> hosts) {
        this.hosts = hosts;
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
