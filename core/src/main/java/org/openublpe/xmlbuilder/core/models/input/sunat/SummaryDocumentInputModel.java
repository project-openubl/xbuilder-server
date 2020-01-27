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
package org.openublpe.xmlbuilder.core.models.input.sunat;

import org.openublpe.xmlbuilder.core.models.input.common.FirmanteInputModel;
import org.openublpe.xmlbuilder.core.models.input.common.ProveedorInputModel;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class SummaryDocumentInputModel {

    @Min(1)
    @NotNull
    private Integer numero;

    private String moneda;
    private Long fechaEmision;

    @NotNull
    private Long fechaEmisionDocumentReference;

    @Valid
    @NotNull
    private ProveedorInputModel proveedor;

    @Valid
    @NotNull
    private FirmanteInputModel firmante;

    @NotNull
    @Valid
    @NotEmpty
    private List<SummaryDocumentLineInputModel> detalle;

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Long getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Long fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Long getFechaEmisionDocumentReference() {
        return fechaEmisionDocumentReference;
    }

    public void setFechaEmisionDocumentReference(Long fechaEmisionDocumentReference) {
        this.fechaEmisionDocumentReference = fechaEmisionDocumentReference;
    }

    public ProveedorInputModel getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorInputModel proveedor) {
        this.proveedor = proveedor;
    }

    public FirmanteInputModel getFirmante() {
        return firmante;
    }

    public void setFirmante(FirmanteInputModel firmante) {
        this.firmante = firmante;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public List<SummaryDocumentLineInputModel> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<SummaryDocumentLineInputModel> detalle) {
        this.detalle = detalle;
    }
}
