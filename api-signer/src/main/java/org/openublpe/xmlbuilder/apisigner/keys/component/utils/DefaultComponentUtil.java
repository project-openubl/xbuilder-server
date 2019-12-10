package org.openublpe.xmlbuilder.apisigner.keys.component.utils;

import org.openublpe.xmlbuilder.apisigner.keys.KeyProviderFactory;
import org.openublpe.xmlbuilder.apisigner.keys.component.ComponentFactory;
import org.openublpe.xmlbuilder.apisigner.keys.qualifiers.RsaKeyType;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.util.Optional;

@ApplicationScoped
public class DefaultComponentUtil implements ComponentUtil {

    @Inject
    @Any
    Instance<KeyProviderFactory> componentFactories;

    @Override
    public ComponentFactory getComponentFactory(String providerType, String providerId) {
        try {
            Class<?> aClass = Class.forName(providerType);

            Optional<RsaKeyType> op = RsaKeyType.findByProviderId(providerId);
            if (!op.isPresent()) {
                return null;
            }

            Annotation componentProviderLiteral = new ComponentProviderLiteral(aClass);
            Annotation rsaKeyProviderLiteral = new RsaKeyProviderLiteral(op.get());

            return componentFactories.select(KeyProviderFactory.class, componentProviderLiteral, rsaKeyProviderLiteral).get();
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Invalid factory", e);
        }
    }


}
