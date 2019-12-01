package org.openublpe.quarkus.freemarker.deployment;

import freemarker.ext.jython.JythonModel;
import freemarker.ext.jython.JythonWrapper;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.arc.deployment.AnnotationsTransformerBuildItem;
import io.quarkus.arc.deployment.BeanContainerListenerBuildItem;
import io.quarkus.arc.deployment.UnremovableBeanBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.CombinedIndexBuildItem;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.ShutdownContextBuildItem;
import io.quarkus.deployment.builditem.nativeimage.ReflectiveClassBuildItem;
import io.quarkus.deployment.builditem.nativeimage.ReflectiveFieldBuildItem;
import io.quarkus.deployment.builditem.nativeimage.ReflectiveMethodBuildItem;
import io.quarkus.deployment.builditem.nativeimage.RuntimeInitializedClassBuildItem;
import io.quarkus.deployment.recording.RecorderContext;
import org.jboss.jandex.AnnotationInstance;
import org.jboss.jandex.AnnotationTarget;
import org.jboss.jandex.DotName;
import org.jboss.jandex.IndexView;
import org.openublpe.quarkus.freemarker.ConfigurationProvider;
import org.openublpe.quarkus.freemarker.FreemarkerConfigurationRecorder;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

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

//    @BuildStep
//    void registerForReflection(BuildProducer<ReflectiveClassBuildItem> reflectiveClass) {
//        reflectiveClass.produce(new ReflectiveClassBuildItem(true, true,
//                Exchange.class,
//                DefaultExchange.class));
//    }

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
