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
package org.openublpe.xmlbuilder.core.models.output.sunat;

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog1;
import org.openublpe.xmlbuilder.core.models.output.common.FirmanteOutputModel;
import org.openublpe.xmlbuilder.core.models.output.common.ProveedorOutputModel;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class VoidedDocumentOutputModel {

    @NotBlank
    private String serieNumero;

    @NotBlank
    private String fechaEmision;

    @Valid
    @NotNull
    private ProveedorOutputModel proveedor;

    @Valid
    @NotNull
    private FirmanteOutputModel firmante;

    @NotBlank
    private String fechaEmisionDocumentReference;

    @NotNull
    private Catalog1 tipoDocumentReference;

    @NotBlank
    private String serieDocumentReference;

    @NotBlank
    private String numeroDocumentReference;

    @NotBlank
    private String motivoBajaDocumentReference;

    public String getSerieNumero() {
        return serieNumero;
    }

    public void setSerieNumero(String serieNumero) {
        this.serieNumero = serieNumero;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public ProveedorOutputModel getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorOutputModel proveedor) {
        this.proveedor = proveedor;
    }

    public String getFechaEmisionDocumentReference() {
        return fechaEmisionDocumentReference;
    }

    public void setFechaEmisionDocumentReference(String fechaEmisionDocumentReference) {
        this.fechaEmisionDocumentReference = fechaEmisionDocumentReference;
    }

    public Catalog1 getTipoDocumentReference() {
        return tipoDocumentReference;
    }

    public void setTipoDocumentReference(Catalog1 tipoDocumentReference) {
        this.tipoDocumentReference = tipoDocumentReference;
    }

    public String getSerieDocumentReference() {
        return serieDocumentReference;
    }

    public void setSerieDocumentReference(String serieDocumentReference) {
        this.serieDocumentReference = serieDocumentReference;
    }

    public String getNumeroDocumentReference() {
        return numeroDocumentReference;
    }

    public void setNumeroDocumentReference(String numeroDocumentReference) {
        this.numeroDocumentReference = numeroDocumentReference;
    }

    public String getMotivoBajaDocumentReference() {
        return motivoBajaDocumentReference;
    }

    public void setMotivoBajaDocumentReference(String motivoBajaDocumentReference) {
        this.motivoBajaDocumentReference = motivoBajaDocumentReference;
    }

    public FirmanteOutputModel getFirmante() {
        return firmante;
    }

    public void setFirmante(FirmanteOutputModel firmante) {
        this.firmante = firmante;
    }
}
