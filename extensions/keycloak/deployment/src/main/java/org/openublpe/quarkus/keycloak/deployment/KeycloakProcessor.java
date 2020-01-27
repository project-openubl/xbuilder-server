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

        // Workaround until https://github.com/quarkusio/quarkus/issues/1762 is solved
        reflectiveClass.produce(
                new ReflectiveClassBuildItem(
                        false,
                        false,
                        "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl",
                        "com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl"));

        reflectiveClass.produce(
                new ReflectiveClassBuildItem(
                        false,
                        false,
                        "com.sun.org.apache.xml.internal.security.transforms.implementations.TransformEnvelopedSignature",
                        "com.sun.org.apache.xml.internal.security.transforms.implementations.TransformC14N"));

        reflectiveClass.produce(
                new ReflectiveClassBuildItem(
                        false,
                        false,
                        "com.sun.org.apache.xerces.internal.dom.DOMXSImplementationSourceImpl"));
    }

}
