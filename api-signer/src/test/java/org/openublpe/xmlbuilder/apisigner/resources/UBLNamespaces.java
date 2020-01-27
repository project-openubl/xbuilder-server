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
package org.openublpe.xmlbuilder.apisigner.resources;

import org.custommonkey.xmlunit.NamespaceContext;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UBLNamespaces implements NamespaceContext {

    Map<String, String> map;

    public UBLNamespaces() {
        map = new HashMap<>();
        map.put("cac", "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2");
        map.put("cbc", "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2");
        map.put("ccts", "urn:un:unece:uncefact:documentation:2");
        map.put("cec", "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2");
        map.put("ds", "http://www.w3.org/2000/09/xmldsig#");
        map.put("ext", "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2");
        map.put("qdt", "urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2");
        map.put("sac", "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1");
        map.put("udt", "urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2");
        map.put("xs", "http://www.w3.org/2001/XMLSchema");
        map.put("xsi", "http://www.w3.org/2001/XMLSchema-instance");
    }

    @Override
    public String getNamespaceURI(String prefix) {
        return map.get(prefix);
    }

    @Override
    public Iterator getPrefixes() {
        return map.keySet().stream().iterator();
    }
}
