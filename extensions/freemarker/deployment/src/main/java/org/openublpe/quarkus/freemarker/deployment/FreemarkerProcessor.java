/**
 * Copyright 2019 Project OpenUBL, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Eclipse Public License - v 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openublpe.quarkus.freemarker.deployment;

import freemarker.ext.jython.JythonModel;
import freemarker.ext.jython.JythonWrapper;
import freemarker.log._Log4jOverSLF4JTester;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.nativeimage.ReflectiveClassBuildItem;
import io.quarkus.deployment.builditem.nativeimage.RuntimeInitializedClassBuildItem;
import org.openublpe.quarkus.freemarker.ConfigurationProvider;
import org.openublpe.quarkus.freemarker.FreemarkerBuildConfig;
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
    RuntimeInitializedClassBuildItem log4jOverSLF4JTester() {
        return new RuntimeInitializedClassBuildItem(_Log4jOverSLF4JTester.class.getCanonicalName());
    }

    @BuildStep
    void registerConfigurationProvider(BuildProducer<AdditionalBeanBuildItem> additionalBeans) {
        additionalBeans.produce(new AdditionalBeanBuildItem(ConfigurationProvider.class));
    }

    @BuildStep
    @Record(STATIC_INIT)
    public void build(FreemarkerConfigurationRecorder recorder) {
        recorder.initializeConfiguration();
    }

    @BuildStep
    void registerClassModelsForReflection(BuildProducer<ReflectiveClassBuildItem> reflectiveClass,
                                          FreemarkerBuildConfig camelFreemarkerConfig) {
        camelFreemarkerConfig.classModels.ifPresent(strings -> reflectiveClass.produce(
                new ReflectiveClassBuildItem(true, false, strings.toArray(new String[0]))));
    }
}
