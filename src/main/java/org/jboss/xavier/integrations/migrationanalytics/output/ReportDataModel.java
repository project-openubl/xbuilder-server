package org.jboss.xavier.integrations.migrationanalytics.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kie.api.definition.type.Label;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportDataModel implements java.io.Serializable {

    static final long serialVersionUID = 1L;

    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO, generator = "INPUTDATAMODEL_ID_GENERATOR")
    @Id
    @SequenceGenerator(sequenceName = "INPUTDATAMODEL_ID_SEQ", name = "INPUTDATAMODEL_ID_GENERATOR")
    private Long id;

    @Label("Customer ID")
    private String customerId;

    @Label("Source payload file name")
    private String fileName;

    @Label("Number of hosts found")
    private Integer numberOfHosts;

    @Label("Total disk space used found ")
    private Long totalDiskSpace;

    @Label("Total price for subscriptions once migrated")
    private Integer totalPrice;

    @Label(value = "Date of creation")
    private Date creationDate;
    
}