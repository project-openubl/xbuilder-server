package org.openublpe.xmlbuilder.models.output.common;

import org.openublpe.xmlbuilder.models.catalogs.Catalog5;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public abstract class ImpuestoDetalladoOutputModel {

    @NotNull
    @Min(0)
    private BigDecimal importe;

    @NotNull
    private Catalog5 categoria;

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public Catalog5 getCategoria() {
        return categoria;
    }

    public void setCategoria(Catalog5 categoria) {
        this.categoria = categoria;
    }

}
