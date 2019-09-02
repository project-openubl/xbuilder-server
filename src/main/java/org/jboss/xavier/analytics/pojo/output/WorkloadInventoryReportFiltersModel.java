package org.jboss.xavier.analytics.pojo.output;

import java.util.Set;

public class WorkloadInventoryReportFiltersModel {

    private Set<String> providers;
    private Set<String> datacenters;
    private Set<String> clusters;
    private Set<String> workloads;
    private Set<String> complexities;
    private Set<String> recommendedTargetsIMS;
    private Set<String> flagsIMS;
    private Set<String> osNames;

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

    public Set<String> getOsNames() {
        return osNames;
    }

    public void setOsNames(Set<String> osNames) {
        this.osNames = osNames;
    }
}
