package org.openubl.xmlbuilder.test;

import javax.xml.namespace.NamespaceContext;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SunatNamespaces implements NamespaceContext {

    private final Map<String, String> prefixes;

    public SunatNamespaces() {
        prefixes = new HashMap<>();
        prefixes.put("cac", "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2");
        prefixes.put("cbc", "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2");
        prefixes.put("ccts", "urn:un:unece:uncefact:documentation:2");
        prefixes.put("cec", "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2");
        prefixes.put("ds", "http://www.w3.org/2000/09/xmldsig#");
        prefixes.put("ext", "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2");
        prefixes.put("qdt", "urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2");
        prefixes.put("sac", "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1");
        prefixes.put("udt", "urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2");
        prefixes.put("xs", "http://www.w3.org/2001/XMLSchema");
        prefixes.put("xsi", "http://www.w3.org/2001/XMLSchema-instance");
    }

    public Map<String, String> getPrefixes() {
        return prefixes;
    }

    @Override
    public String getNamespaceURI(String prefix) {
        return prefixes.get(prefix);
    }

    @Override
    public String getPrefix(String s) {
        return null;
    }

    @Override
    public Iterator getPrefixes(String s) {
        return null;
    }

}
