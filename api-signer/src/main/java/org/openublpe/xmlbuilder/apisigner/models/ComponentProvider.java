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
package org.openublpe.xmlbuilder.apisigner.models;

import org.openublpe.xmlbuilder.apisigner.keys.component.ComponentModel;

import java.util.List;

public interface ComponentProvider {

    /**
     * Adds component model. Will call onCreate() method of ComponentFactory
     *
     * @param model
     * @return
     */
    ComponentModel addComponentModel(OrganizationModel organization, ComponentModel model) throws ModelException;

    /**
     * Adds component model. Will NOT call onCreate() method of ComponentFactory
     *
     * @param model
     * @return
     */
    ComponentModel importComponentModel(OrganizationModel organization, ComponentModel model) throws ModelException;

    void updateComponent(OrganizationModel organization, ComponentModel component) throws ModelException;

    void removeComponent(OrganizationModel organization, ComponentModel component);

    void removeComponents(OrganizationModel organization, String parentId);

    List<ComponentModel> getComponents(OrganizationModel organization, String parentId, String providerType);

    List<ComponentModel> getComponents(OrganizationModel organization, String parentId);

    List<ComponentModel> getComponents(OrganizationModel organization);

    ComponentModel getComponent(OrganizationModel organization, String id);

}
