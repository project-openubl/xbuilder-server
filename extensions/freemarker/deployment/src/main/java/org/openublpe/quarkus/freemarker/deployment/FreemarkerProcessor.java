package org.openublpe.quarkus.freemarker.deployment;

import freemarker.ext.jython.JythonModel;
import freemarker.ext.jython.JythonWrapper;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.nativeimage.ReflectiveClassBuildItem;
import io.quarkus.deployment.builditem.nativeimage.RuntimeInitializedClassBuildItem;

class FreemarkerProcessor {

    private static final String FEATURE = "freemarker";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    RuntimeInitializedClassBuildItem jythonModel() {
        return new RuntimeInitializedClassBuildItem(JythonModel.class.getCanonicalName());
    }

    @BuildStep
    RuntimeInitializedClassBuildItem jythonWrapper() {
        return new RuntimeInitializedClassBuildItem(JythonWrapper.class.getCanonicalName());
    }

//    @BuildStep
//    void registerForReflection(BuildProducer<ReflectiveClassBuildItem> reflectiveClass) {
//        reflectiveClass.produce(new ReflectiveClassBuildItem(true, true,
//                Exchange.class,
//                DefaultExchange.class));
//    }

}
