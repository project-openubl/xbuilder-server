package org.openublpe.xmlbuilder.models.ubl;

public enum Catalog7 implements Catalog {

    GRAVADO_OPERACION_ONEROSA("10", true, true, Catalog5.IGV),
    GRAVADO_RETIRO_POR_PREMIO("11", true, false, Catalog5.GRATUITO),
    GRAVADO_RETIRO_POR_DONACION("12", true, false, Catalog5.GRATUITO),
    GRAVADO_RETIRO("13", true, false, Catalog5.GRATUITO),
    GRAVADO_RETIRO_POR_PUBLICIDAD("14", true, false, Catalog5.GRATUITO),
    GRAVADO_BONIFICACIONES("15", true, false, Catalog5.GRATUITO),
    GRAVADO_RETIRO_POR_ENTREGA_A_TRABAJADORES("16", true, false, Catalog5.GRATUITO),
    GRAVADO_IVAP("17", true, false, Catalog5.GRATUITO),
    EXONERADO_OPERACION_ONEROSA("20", false, true, Catalog5.EXONERADO),
    EXONERADO_TRANSFERENCIA_GRATUITA("21", false, false, Catalog5.GRATUITO),
    INAFECTO_OPERACION_ONEROSA("30", false, true, Catalog5.INAFECTO),
    INAFECTO_RETIRO_POR_BONIFICACION("31", false, false, Catalog5.GRATUITO),
    INAFECTO_RETIRO("32", false, false, Catalog5.GRATUITO),
    INAFECTO_RETIRO_POR_MUESTRAS_MEDICAS("33", false, false, Catalog5.GRATUITO),
    INAFECTO_RETIRO_POR_CONVENIO_COLECTIVO("34", false, false, Catalog5.GRATUITO),
    INAFECTO_RETIRO_POR_PREMIO("35", false, false, Catalog5.GRATUITO),
    INAFECTO_RETIRO_POR_PUBLICIDAD("36", false, false, Catalog5.GRATUITO),
    EXPORTACION("40", false, false, Catalog5.GRATUITO);

    private final String code;
    private final boolean gravado;
    private final boolean operacionOnerosa;
    private final Catalog5 taxCategory;

    Catalog7(String code, boolean gravado, boolean operacionOnerosa, Catalog5 taxCategory) {
        this.code = code;
        this.gravado = gravado;
        this.operacionOnerosa = operacionOnerosa;
        this.taxCategory = taxCategory;
    }

    @Override
    public String getCode() {
        return code;
    }

    public boolean isOperacionOnerosa() {
        return operacionOnerosa;
    }

    public boolean isGravado() {
        return gravado;
    }

    public Catalog5 getTaxCategory() {
        return taxCategory;
    }
}
