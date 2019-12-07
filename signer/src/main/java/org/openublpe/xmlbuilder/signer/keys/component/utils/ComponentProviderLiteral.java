package org.openublpe.xmlbuilder.signer.keys.component.utils;

import org.openublpe.xmlbuilder.signer.keys.qualifiers.ComponentProviderType;

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
