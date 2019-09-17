package org.jboss.xavier.analytics.pojo.output.workload.summary;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class FlagAssessmentModel implements java.io.Serializable {

    @JsonIgnore
    @EmbeddedId
    private FlagAssessmentIdentityModel id;

    // This comes from FlagAssessmentIdentityModel
    @Column(insertable = false, updatable = false)
    private String flag;

    // This comes from FlagAssessmentIdentityModel
    @Column(insertable = false, updatable = false)
    private String osName;

    private String flagLabel;
    private String assessment;

    public FlagAssessmentIdentityModel getId() {
        return id;
    }

    public void setId(FlagAssessmentIdentityModel id) {
        this.id = id;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getFlagLabel() {
        return flagLabel;
    }

    public void setFlagLabel(String flagLabel) {
        this.flagLabel = flagLabel;
    }

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }
}
