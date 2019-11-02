package org.openublpe.xmlbuilder.models.ubl;

public enum Catalog7 implements Catalog {

    GRAVADO_OPERACION_ONEROSA("10", true, true),
    GRAVADO_RETIRO_POR_PREMIO("11", true, false),
    GRAVADO_RETIRO_POR_DONACION("12", true, false),
    GRAVADO_RETIRO("13", true, false),
    GRAVADO_RETIRO_POR_PUBLICIDAD("14", true, false),
    GRAVADO_BONIFICACIONES("15", true, false),
    GRAVADO_RETIRO_POR_ENTREGA_A_TRABAJADORES("16", true, false),
    GRAVADO_IVAP("17", true, false),
    EXONERADO_OPERACION_ONEROSA("20", false, true),
    EXONERADO_TRANSFERENCIA_GRATUITA("21", false, false),
    INAFECTO_OPERACION_ONEROSA("30", false, true),
    INAFECTO_RETIRO_POR_BONIFICACION("31", false, false),
    INAFECTO_RETIRO("32", false, false),
    INAFECTO_RETIRO_POR_MUESTRAS_MEDICAS("33", false, false),
    INAFECTO_RETIRO_POR_CONVENIO_COLECTIVO("34", false, false),
    INAFECTO_RETIRO_POR_PREMIO("35", false, false),
    INAFECTO_RETIRO_POR_PUBLICIDAD("36", false, false),
    EXPORTACION("40", false, false);

    private final String code;
    private final boolean gravado;
    private final boolean operacionOnerosa;

    @Override
    public String getCode() {
        return code;
    }

    Catalog7(String code, boolean gravado, boolean operacionOnerosa) {
        this.code = code;
        this.gravado = gravado;
        this.operacionOnerosa = operacionOnerosa;
    }

    public boolean isOperacionOnerosa() {
        return operacionOnerosa;
    }

    public boolean isGravado() {
        return gravado;
    }
}
