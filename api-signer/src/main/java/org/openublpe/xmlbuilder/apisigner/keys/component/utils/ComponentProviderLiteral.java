package org.openublpe.xmlbuilder.apisigner.keys.component.utils;

import org.openublpe.xmlbuilder.apisigner.keys.qualifiers.ComponentProviderType;

import javax.enterprise.util.AnnotationLiteral;

public class ComponentProviderLiteral extends AnnotationLiteral<ComponentProviderType> implements ComponentProviderType {

    private final Class<?> providerType;

    public ComponentProviderLiteral(Class<?> providerType) {
        this.providerType = providerType;
    }

    @Override
    public Class<?> providerType() {
        return providerType;
    }
}
