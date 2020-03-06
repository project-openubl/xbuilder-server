package org.openublpe.xmlbuilder.core.models.output.standard;

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog8;

import javax.validation.constraints.NotNull;

public class ImpuestoDetalladoISCOutputModel extends ImpuestoDetalladoOutputModel {

    @NotNull
    private Catalog8 tipo;

    public Catalog8 getTipo() {
        return tipo;
    }

    public void setTipo(Catalog8 tipo) {
        this.tipo = tipo;
    }
}
