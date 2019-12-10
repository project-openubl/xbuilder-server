package org.openublpe.xmlbuilder.core.models.output.standard;

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog16;

import java.math.BigDecimal;

public class DetallePrecioReferenciaOutputModel {

    private BigDecimal precio;
    private Catalog16 tipoPrecio;

    public DetallePrecioReferenciaOutputModel() {
    }

    public DetallePrecioReferenciaOutputModel(BigDecimal precio, Catalog16 tipoPrecio) {
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
