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
package org.openublpe.xmlbuilder.apisigner.keys.component.utils;

import org.openublpe.xmlbuilder.apisigner.keys.component.ComponentFactory;
import org.openublpe.xmlbuilder.apisigner.keys.component.ComponentModel;
import org.openublpe.xmlbuilder.apisigner.keys.provider.ProviderConfigProperty;
import org.openublpe.xmlbuilder.apisigner.models.OrganizationModel;
import org.openublpe.xmlbuilder.apisigner.representations.idm.ComponentRepresentation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ComponentUtil {

    default Map<String, ProviderConfigProperty> getComponentConfigProperties(ComponentRepresentation component) {
        return getComponentConfigProperties(component.getProviderType(), component.getProviderId());
    }

    default Map<String, ProviderConfigProperty> getComponentConfigProperties(ComponentModel component) {
        return getComponentConfigProperties(component.getProviderType(), component.getProviderId());
    }

    default ComponentFactory getComponentFactory(ComponentRepresentation component) {
        return getComponentFactory(component.getProviderType(), component.getProviderId());
    }

    default ComponentFactory getComponentFactory(ComponentModel component) {
        return getComponentFactory(component.getProviderType(), component.getProviderId());
    }

    default Map<String, ProviderConfigProperty> getComponentConfigProperties(String providerType, String providerId) {
        try {
            ComponentFactory componentFactory = getComponentFactory(providerType, providerId);
            List<ProviderConfigProperty> l = componentFactory.getConfigProperties();
            Map<String, ProviderConfigProperty> properties = new HashMap<>();
            for (ProviderConfigProperty p : l) {
                properties.put(p.getName(), p);
            }
            List<ProviderConfigProperty> common = componentFactory.getCommonProviderConfigProperties();
            for (ProviderConfigProperty p : common) {
                properties.put(p.getName(), p);
            }

            return properties;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    ComponentFactory getComponentFactory(String providerType, String providerId);

    default void notifyCreated(OrganizationModel organization, ComponentModel model) {
        ComponentFactory factory = getComponentFactory(model);
        factory.onCreate(organization, model);
    }

    default void notifyUpdated(OrganizationModel organization, ComponentModel model) {
        ComponentFactory factory = getComponentFactory(model);
        factory.onUpdate(organization, model);
    }

}
