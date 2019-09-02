package org.jboss.xavier.integrations.route.model;

import java.util.Objects;
import java.util.Set;

public class WorkloadInventoryFilterBean {

    private Set<String> providers;
    private Set<String> datacenters;
    private Set<String> clusters;
    private Set<String> vmNames;
    private Set<String> osNames;
    private Set<String> workloads;
    private Set<String> complexities;
    private Set<String> recommendedTargetsIMS;
    private Set<String> flagsIMS;

    public Set<String> getProviders() {
        return providers;
    }

    public void setProviders(Set<String> providers) {
        this.providers = providers;
    }

    public Set<String> getDatacenters() {
        return datacenters;
    }

    public void setDatacenters(Set<String> datacenters) {
        this.datacenters = datacenters;
    }

    public Set<String> getClusters() {
        return clusters;
    }

    public void setClusters(Set<String> clusters) {
        this.clusters = clusters;
    }

    public Set<String> getVmNames() {
        return vmNames;
    }

    public void setVmNames(Set<String> vmNames) {
        this.vmNames = vmNames;
    }

    public Set<String> getOsNames() {
        return osNames;
    }

    public void setOsNames(Set<String> osNames) {
        this.osNames = osNames;
    }

    public Set<String> getWorkloads() {
        return workloads;
    }

    public void setWorkloads(Set<String> workloads) {
        this.workloads = workloads;
    }

    public Set<String> getComplexities() {
        return complexities;
    }

    public void setComplexities(Set<String> complexities) {
        this.complexities = complexities;
    }

    public Set<String> getRecommendedTargetsIMS() {
        return recommendedTargetsIMS;
    }

    public void setRecommendedTargetsIMS(Set<String> recommendedTargetsIMS) {
        this.recommendedTargetsIMS = recommendedTargetsIMS;
    }

    public Set<String> getFlagsIMS() {
        return flagsIMS;
    }

    public void setFlagsIMS(Set<String> flagsIMS) {
        this.flagsIMS = flagsIMS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkloadInventoryFilterBean that = (WorkloadInventoryFilterBean) o;
        return Objects.equals(providers, that.providers) &&
                Objects.equals(datacenters, that.datacenters) &&
                Objects.equals(clusters, that.clusters) &&
                Objects.equals(vmNames, that.vmNames) &&
                Objects.equals(osNames, that.osNames) &&
                Objects.equals(workloads, that.workloads) &&
                Objects.equals(complexities, that.complexities) &&
                Objects.equals(recommendedTargetsIMS, that.recommendedTargetsIMS) &&
                Objects.equals(flagsIMS, that.flagsIMS);
    }

    @Override
    public int hashCode() {
        return Objects.hash(providers, datacenters, clusters, vmNames, osNames, workloads, complexities, recommendedTargetsIMS, flagsIMS);
    }
}
