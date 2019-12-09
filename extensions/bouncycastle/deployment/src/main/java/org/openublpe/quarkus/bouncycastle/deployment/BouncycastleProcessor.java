package org.openublpe.quarkus.bouncycastle.deployment;

import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.nativeimage.ReflectiveClassBuildItem;

class BouncycastleProcessor {

    private static final String FEATURE = "bouncycastle";

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
                        "org.bouncycastle.jcajce.provider.asymmetric.rsa.KeyFactorySpi",
                        "org.bouncycastle.jcajce.provider.asymmetric.x509.CertificateFactory"));
    }

}
