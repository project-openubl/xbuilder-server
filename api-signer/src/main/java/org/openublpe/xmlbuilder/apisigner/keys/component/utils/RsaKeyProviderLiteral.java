package org.openublpe.xmlbuilder.apisigner.keys.component.utils;

import org.openublpe.xmlbuilder.apisigner.keys.qualifiers.RsaKeyProviderType;
import org.openublpe.xmlbuilder.apisigner.keys.qualifiers.RsaKeyType;

import javax.enterprise.util.AnnotationLiteral;

public class RsaKeyProviderLiteral extends AnnotationLiteral<RsaKeyProviderType> implements RsaKeyProviderType {

    private final RsaKeyType type;

    public RsaKeyProviderLiteral(RsaKeyType type) {
        this.type = type;
    }

    @Override
    public RsaKeyType type() {
        return type;
    }

}
