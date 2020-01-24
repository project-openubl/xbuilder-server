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
package org.openublpe.xmlbuilder.apisigner.keys.component;

import org.openublpe.xmlbuilder.apisigner.keys.provider.ConfiguredProvider;
import org.openublpe.xmlbuilder.apisigner.keys.provider.ProviderConfigProperty;
import org.openublpe.xmlbuilder.apisigner.keys.provider.ProviderFactory;
import org.openublpe.xmlbuilder.apisigner.models.OrganizationModel;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface ComponentFactory<CreatedType, ProviderType> extends ProviderFactory<ProviderType>, ConfiguredProvider {

    CreatedType create(OrganizationModel organization, ComponentModel model);

    @Override
    default ProviderType create() {
        return null;
    }

    default void validateConfiguration(OrganizationModel organization, ComponentModel model) throws ComponentValidationException {
    }

    default void onCreate(OrganizationModel organization, ComponentModel model) {
    }


    default void onUpdate(OrganizationModel organization, ComponentModel model) {
    }

    /**
     * These are config properties that are common across all implementation of this component type
     *
     * @return
     */
    default List<ProviderConfigProperty> getCommonProviderConfigProperties() {
        return Collections.EMPTY_LIST;
    }

    /**
     * This is metadata about this component type.  Its really configuration information about the component type and not
     * an individual instance
     *
     * @return
     */
    default Map<String, Object> getTypeMetadata() {
        return Collections.EMPTY_MAP;
    }


}
