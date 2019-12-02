package org.openublpe.quarkus.freemarker.deployment;

import freemarker.ext.jython.JythonModel;
import freemarker.ext.jython.JythonWrapper;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.nativeimage.RuntimeInitializedClassBuildItem;
import org.openublpe.quarkus.freemarker.ConfigurationProvider;
import org.openublpe.quarkus.freemarker.FreemarkerConfigurationRecorder;

import static io.quarkus.deployment.annotations.ExecutionTime.STATIC_INIT;

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

    @BuildStep
    void registerAdditionalBeans(BuildProducer<AdditionalBeanBuildItem> additionalBeans) {
        additionalBeans.produce(new AdditionalBeanBuildItem(ConfigurationProvider.class));
    }

    @BuildStep
    @Record(STATIC_INIT)
    public void build(FreemarkerConfigurationRecorder recorder) {
        recorder.initializeConfiguration();
    }
}
