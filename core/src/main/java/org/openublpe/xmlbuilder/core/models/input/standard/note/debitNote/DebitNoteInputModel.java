package org.openublpe.xmlbuilder.core.models.input.standard.note.debitNote;

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog10;
import org.openublpe.xmlbuilder.core.models.catalogs.constraints.CatalogConstraint;
import org.openublpe.xmlbuilder.core.models.input.standard.note.NoteInputModel;

public class DebitNoteInputModel extends NoteInputModel {

    @CatalogConstraint(value = Catalog10.class)
    private String tipoNota;

    public String getTipoNota() {
        return tipoNota;
    }

    public void setTipoNota(String tipoNota) {
        this.tipoNota = tipoNota;
    }
}
