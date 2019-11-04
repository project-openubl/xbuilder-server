package org.openublpe.xmlbuilder.models.input.general.note;

import org.openublpe.xmlbuilder.models.input.general.AbstractInputDocumentModel;

import javax.validation.constraints.NotBlank;

public abstract class AbstractNoteInputDocumentModel extends AbstractInputDocumentModel {

    @NotBlank
    private String serieNumeroInvoiceReference;

    @NotBlank
    private String descripcionSustentoInvoiceReference;

    private String tipoNota;

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

    public String getTipoNota() {
        return tipoNota;
    }

    public void setTipoNota(String tipoNota) {
        this.tipoNota = tipoNota;
    }
}
