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
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog1;
import org.openublpe.xmlbuilder.core.models.catalogs.constraints.CatalogConstraint;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class PerceptionRetentionComprobanteInputModel {

    @Size(min = 3, max = 3)
    @Schema(example = "PEN", description = "Moneda en la que se emitió el comprobante a Retener o Percibir")
    private String moneda;

    @NotNull
    @CatalogConstraint(value = Catalog1.class)
    @Schema(example = "FACTURA", description = "Catalog 01", enumeration = {
            "FACTURA", "01",
            "BOLETA", "03",
            "NOTA_CREDITO", "07",
            "NOTA_DEBITO", "08"
    })
    private String tipo;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[F|B|0-9].*$")
    @Schema(example = "F001-1", description = "Serie y número del comprobante")
    private String serieNumero;

    @NotNull
    @Schema(example = "1585398109198", description = "Fecha expresada en milliseconds")
    private Long fechaEmision;

    @NotNull
    @Positive
    @Digits(integer = 100, fraction = 2)
    @Schema(example = "100", description = "Importe total del comprobante")
    private BigDecimal importeTotal;

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSerieNumero() {
        return serieNumero;
    }

    public void setSerieNumero(String serieNumero) {
        this.serieNumero = serieNumero;
    }

    public Long getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Long fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }
}
