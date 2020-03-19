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

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog7;
import org.openublpe.xmlbuilder.core.models.catalogs.constraints.CatalogConstraint;
import org.openublpe.xmlbuilder.core.models.input.constraints.DocumentLineInputModel_PrecioConstraint;
import org.openublpe.xmlbuilder.core.models.input.constraints.HighLevelGroupValidation;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@DocumentLineInputModel_PrecioConstraint(groups = HighLevelGroupValidation.class)
public class DocumentLineInputModel {

    @NotBlank
    private String descripcion;

    private String unidadMedida;

    @NotNull
    @Positive
    @Digits(integer = 100, fraction = 3)
    private BigDecimal cantidad;

    /**
     * Precio sin impuestos
     */
    @Positive
    @Digits(integer = 100, fraction = 2)
    private BigDecimal precioSinImpuestos;

    /**
     * Precio con impuestos
     */
    @Positive
    @Digits(integer = 100, fraction = 2)
    private BigDecimal precioConImpuestos;

    @CatalogConstraint(value = Catalog7.class)
    private String tipoIgv;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioSinImpuestos() {
        return precioSinImpuestos;
    }

    public void setPrecioSinImpuestos(BigDecimal precioSinImpuestos) {
        this.precioSinImpuestos = precioSinImpuestos;
    }

    public BigDecimal getPrecioConImpuestos() {
        return precioConImpuestos;
    }

    public void setPrecioConImpuestos(BigDecimal precioConImpuestos) {
        this.precioConImpuestos = precioConImpuestos;
    }

    public String getTipoIgv() {
        return tipoIgv;
    }

    public void setTipoIgv(String tipoIgv) {
        this.tipoIgv = tipoIgv;
    }

    public static final class Builder {
        private String descripcion;
        private String unidadMedida;
        private BigDecimal cantidad;
        private BigDecimal precioSinImpuestos;
        private BigDecimal precioConImpuestos;
        private String tipoIgv;

        private Builder() {
        }

        public static Builder aDocumentLineInputModel() {
            return new Builder();
        }

        public Builder withDescripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public Builder withUnidadMedida(String unidadMedida) {
            this.unidadMedida = unidadMedida;
            return this;
        }

        public Builder withCantidad(BigDecimal cantidad) {
            this.cantidad = cantidad;
            return this;
        }

        public Builder withPrecioSinImpuestos(BigDecimal precioSinImpuestos) {
            this.precioSinImpuestos = precioSinImpuestos;
            return this;
        }

        public Builder withPrecioConImpuestos(BigDecimal precioConImpuestos) {
            this.precioConImpuestos = precioConImpuestos;
            return this;
        }

        public Builder withTipoIgv(String tipoIgv) {
            this.tipoIgv = tipoIgv;
            return this;
        }

        public DocumentLineInputModel build() {
            DocumentLineInputModel documentLineInputModel = new DocumentLineInputModel();
            documentLineInputModel.setDescripcion(descripcion);
            documentLineInputModel.setUnidadMedida(unidadMedida);
            documentLineInputModel.setCantidad(cantidad);
            documentLineInputModel.setPrecioSinImpuestos(precioSinImpuestos);
            documentLineInputModel.setPrecioConImpuestos(precioConImpuestos);
            documentLineInputModel.setTipoIgv(tipoIgv);
            return documentLineInputModel;
        }
    }
}
