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

        reflectiveClass.produce(
                new ReflectiveClassBuildItem(
                        false,
                        false,
                        "com.sun.org.apache.xml.internal.security.transforms.implementations.TransformEnvelopedSignature",
                        "com.sun.org.apache.xml.internal.security.transforms.implementations.TransformC14N"));

        reflectiveClass.produce(
                new ReflectiveClassBuildItem(
                        true,
                        true,
                        "com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl",
                        "com.sun.org.apache.xml.internal.serializer.utils.SerializerMessages"));

        // Workaround until https://github.com/quarkusio/quarkus/issues/1762 is solved
        reflectiveClass.produce(
                new ReflectiveClassBuildItem(
                        false,
                        false,
                        "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl",
                        "com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl"));
    }

}
