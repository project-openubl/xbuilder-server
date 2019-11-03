package org.openublpe.xmlbuilder.models.output.debitNote;

import org.openublpe.xmlbuilder.models.output.AbstractNoteOutputDocumentModel;
import org.openublpe.xmlbuilder.models.ubl.Catalog10;

import javax.validation.constraints.NotNull;

public class DebitNoteOutputModel extends AbstractNoteOutputDocumentModel {

    @NotNull
    private Catalog10 tipoNota;

    public Catalog10 getTipoNota() {
        return tipoNota;
    }

    public void setTipoNota(Catalog10 tipoNota) {
        this.tipoNota = tipoNota;
    }
}
