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

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog1;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog19;
import org.openublpe.xmlbuilder.core.models.catalogs.constraints.CatalogConstraint;
import org.openublpe.xmlbuilder.core.models.input.common.ClienteInputModel;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class SummaryDocumentLineInputModel {

    @NotBlank
    private String serieNumero;

    @NotBlank
    @CatalogConstraint(value = Catalog19.class)
    private String tipoOperacion;

    @NotNull
    @CatalogConstraint(value = Catalog1.class)
    private String tipoComprobante;

    @Valid
    @NotNull
    private ClienteInputModel cliente;

    @NotNull
    @Min(0)
    private BigDecimal importeTotal;

    @Min(0)
    private BigDecimal totalOtrosCargos;

    @Min(0)
    private BigDecimal totalOperacionesGravadas;

    @Min(0)
    private BigDecimal totalOperacionesExoneradas;

    @Min(0)
    private BigDecimal totalOperacionesInafectas;

    @Min(0)
    private BigDecimal totalOperacionesGratuitas;

    @NotNull
    @Min(0)
    private BigDecimal igv;

    @Min(0)
    private BigDecimal icb;

    public String getSerieNumero() {
        return serieNumero;
    }

    public void setSerieNumero(String serieNumero) {
        this.serieNumero = serieNumero;
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public String getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public ClienteInputModel getCliente() {
        return cliente;
    }

    public void setCliente(ClienteInputModel cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }

    public BigDecimal getTotalOtrosCargos() {
        return totalOtrosCargos;
    }

    public void setTotalOtrosCargos(BigDecimal totalOtrosCargos) {
        this.totalOtrosCargos = totalOtrosCargos;
    }

    public BigDecimal getTotalOperacionesGravadas() {
        return totalOperacionesGravadas;
    }

    public void setTotalOperacionesGravadas(BigDecimal totalOperacionesGravadas) {
        this.totalOperacionesGravadas = totalOperacionesGravadas;
    }

    public BigDecimal getTotalOperacionesExoneradas() {
        return totalOperacionesExoneradas;
    }

    public void setTotalOperacionesExoneradas(BigDecimal totalOperacionesExoneradas) {
        this.totalOperacionesExoneradas = totalOperacionesExoneradas;
    }

    public BigDecimal getTotalOperacionesInafectas() {
        return totalOperacionesInafectas;
    }

    public void setTotalOperacionesInafectas(BigDecimal totalOperacionesInafectas) {
        this.totalOperacionesInafectas = totalOperacionesInafectas;
    }

    public BigDecimal getTotalOperacionesGratuitas() {
        return totalOperacionesGratuitas;
    }

    public void setTotalOperacionesGratuitas(BigDecimal totalOperacionesGratuitas) {
        this.totalOperacionesGratuitas = totalOperacionesGratuitas;
    }

    public BigDecimal getIgv() {
        return igv;
    }

    public void setIgv(BigDecimal igv) {
        this.igv = igv;
    }

    public BigDecimal getIcb() {
        return icb;
    }

    public void setIcb(BigDecimal icb) {
        this.icb = icb;
    }

}
