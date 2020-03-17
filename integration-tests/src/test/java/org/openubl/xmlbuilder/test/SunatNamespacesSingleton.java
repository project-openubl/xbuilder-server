package org.openubl.xmlbuilder.test;

public class SunatNamespacesSingleton {

    private static SunatNamespaces instance;

    private SunatNamespacesSingleton() {
    }

    public static SunatNamespaces getInstance() {
        if (instance == null) {
            instance = new SunatNamespaces();
        }
        return instance;
    }

}
