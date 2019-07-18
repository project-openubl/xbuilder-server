package org.jboss.xavier.integrations.jpa.projection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.jboss.xavier.analytics.pojo.output.EnvironmentModel;
import org.jboss.xavier.analytics.pojo.output.InitialSavingsEstimationReportModel;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import java.util.Date;

public interface InitialSavingsEstimationReportSummary
{
    Long getId();
    String getCustomerId();
    String getFileName();
    Date getcreationDate();
    default String getStatus()
    {
        return "CREATED";
    }

}
