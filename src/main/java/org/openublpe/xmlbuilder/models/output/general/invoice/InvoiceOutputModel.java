package org.openublpe.xmlbuilder.models.output.general.invoice;

import org.openublpe.xmlbuilder.models.output.general.AbstractOutputDocumentModel;
import org.openublpe.xmlbuilder.models.ubl.Catalog1;

import javax.validation.constraints.NotNull;

public class InvoiceOutputModel extends AbstractOutputDocumentModel {

    @NotNull
    private Catalog1 tipoInvoice;

    public Catalog1 getTipoInvoice() {
        return tipoInvoice;
    }

    public void setTipoInvoice(Catalog1 tipoInvoice) {
        this.tipoInvoice = tipoInvoice;
    }

}
