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
package org.openublpe.xmlbuilder.apisigner.keys;

import org.openublpe.xmlbuilder.apisigner.keys.component.ComponentModel;
import org.openublpe.xmlbuilder.apisigner.keys.component.ComponentValidationException;
import org.openublpe.xmlbuilder.apisigner.keys.provider.ConfigurationValidationHelper;
import org.openublpe.xmlbuilder.apisigner.keys.provider.ProviderConfigurationBuilder;
import org.openublpe.xmlbuilder.apisigner.models.OrganizationModel;

public abstract class AbstractRsaKeyProviderFactory {

    public final static ProviderConfigurationBuilder configurationBuilder() {
        return ProviderConfigurationBuilder.create()
                .property(Attributes.PRIORITY_PROPERTY)
                .property(Attributes.ENABLED_PROPERTY)
                .property(Attributes.ACTIVE_PROPERTY);
    }

    public void validateConfiguration(OrganizationModel organization, ComponentModel model) throws ComponentValidationException {
        ConfigurationValidationHelper.check(model)
                .checkLong(Attributes.PRIORITY_PROPERTY, false)
                .checkBoolean(Attributes.ENABLED_PROPERTY, false)
                .checkBoolean(Attributes.ACTIVE_PROPERTY, false);
    }
}
