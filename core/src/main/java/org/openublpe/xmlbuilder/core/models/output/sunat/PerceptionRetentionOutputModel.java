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

import org.openublpe.xmlbuilder.core.models.output.common.ClienteOutputModel;
import org.openublpe.xmlbuilder.core.models.output.common.FirmanteOutputModel;
import org.openublpe.xmlbuilder.core.models.output.common.ProveedorOutputModel;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

public abstract class PerceptionRetentionOutputModel {

    @Valid
    @NotNull ClienteOutputModel cliente;
    @NotBlank
    private String serieNumero;
    @NotBlank
    private String fechaEmision;
    @Size(min = 3, max = 3)
    private String moneda;
    private String observacion;
    @NotNull
    @Min(0)
    private BigDecimal importeTotalPercibidoRetenido;
    @NotNull
    @Min(0)
    private BigDecimal importeTotalCobradoPagado;
    @Valid
    @NotNull
    private ProveedorOutputModel proveedor;
    @Valid
    @NotNull
    private FirmanteOutputModel firmante;

    @NotNull
    @Valid
    @NotEmpty
    private List<PerceptionRetentionLineOutputModel> detalle;

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

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public BigDecimal getImporteTotalPercibidoRetenido() {
        return importeTotalPercibidoRetenido;
    }

    public void setImporteTotalPercibidoRetenido(BigDecimal importeTotalPercibidoRetenido) {
        this.importeTotalPercibidoRetenido = importeTotalPercibidoRetenido;
    }

    public ProveedorOutputModel getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorOutputModel proveedor) {
        this.proveedor = proveedor;
    }

    public ClienteOutputModel getCliente() {
        return cliente;
    }

    public void setCliente(ClienteOutputModel cliente) {
        this.cliente = cliente;
    }

    public FirmanteOutputModel getFirmante() {
        return firmante;
    }

    public void setFirmante(FirmanteOutputModel firmante) {
        this.firmante = firmante;
    }

    public List<PerceptionRetentionLineOutputModel> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<PerceptionRetentionLineOutputModel> detalle) {
        this.detalle = detalle;
    }

    public BigDecimal getImporteTotalCobradoPagado() {
        return importeTotalCobradoPagado;
    }

    public void setImporteTotalCobradoPagado(BigDecimal importeTotalCobradoPagado) {
        this.importeTotalCobradoPagado = importeTotalCobradoPagado;
    }
}
