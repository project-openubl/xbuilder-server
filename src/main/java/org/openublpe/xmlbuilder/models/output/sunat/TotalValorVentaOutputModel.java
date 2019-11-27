package org.openublpe.xmlbuilder.models.output.sunat;

import org.openublpe.xmlbuilder.models.catalogs.Catalog7_1;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class TotalValorVentaOutputModel {

    @NotNull
    @Min(0)
    private BigDecimal importe;

    @NotNull
    private Catalog7_1 tipo;

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public Catalog7_1 getTipo() {
        return tipo;
    }

    public void setTipo(Catalog7_1 tipo) {
        this.tipo = tipo;
    }
}
