package org.openublpe.xmlbuilder.models.output.standard.note.debitNote;

import org.openublpe.xmlbuilder.models.catalogs.Catalog10;
import org.openublpe.xmlbuilder.models.output.standard.note.NoteOutputModel;

import javax.validation.constraints.NotNull;

public class DebitNoteOutputModel extends NoteOutputModel {

    @NotNull
    private Catalog10 tipoNota;

    public Catalog10 getTipoNota() {
        return tipoNota;
    }

    public void setTipoNota(Catalog10 tipoNota) {
        this.tipoNota = tipoNota;
    }
}
