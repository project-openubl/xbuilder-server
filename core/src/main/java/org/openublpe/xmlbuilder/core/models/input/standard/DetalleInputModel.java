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
package org.openublpe.xmlbuilder.core.models.input.standard;

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog7;
import org.openublpe.xmlbuilder.core.models.catalogs.constraints.CatalogConstraint;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class DetalleInputModel {

    @NotNull
    @NotBlank
    private String descripcion;

    private String unidadMedida;

    @NotNull
    @Positive
    @Digits(integer = Integer.MAX_VALUE, fraction = 3)
    private BigDecimal cantidad;

    /**
     * Precio con impuestos
     */
    @NotNull
    @Positive
    private BigDecimal precioUnitario;

    @CatalogConstraint(value = Catalog7.class)
    private String tipoIGV;
    private boolean icb;

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

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

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public String getTipoIGV() {
        return tipoIGV;
    }

    public void setTipoIGV(String tipoIGV) {
        this.tipoIGV = tipoIGV;
    }

    public boolean isIcb() {
        return icb;
    }

    public void setIcb(boolean icb) {
        this.icb = icb;
    }
}
