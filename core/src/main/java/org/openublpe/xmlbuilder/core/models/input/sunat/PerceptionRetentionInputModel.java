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

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.openublpe.xmlbuilder.core.models.input.common.ClienteInputModel;
import org.openublpe.xmlbuilder.core.models.input.common.FirmanteInputModel;
import org.openublpe.xmlbuilder.core.models.input.common.ProveedorInputModel;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

public abstract class PerceptionRetentionInputModel {

    @NotNull
    @Min(1)
    @Max(99999999)
    @Schema(example = "1")
    private Integer numero;

    @Schema(example = "1585398109198", description = "Fecha expresada en milliseconds")
    private Long fechaEmision;

    @Schema(example = "mi observación")
    private String observacion;

    @NotNull
    @Valid
    private ProveedorInputModel proveedor;

    @NotNull
    @Valid
    private ClienteInputModel cliente;

    @Valid
    @Schema(description = "Si 'firmante' es NULL se usa datos del proveedor")
    private FirmanteInputModel firmante;

    @NotNull
    @NotEmpty
    @Valid
    private List<PerceptionRetentionLineInputModel> detalle;

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Long getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Long fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public ProveedorInputModel getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorInputModel proveedor) {
        this.proveedor = proveedor;
    }

    public ClienteInputModel getCliente() {
        return cliente;
    }

    public void setCliente(ClienteInputModel cliente) {
        this.cliente = cliente;
    }

    public FirmanteInputModel getFirmante() {
        return firmante;
    }

    public void setFirmante(FirmanteInputModel firmante) {
        this.firmante = firmante;
    }

    public List<PerceptionRetentionLineInputModel> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<PerceptionRetentionLineInputModel> detalle) {
        this.detalle = detalle;
    }

}
