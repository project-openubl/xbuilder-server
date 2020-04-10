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

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.openublpe.xmlbuilder.core.models.input.common.ClienteInputModel;
import org.openublpe.xmlbuilder.core.models.input.common.FirmanteInputModel;
import org.openublpe.xmlbuilder.core.models.input.common.ProveedorInputModel;
import org.openublpe.xmlbuilder.core.models.input.constraints.DocumentInputModel_PuedeCrearComprobanteConSerieFConstraint;
import org.openublpe.xmlbuilder.core.models.input.constraints.DocumentInputModel_PuedeCrearComprobanteConSerieFGroupValidation;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@DocumentInputModel_PuedeCrearComprobanteConSerieFConstraint(groups = DocumentInputModel_PuedeCrearComprobanteConSerieFGroupValidation.class)
public abstract class DocumentInputModel {

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[F|f|B|b].*$")
    @Size(min = 4, max = 4)
    @Schema(example = "F001")
    protected String serie;

    @NotNull
    @Min(1)
    @Max(99999999)
    @Schema(example = "1")
    private Integer numero;

    @Schema(example = "1585398109198", description = "Fecha expresada en milliseconds")
    private Long fechaEmision;

    @NotNull
    @Valid
    private ClienteInputModel cliente;

    @NotNull
    @Valid
    private ProveedorInputModel proveedor;

    @Valid
    @Schema(description = "Si 'firmante' es NULL se usa datos del proveedor")
    private FirmanteInputModel firmante;

    @NotNull
    @NotEmpty
    @Valid
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

    public Long getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Long fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public ClienteInputModel getCliente() {
        return cliente;
    }

    public void setCliente(ClienteInputModel cliente) {
        this.cliente = cliente;
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

    public List<DocumentLineInputModel> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<DocumentLineInputModel> detalle) {
        this.detalle = detalle;
    }

}
