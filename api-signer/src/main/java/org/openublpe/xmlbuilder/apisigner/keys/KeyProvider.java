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

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.keycloak.jose.jws.AlgorithmType;

import java.util.List;

@RegisterForReflection
public interface KeyProvider<T extends KeyMetadata> {

    /**
     * Returns the algorithm type the keys can be used for
     *
     * @return
     */
    AlgorithmType getType();

    /**
     * Return the KID for the active keypair, or <code>null</code> if no active key is available.
     *
     * @return
     */
    String getKid();

    /**
     * Return metadata about all keypairs held by the provider
     *
     * @return
     */
    List<T> getKeyMetadata();

}
