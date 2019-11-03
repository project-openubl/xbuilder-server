package org.openublpe.xmlbuilder.models.output.creditNote;

import org.openublpe.xmlbuilder.models.output.AbstractNoteOutputDocumentModel;
import org.openublpe.xmlbuilder.models.ubl.Catalog9;

import javax.validation.constraints.NotNull;

public class CreditNoteOutputModel extends AbstractNoteOutputDocumentModel {

    @NotNull
    private Catalog9 tipoNota;

    public Catalog9 getTipoNota() {
        return tipoNota;
    }

    public void setTipoNota(Catalog9 tipoNota) {
        this.tipoNota = tipoNota;
    }
}
