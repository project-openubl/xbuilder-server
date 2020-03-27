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
package org.openublpe.xmlbuilder.core.models.output.standard;

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog12;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class DocumentoTributarioRelacionadoOutputModel {

    @NotBlank
    private String serieNumero;

    @NotNull
    private Catalog12 tipoDocumento;

    public String getSerieNumero() {
        return serieNumero;
    }

    public void setSerieNumero(String serieNumero) {
        this.serieNumero = serieNumero;
    }

    public Catalog12 getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(Catalog12 tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
}
