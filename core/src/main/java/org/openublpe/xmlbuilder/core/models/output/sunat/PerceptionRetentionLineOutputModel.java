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
package org.openublpe.xmlbuilder.core.models.output.sunat;

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog1;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class PerceptionRetentionLineOutputModel {

    @NotNull
    private Catalog1 tipoComprobante;

    @NotBlank
    @Pattern(regexp = "^[F|B|0-9].*$")
    private String serieNumeroComprobante;

    @NotBlank
    private String fechaEmisionComprobante;

    @Min(0)
    @NotNull
    private BigDecimal importeTotalComprobante;

    @NotBlank
    @Size(min = 3, max = 3)
    private String monedaComprobante;


    //


    @Min(1)
    @NotNull
    private Integer numeroCobroPago;

    @NotBlank
    private String fechaCobroPago;

    @Min(0)
    @NotNull
    private BigDecimal importeCobroPago;


    //


    @Min(0)
    @NotNull
    private BigDecimal importePercibidoRetenido;

    @Min(0)
    @NotNull
    private BigDecimal importeTotalCobradoPagado;

    public Catalog1 getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(Catalog1 tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public String getSerieNumeroComprobante() {
        return serieNumeroComprobante;
    }

    public void setSerieNumeroComprobante(String serieNumeroComprobante) {
        this.serieNumeroComprobante = serieNumeroComprobante;
    }

    public String getFechaEmisionComprobante() {
        return fechaEmisionComprobante;
    }

    public void setFechaEmisionComprobante(String fechaEmisionComprobante) {
        this.fechaEmisionComprobante = fechaEmisionComprobante;
    }

    public BigDecimal getImporteTotalComprobante() {
        return importeTotalComprobante;
    }

    public void setImporteTotalComprobante(BigDecimal importeTotalComprobante) {
        this.importeTotalComprobante = importeTotalComprobante;
    }

    public String getMonedaComprobante() {
        return monedaComprobante;
    }

    public void setMonedaComprobante(String monedaComprobante) {
        this.monedaComprobante = monedaComprobante;
    }

    public Integer getNumeroCobroPago() {
        return numeroCobroPago;
    }

    public void setNumeroCobroPago(Integer numeroCobroPago) {
        this.numeroCobroPago = numeroCobroPago;
    }

    public String getFechaCobroPago() {
        return fechaCobroPago;
    }

    public void setFechaCobroPago(String fechaCobroPago) {
        this.fechaCobroPago = fechaCobroPago;
    }

    public BigDecimal getImporteCobroPago() {
        return importeCobroPago;
    }

    public void setImporteCobroPago(BigDecimal importeCobroPago) {
        this.importeCobroPago = importeCobroPago;
    }

    public BigDecimal getImportePercibidoRetenido() {
        return importePercibidoRetenido;
    }

    public void setImportePercibidoRetenido(BigDecimal importePercibidoRetenido) {
        this.importePercibidoRetenido = importePercibidoRetenido;
    }

    public BigDecimal getImporteTotalCobradoPagado() {
        return importeTotalCobradoPagado;
    }

    public void setImporteTotalCobradoPagado(BigDecimal importeTotalCobradoPagado) {
        this.importeTotalCobradoPagado = importeTotalCobradoPagado;
    }

}
