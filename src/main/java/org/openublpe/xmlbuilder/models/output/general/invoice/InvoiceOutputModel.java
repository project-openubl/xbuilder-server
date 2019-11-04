package org.openublpe.xmlbuilder.models.output.general.invoice;

import org.openublpe.xmlbuilder.models.output.general.AbstractOutputDocumentModel;
import org.openublpe.xmlbuilder.models.ubl.Catalog1;

import javax.validation.constraints.NotNull;

public class InvoiceOutputModel extends AbstractOutputDocumentModel {

    @NotNull
    private Catalog1 tipoComprobante;

    public Catalog1 getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(Catalog1 tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

}
