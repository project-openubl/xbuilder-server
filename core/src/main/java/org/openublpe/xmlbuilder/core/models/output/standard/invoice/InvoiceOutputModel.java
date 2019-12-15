package org.openublpe.xmlbuilder.core.models.output.standard.invoice;

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog1;
import org.openublpe.xmlbuilder.core.models.output.standard.DocumentOutputModel;

import javax.validation.constraints.NotNull;

public class InvoiceOutputModel extends DocumentOutputModel {

    @NotNull
    private Catalog1 tipoInvoice;

    public Catalog1 getTipoInvoice() {
        return tipoInvoice;
    }

    public void setTipoInvoice(Catalog1 tipoInvoice) {
        this.tipoInvoice = tipoInvoice;
    }

}
