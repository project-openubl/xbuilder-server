package org.openublpe.xmlbuilder.models.output.general;

import org.openublpe.xmlbuilder.models.ubl.Catalog5;
import org.openublpe.xmlbuilder.models.ubl.Catalog7;

import java.math.BigDecimal;

public class DetalleImpuestoOutputModel {

    private BigDecimal importe;
    private BigDecimal baseImponible;
    private Catalog5 categoria;
    private Catalog7 tipo;

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

    public Catalog7 getTipo() {
        return tipo;
    }

    public void setTipo(Catalog7 tipo) {
        this.tipo = tipo;
    }
}
