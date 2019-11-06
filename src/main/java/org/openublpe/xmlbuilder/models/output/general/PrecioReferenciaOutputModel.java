package org.openublpe.xmlbuilder.models.output.general;

import org.openublpe.xmlbuilder.models.ubl.Catalog16;

import java.math.BigDecimal;

public class PrecioReferenciaOutputModel {

    private BigDecimal precio;
    private Catalog16 tipoPrecio;

    public PrecioReferenciaOutputModel() {
    }

    public PrecioReferenciaOutputModel(BigDecimal precio, Catalog16 tipoPrecio) {
        this.precio = precio;
        this.tipoPrecio = tipoPrecio;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Catalog16 getTipoPrecio() {
        return tipoPrecio;
    }

    public void setTipoPrecio(Catalog16 tipoPrecio) {
        this.tipoPrecio = tipoPrecio;
    }
}
