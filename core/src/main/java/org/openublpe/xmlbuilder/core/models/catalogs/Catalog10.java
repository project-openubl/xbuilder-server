package org.openublpe.xmlbuilder.core.models.catalogs;

public enum Catalog10 implements Catalog {
    INTERES_POR_MORA("01"),
    AUMENTO_EN_EL_VALOR("02"),
    PENALIDAD_OTROS_CONCEPTOR("03");

    private final String code;

    Catalog10(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

}
