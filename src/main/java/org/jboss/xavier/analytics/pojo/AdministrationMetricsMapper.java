package org.jboss.xavier.analytics.pojo;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AdministrationMetricsMapper {

    public List<AdministrationMetricsModel> toAdministrationMetricsModels(List<AdministrationMetricsProjection> projections) {
        return projections.stream().map(this::toAdministrationMetricsModel).collect(Collectors.toList());
    }

    public AdministrationMetricsModel toAdministrationMetricsModel(AdministrationMetricsProjection projection) {
        AdministrationMetricsModel model = new AdministrationMetricsModel();
        model.setOwner(projection.getOwner());
        model.setPayloadName(projection.getPayloadName());
        model.setAnalysisStatus(projection.getAnalysisStatus());
        model.setAnalysisInserted(projection.getAnalysisInserted());
        model.setTotalVms(projection.getTotalVms());

        String owner = projection.getOwner();
        if (owner.contains("@")) {
            model.setOwnerDomain(owner.substring(owner.indexOf("@") + 1));
        }

        return model;
    }
}
