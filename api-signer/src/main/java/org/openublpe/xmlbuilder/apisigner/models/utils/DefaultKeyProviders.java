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
package org.openublpe.xmlbuilder.apisigner.models.utils;

import org.keycloak.common.util.MultivaluedHashMap;
import org.openublpe.xmlbuilder.apisigner.keys.KeyProvider;
import org.openublpe.xmlbuilder.apisigner.keys.component.ComponentModel;
import org.openublpe.xmlbuilder.apisigner.models.ComponentProvider;
import org.openublpe.xmlbuilder.apisigner.models.ModelException;
import org.openublpe.xmlbuilder.apisigner.models.OrganizationModel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Transactional
@ApplicationScoped
public class DefaultKeyProviders {

    @Inject
    ComponentProvider componentProvider;

    public void createProviders(OrganizationModel organization) throws ModelException {
        ComponentModel generated = new ComponentModel();
        generated.setName("rsa-generated");
        generated.setParentId(organization.getId());
        generated.setProviderId("rsa-generated");
        generated.setProviderType(KeyProvider.class.getName());

        MultivaluedHashMap<String, String> config = new MultivaluedHashMap<>();
        config.putSingle("priority", "100");
        generated.setConfig(config);

        componentProvider.addComponentModel(organization, generated);
    }

    public void createProviders(OrganizationModel organization, String privateKeyPem, String certificatePem) throws ModelException {
        ComponentModel rsa = new ComponentModel();
        rsa.setName("rsa");
        rsa.setParentId(organization.getId());
        rsa.setProviderId("rsa");
        rsa.setProviderType(KeyProvider.class.getName());

        MultivaluedHashMap<String, String> config = new MultivaluedHashMap<>();
        config.putSingle("priority", "100");
        config.putSingle("privateKey", privateKeyPem);
        if (certificatePem != null) {
            config.putSingle("certificate", certificatePem);
        }
        rsa.setConfig(config);

        componentProvider.addComponentModel(organization, rsa);
    }

}
