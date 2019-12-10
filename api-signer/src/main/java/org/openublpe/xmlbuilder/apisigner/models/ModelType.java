package org.openublpe.xmlbuilder.apisigner.models;

public enum ModelType {

    ORGANIZATION("organization");

    private String alias;

    ModelType(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }
}
