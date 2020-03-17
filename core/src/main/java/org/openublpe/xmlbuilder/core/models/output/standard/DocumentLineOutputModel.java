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

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class DocumentLineOutputModel {

    @NotBlank
    private String descripcion;

    @NotBlank
    private String unidadMedida;

    @NotNull
    @Positive
    @Digits(integer = 100, fraction = 3)
    private BigDecimal cantidad;

    /**
     * Precio sin impuestos
     */
    @Min(0)
    @NotNull
    @Digits(integer = 100, fraction = 2)
    private BigDecimal precioSinImpuestos;

    /**
     * Precio con impuestos
     */
    @Min(0)
    @NotNull
    @Digits(integer = 100, fraction = 2)
    private BigDecimal precioConImpuestos;

    @Valid
    @NotNull
    private DocumentLinePrecioReferenciaOutputModel precioDeReferencia;

    @Min(0)
    @NotNull
    @Digits(integer = 100, fraction = 2)
    private BigDecimal valorVentaSinImpuestos;

    @NotNull
    @Valid
    private DocumentLineImpuestosOutputModel impuestos;

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

    public DocumentLinePrecioReferenciaOutputModel getPrecioDeReferencia() {
        return precioDeReferencia;
    }

    public void setPrecioDeReferencia(DocumentLinePrecioReferenciaOutputModel precioDeReferencia) {
        this.precioDeReferencia = precioDeReferencia;
    }

    public BigDecimal getValorVentaSinImpuestos() {
        return valorVentaSinImpuestos;
    }

    public void setValorVentaSinImpuestos(BigDecimal valorVentaSinImpuestos) {
        this.valorVentaSinImpuestos = valorVentaSinImpuestos;
    }

    public DocumentLineImpuestosOutputModel getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(DocumentLineImpuestosOutputModel impuestos) {
        this.impuestos = impuestos;
    }

    public static final class Builder {
        private String descripcion;
        private String unidadMedida;
        private BigDecimal cantidad;
        private BigDecimal precioSinImpuestos;
        private BigDecimal precioConImpuestos;
        private DocumentLinePrecioReferenciaOutputModel precioDeReferencia;
        private BigDecimal valorVentaSinImpuestos;
        private DocumentLineImpuestosOutputModel impuestos;

        private Builder() {
        }

        public static Builder aDocumentLineOutputModel() {
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

        public Builder withPrecioDeReferencia(DocumentLinePrecioReferenciaOutputModel precioDeReferencia) {
            this.precioDeReferencia = precioDeReferencia;
            return this;
        }

        public Builder withValorVentaSinImpuestos(BigDecimal valorVenta) {
            this.valorVentaSinImpuestos = valorVenta;
            return this;
        }

        public Builder withImpuestos(DocumentLineImpuestosOutputModel impuestos) {
            this.impuestos = impuestos;
            return this;
        }

        public DocumentLineOutputModel build() {
            DocumentLineOutputModel documentLineOutputModel = new DocumentLineOutputModel();
            documentLineOutputModel.setDescripcion(descripcion);
            documentLineOutputModel.setUnidadMedida(unidadMedida);
            documentLineOutputModel.setCantidad(cantidad);
            documentLineOutputModel.setPrecioSinImpuestos(precioSinImpuestos);
            documentLineOutputModel.setPrecioConImpuestos(precioConImpuestos);
            documentLineOutputModel.setPrecioDeReferencia(precioDeReferencia);
            documentLineOutputModel.setValorVentaSinImpuestos(valorVentaSinImpuestos);
            documentLineOutputModel.setImpuestos(impuestos);
            return documentLineOutputModel;
        }
    }
}
