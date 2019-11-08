package org.openublpe.xmlbuilder.models.output.general;

import org.openublpe.xmlbuilder.models.ubl.Catalog5;

import java.math.BigDecimal;

public class ImpuestoOutputModel {

    private BigDecimal importe;
    private BigDecimal baseImponible;
    private Catalog5 categoria;

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public BigDecimal getBaseImponible() {
        return baseImponible;
    }

    public void setBaseImponible(BigDecimal baseImponible) {
        this.baseImponible = baseImponible;
    }

    public Catalog5 getCategoria() {
        return categoria;
    }

    public void setCategoria(Catalog5 categoria) {
        this.categoria = categoria;
    }
}
