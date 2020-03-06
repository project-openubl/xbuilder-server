/**
 * Copyright 2019 Project OpenUBL, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 * <p>
 * Licensed under the Eclipse Public License - v 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.eclipse.org/legal/epl-2.0/
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openublpe.xmlbuilder.core.models.input.standard;

import org.openublpe.xmlbuilder.core.models.input.common.ClienteInputModel;
import org.openublpe.xmlbuilder.core.models.input.common.FirmanteInputModel;
import org.openublpe.xmlbuilder.core.models.input.common.ProveedorInputModel;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public abstract class DocumentInputModel {

    @NotBlank
    @Pattern(regexp = "^[F|B].*$")
    @Size(min = 4, max = 4)
    protected String serie;

    @Min(1)
    @Max(99999999)
    @NotNull
    private Integer numero;

    @Size(min = 3, max = 3)
    private String moneda;

    private Long fechaEmision;
    private Long fechaVencimiento;

    private String observacion;
    private String ordenDeCompra;

    @Valid
    @NotNull
    private ProveedorInputModel proveedor;

    @Valid
    @NotNull
    private ClienteInputModel cliente;

    @Valid
    private FirmanteInputModel firmante;

    @Valid
    private List<GuiaRemisionRelacionadaInputModel> guiasDeRemisionRelacionadas;

    @Valid
    private List<DocumentoTributarioRelacionadoInputModel> documentosTributariosRelacionados;

    @Valid
    @NotEmpty
    private List<DocumentLineInputModel> detalle;

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

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

    public Long getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Long fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getOrdenDeCompra() {
        return ordenDeCompra;
    }

    public void setOrdenDeCompra(String ordenDeCompra) {
        this.ordenDeCompra = ordenDeCompra;
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

    public List<GuiaRemisionRelacionadaInputModel> getGuiasDeRemisionRelacionadas() {
        return guiasDeRemisionRelacionadas;
    }

    public void setGuiasDeRemisionRelacionadas(List<GuiaRemisionRelacionadaInputModel> guiasDeRemisionRelacionadas) {
        this.guiasDeRemisionRelacionadas = guiasDeRemisionRelacionadas;
    }

    public List<DocumentoTributarioRelacionadoInputModel> getDocumentosTributariosRelacionados() {
        return documentosTributariosRelacionados;
    }

    public void setDocumentosTributariosRelacionados(List<DocumentoTributarioRelacionadoInputModel> documentosTributariosRelacionados) {
        this.documentosTributariosRelacionados = documentosTributariosRelacionados;
    }

    public List<DocumentLineInputModel> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<DocumentLineInputModel> detalle) {
        this.detalle = detalle;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
