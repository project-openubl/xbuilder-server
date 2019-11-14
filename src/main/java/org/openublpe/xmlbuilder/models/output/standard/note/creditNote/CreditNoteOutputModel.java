package org.openublpe.xmlbuilder.models.output.standard.note.creditNote;

import org.openublpe.xmlbuilder.models.catalogs.Catalog9;
import org.openublpe.xmlbuilder.models.output.standard.note.NoteOutputModel;

import javax.validation.constraints.NotNull;

public class CreditNoteOutputModel extends NoteOutputModel {

    @NotNull
    private Catalog9 tipoNota;

    public Catalog9 getTipoNota() {
        return tipoNota;
    }

    public void setTipoNota(Catalog9 tipoNota) {
        this.tipoNota = tipoNota;
    }
}
