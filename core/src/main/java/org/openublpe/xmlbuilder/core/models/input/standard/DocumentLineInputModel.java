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
import org.openublpe.xmlbuilder.core.models.input.standard.invoice.CargoDescuentoInputModel;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

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
    private BigDecimal valorUnitario;

    /**
     * Precio con impuestos
     */
    @Positive
    @Digits(integer = 100, fraction = 2)
    private BigDecimal precioUnitario;

    @CatalogConstraint(value = Catalog7.class)
    private String tipoIgv;

    private String tipoIsc;

    private boolean icb;

    @Min(0)
    private BigDecimal otrosTributos;

    private String codigoProducto;
    private String codigoProductoSunat;

    @Valid
    private List<CargoDescuentoInputModel> cargos;

    @Valid
    private List<CargoDescuentoInputModel> descuentos;

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

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public String getTipoIgv() {
        return tipoIgv;
    }

    public void setTipoIgv(String tipoIgv) {
        this.tipoIgv = tipoIgv;
    }

    public String getTipoIsc() {
        return tipoIsc;
    }

    public void setTipoIsc(String tipoIsc) {
        this.tipoIsc = tipoIsc;
    }

    public boolean isIcb() {
        return icb;
    }

    public void setIcb(boolean icb) {
        this.icb = icb;
    }

    public BigDecimal getOtrosTributos() {
        return otrosTributos;
    }

    public void setOtrosTributos(BigDecimal otrosTributos) {
        this.otrosTributos = otrosTributos;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getCodigoProductoSunat() {
        return codigoProductoSunat;
    }

    public void setCodigoProductoSunat(String codigoProductoSunat) {
        this.codigoProductoSunat = codigoProductoSunat;
    }

    public List<CargoDescuentoInputModel> getCargos() {
        return cargos;
    }

    public void setCargos(List<CargoDescuentoInputModel> cargos) {
        this.cargos = cargos;
    }

    public List<CargoDescuentoInputModel> getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(List<CargoDescuentoInputModel> descuentos) {
        this.descuentos = descuentos;
    }
}
