package org.openublpe.xmlbuilder.signer.keys.component.utils;

import org.openublpe.xmlbuilder.signer.keys.qualifiers.RsaKeyProviderType;
import org.openublpe.xmlbuilder.signer.keys.qualifiers.RsaKeyType;

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
