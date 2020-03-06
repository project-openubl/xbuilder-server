package org.openublpe.xmlbuilder.core.models.output.standard;

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog7;

import javax.validation.constraints.NotNull;

public class ImpuestoDetalladoIGVOutputModel extends ImpuestoDetalladoOutputModel {

    @NotNull
    private Catalog7 tipo;

    public Catalog7 getTipo() {
        return tipo;
    }

    public void setTipo(Catalog7 tipo) {
        this.tipo = tipo;
    }
}
