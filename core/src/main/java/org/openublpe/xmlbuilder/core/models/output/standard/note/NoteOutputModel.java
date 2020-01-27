/**
 * Copyright 2019 Project OpenUBL, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Eclipse Public License - v 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openublpe.xmlbuilder.core.models.output.standard.note;

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog1;
import org.openublpe.xmlbuilder.core.models.output.standard.DocumentOutputModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public abstract class NoteOutputModel extends DocumentOutputModel {

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
