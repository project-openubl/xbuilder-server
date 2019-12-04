package org.openublpe.xmlbuilder.models.input.standard.note;

import org.openublpe.xmlbuilder.models.input.standard.DocumentInputModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public abstract class NoteInputModel extends DocumentInputModel {

    @NotNull
    @NotBlank
    private String serieNumeroInvoiceReference;

    @NotNull
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
