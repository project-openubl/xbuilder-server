package org.jboss.xavier.analytics.pojo.input;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.io.Serializable;

public abstract class AbstractInputModel implements Serializable
{
    @XStreamOmitField
    private Long analysisId;

    public AbstractInputModel() {}

    public AbstractInputModel(Long analysisId)
    {
        this.analysisId = analysisId;
    }

    public Long getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(Long analysisId) {
        this.analysisId = analysisId;
    }
}
