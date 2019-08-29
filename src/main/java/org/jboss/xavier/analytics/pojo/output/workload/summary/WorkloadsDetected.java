package org.jboss.xavier.analytics.pojo.output.workload.summary;

public class WorkloadsDetected {
    private Long id;

    private Integer sles;
    private Integer oel;
    private Integer rhel;
    private Integer windows;

    public Integer getSles() {
        return sles;
    }

    public void setSles(Integer sles) {
        this.sles = sles;
    }

    public Integer getOel() {
        return oel;
    }

    public void setOel(Integer oel) {
        this.oel = oel;
    }

    public Integer getRhel() {
        return rhel;
    }

    public void setRhel(Integer rhel) {
        this.rhel = rhel;
    }

    public Integer getWindows() {
        return windows;
    }

    public void setWindows(Integer windows) {
        this.windows = windows;
    }
}
