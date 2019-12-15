package org.openublpe.xmlbuilder.core.models.catalogs;

public enum Catalog16 implements Catalog {

    PRECIO_UNITARIO_INCLUYE_IGV("01"),
    VALOR_FERENCIAL_UNITARIO_EN_OPERACIONES_NO_ONEROSAS("02");

    private final String code;

    Catalog16(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

}
