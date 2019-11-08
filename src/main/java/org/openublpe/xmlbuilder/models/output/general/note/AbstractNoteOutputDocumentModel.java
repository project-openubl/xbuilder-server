package org.openublpe.xmlbuilder.models.output.general.note;

import org.openublpe.xmlbuilder.models.output.general.AbstractOutputDocumentModel;
import org.openublpe.xmlbuilder.models.ubl.Catalog1;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public abstract class AbstractNoteOutputDocumentModel extends AbstractOutputDocumentModel {

    @NotBlank
    private String serieNumeroInvoiceReference;

    @NotBlank
    private String descripcionSustentoInvoiceReference;

    @NotNull
    private Catalog1 tipoInvoiceReference;

    public String getSerieNumeroInvoiceReference() {
        return serieNumeroInvoiceReference;
    }

    public void setSerieNumeroInvoiceReference(String serieNumeroInvoiceReference) {
        this.serieNumeroInvoiceReference = serieNumeroInvoiceReference;
    }

    public String getDescripcionSustentoInvoiceReference() {
        return descripcionSustentoInvoiceReference;
    }

    public void setDescripcionSustentoInvoiceReference(String descripcionSustentoInvoiceReference) {
        this.descripcionSustentoInvoiceReference = descripcionSustentoInvoiceReference;
    }

    public Catalog1 getTipoInvoiceReference() {
        return tipoInvoiceReference;
    }

    public void setTipoInvoiceReference(Catalog1 tipoInvoiceReference) {
        this.tipoInvoiceReference = tipoInvoiceReference;
    }
}
