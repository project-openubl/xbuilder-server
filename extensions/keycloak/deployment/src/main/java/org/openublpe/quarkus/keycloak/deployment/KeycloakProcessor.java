package org.openublpe.quarkus.keycloak.deployment;

import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.nativeimage.ReflectiveClassBuildItem;

class KeycloakProcessor {

    private static final String FEATURE = "keycloak";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    void reflective(BuildProducer<ReflectiveClassBuildItem> reflectiveClass) {
        reflectiveClass.produce(
                new ReflectiveClassBuildItem(
                        true,
                        true,
                        "org.keycloak.representations.idm.KeysMetadataRepresentation$KeyMetadataRepresentation",
                        "org.keycloak.representations.idm.KeysMetadataRepresentation"));
    }

}
