package org.openublpe.xmlbuilder.models.ubl;

public enum Catalog6 implements Catalog {

    DOC_TRIB_NO_DOM_SIN_RUC("0"),
    DNI("1"),
    EXTRANJERIA("4"),
    RUC("6"),
    PASAPORTE("7"),
    DEC_DIPLOMATICA("A");

    private final String code;

    @Override
    public String getCode() {
        return code;
    }

    Catalog6(String code) {
        this.code = code;
    }

}
