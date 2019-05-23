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
    "hostname",
    "emstype_description",
    "api_version",
    "datacenters"
})
public class CloudFormAnalysis {

    @JsonProperty("hostname")
    private String hostname;
    @JsonProperty("emstype_description")
    private String emstypeDescription;
    @JsonProperty("api_version")
    private String apiVersion;
    @JsonProperty("datacenters")
    private List<Datacenter> datacenters = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("hostname")
    public String getHostname() {
        return hostname;
    }

    @JsonProperty("hostname")
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    @JsonProperty("emstype_description")
    public String getEmstypeDescription() {
        return emstypeDescription;
    }

    @JsonProperty("emstype_description")
    public void setEmstypeDescription(String emstypeDescription) {
        this.emstypeDescription = emstypeDescription;
    }

    @JsonProperty("api_version")
    public String getApiVersion() {
        return apiVersion;
    }

    @JsonProperty("api_version")
    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    @JsonProperty("datacenters")
    public List<Datacenter> getDatacenters() {
        return datacenters;
    }

    @JsonProperty("datacenters")
    public void setDatacenters(List<Datacenter> datacenters) {
        this.datacenters = datacenters;
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
