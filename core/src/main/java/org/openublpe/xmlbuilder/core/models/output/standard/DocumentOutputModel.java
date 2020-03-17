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
package org.openublpe.xmlbuilder.core.models.output.standard;

import org.openublpe.xmlbuilder.core.models.output.common.ClienteOutputModel;
import org.openublpe.xmlbuilder.core.models.output.common.FirmanteOutputModel;
import org.openublpe.xmlbuilder.core.models.output.common.ProveedorOutputModel;
import org.openublpe.xmlbuilder.core.models.output.constraints.DocumentOutputModelConstraint;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@DocumentOutputModelConstraint
public class DocumentOutputModel {

    @NotBlank
    @Size(min = 3, max = 3)
    private String moneda;

    @NotBlank
    private String serieNumero;

    @NotBlank
    private String horaEmision;

    @NotBlank
    private String fechaEmision;

    @Valid
    @NotNull
    private ClienteOutputModel cliente;

    @Valid
    @NotNull
    private FirmanteOutputModel firmante;

    @Valid
    @NotNull
    private ProveedorOutputModel proveedor;

    @Valid
    @NotNull DocumentMonetaryTotalOutputModel totales;

    @Valid
    @NotNull
    private DocumentImpuestosOutputModel impuestos;

    @Valid
    @NotEmpty
    private List<DocumentLineOutputModel> detalle;

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getSerieNumero() {
        return serieNumero;
    }

    public void setSerieNumero(String serieNumero) {
        this.serieNumero = serieNumero;
    }

    public String getHoraEmision() {
        return horaEmision;
    }

    public void setHoraEmision(String horaEmision) {
        this.horaEmision = horaEmision;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
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

    public ProveedorOutputModel getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorOutputModel proveedor) {
        this.proveedor = proveedor;
    }

    public DocumentMonetaryTotalOutputModel getTotales() {
        return totales;
    }

    public void setTotales(DocumentMonetaryTotalOutputModel totales) {
        this.totales = totales;
    }

    public DocumentImpuestosOutputModel getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(DocumentImpuestosOutputModel impuestos) {
        this.impuestos = impuestos;
    }

    public List<DocumentLineOutputModel> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<DocumentLineOutputModel> detalle) {
        this.detalle = detalle;
    }

}
