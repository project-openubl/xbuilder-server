package org.jboss.xavier.analytics.pojo.output.workload.summary;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class FlagAssessmentIdentityModel implements java.io.Serializable {

    private String flag;
    private String osName;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlagAssessmentIdentityModel that = (FlagAssessmentIdentityModel) o;
        return flag.equals(that.flag) &&
                osName.equals(that.osName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flag, osName);
    }
}
