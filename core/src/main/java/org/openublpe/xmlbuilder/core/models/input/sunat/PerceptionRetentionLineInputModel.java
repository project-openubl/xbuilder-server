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

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;

public class PerceptionRetentionLineInputModel {

    @Min(1)
    @Schema(example = "1")
    private Integer numeroCobroPago;

    @Schema(example = "1585398109198", description = "Fecha expresada en milliseconds")
    private Long fechaCobroPago;

    @Positive
    @Schema(example = "1", description = "importe de cobro o pago de la percepcion")
    private BigDecimal importeCobroPago;

    @NotNull
    @Valid
    private PerceptionRetentionComprobanteInputModel comprobante;

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

    public PerceptionRetentionComprobanteInputModel getComprobante() {
        return comprobante;
    }

    public void setComprobante(PerceptionRetentionComprobanteInputModel comprobante) {
        this.comprobante = comprobante;
    }

    public static final class Builder {
        private Integer numeroCobroPago;
        private Long fechaCobroPago;
        private BigDecimal importeCobroPago;
        private PerceptionRetentionComprobanteInputModel comprobante;

        private Builder() {
        }

        public static Builder aPerceptionRetentionLineInputModel() {
            return new Builder();
        }

        public Builder withNumeroCobroPago(Integer numeroCobroPago) {
            this.numeroCobroPago = numeroCobroPago;
            return this;
        }

        public Builder withFechaCobroPago(Long fechaCobroPago) {
            this.fechaCobroPago = fechaCobroPago;
            return this;
        }

        public Builder withImporteCobroPago(BigDecimal importeCobroPago) {
            this.importeCobroPago = importeCobroPago;
            return this;
        }

        public Builder withComprobante(PerceptionRetentionComprobanteInputModel comprobante) {
            this.comprobante = comprobante;
            return this;
        }

        public PerceptionRetentionLineInputModel build() {
            PerceptionRetentionLineInputModel perceptionRetentionLineInputModel = new PerceptionRetentionLineInputModel();
            perceptionRetentionLineInputModel.setNumeroCobroPago(numeroCobroPago);
            perceptionRetentionLineInputModel.setFechaCobroPago(fechaCobroPago);
            perceptionRetentionLineInputModel.setImporteCobroPago(importeCobroPago);
            perceptionRetentionLineInputModel.setComprobante(comprobante);
            return perceptionRetentionLineInputModel;
        }
    }
}
