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
import org.openublpe.xmlbuilder.core.models.catalogs.constraints.CatalogConstraint;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class PerceptionRetentionLineInputModel {

    @NotNull
    @CatalogConstraint(value = Catalog1.class)
    private String tipoComprobante;

    @NotBlank
    @Pattern(regexp = "^[F|B|0-9].*$")
    private String serieNumeroComprobante;

    @NotNull
    private Long fechaEmisionComprobante;

    @Min(0)
    @NotNull
    private BigDecimal importeTotalComprobante;

    @Size(min = 3, max = 3)
    private String monedaComprobante;

    @Min(1)
    private Integer numeroCobroPago;
    private Long fechaCobroPago;

    @Min(0)
    private BigDecimal importeCobroPago;

    public String getSerieNumeroComprobante() {
        return serieNumeroComprobante;
    }

    public void setSerieNumeroComprobante(String serieNumeroComprobante) {
        this.serieNumeroComprobante = serieNumeroComprobante;
    }

    public String getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public Long getFechaEmisionComprobante() {
        return fechaEmisionComprobante;
    }

    public void setFechaEmisionComprobante(Long fechaEmisionComprobante) {
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

    public Long getFechaCobroPago() {
        return fechaCobroPago;
    }

    public void setFechaCobroPago(Long fechaCobroPago) {
        this.fechaCobroPago = fechaCobroPago;
    }

    public BigDecimal getImporteCobroPago() {
        return importeCobroPago;
    }

    public void setImporteCobroPago(BigDecimal importeCobroPago) {
        this.importeCobroPago = importeCobroPago;
    }

}
