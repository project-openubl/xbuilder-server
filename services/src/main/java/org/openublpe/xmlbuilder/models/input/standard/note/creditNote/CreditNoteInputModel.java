package org.openublpe.xmlbuilder.models.input.standard.note.creditNote;

import org.openublpe.xmlbuilder.models.catalogs.Catalog9;
import org.openublpe.xmlbuilder.models.catalogs.constraints.CatalogConstraint;
import org.openublpe.xmlbuilder.models.input.standard.note.NoteInputModel;

public class CreditNoteInputModel extends NoteInputModel {

    @CatalogConstraint(value = Catalog9.class)
    private String tipoNota;

    public String getTipoNota() {
        return tipoNota;
    }

    public void setTipoNota(String tipoNota) {
        this.tipoNota = tipoNota;
    }
}
