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

import org.openublpe.xmlbuilder.core.models.output.standard.invoice.CargoDescuentoOutputModel;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

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
    private BigDecimal valorUnitario;

    /**
     * Precio con impuestos
     */
    @Min(0)
    @NotNull
    @Digits(integer = 100, fraction = 2)
    private BigDecimal precioUnitario;

    @Min(0)
    @NotNull
    @Digits(integer = 100, fraction = 2)
    private BigDecimal valorVenta;

    @Valid
    private List<CargoDescuentoOutputModel> cargos;

    @Valid
    private List<CargoDescuentoOutputModel> descuentos;

    @Valid
    @NotNull
    private DetallePrecioReferenciaOutputModel precioDeReferencia;

    @Min(0)
    @NotNull
    @Digits(integer = 100, fraction = 2)
    private BigDecimal importeTotalImpuestos;

    @Valid
    @NotNull
    private ImpuestoDetalladoIGVOutputModel igv;

    @Valid
    private ImpuestoDetalladoICBOutputModel icb;

    @Valid
    private ImpuestoDetalladoISCOutputModel isc;

    @Valid
    private ImpuestoDetalladoOutputModel otroTributo;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
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

    public List<CargoDescuentoOutputModel> getCargos() {
        return cargos;
    }

    public void setCargos(List<CargoDescuentoOutputModel> cargos) {
        this.cargos = cargos;
    }

    public List<CargoDescuentoOutputModel> getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(List<CargoDescuentoOutputModel> descuentos) {
        this.descuentos = descuentos;
    }

    public BigDecimal getImporteTotalImpuestos() {
        return importeTotalImpuestos;
    }

    public void setImporteTotalImpuestos(BigDecimal importeTotalImpuestos) {
        this.importeTotalImpuestos = importeTotalImpuestos;
    }

    public ImpuestoDetalladoIGVOutputModel getIgv() {
        return igv;
    }

    public void setIgv(ImpuestoDetalladoIGVOutputModel igv) {
        this.igv = igv;
    }

    public ImpuestoDetalladoICBOutputModel getIcb() {
        return icb;
    }

    public void setIcb(ImpuestoDetalladoICBOutputModel icb) {
        this.icb = icb;
    }

    public ImpuestoDetalladoOutputModel getOtroTributo() {
        return otroTributo;
    }

    public void setOtroTributo(ImpuestoDetalladoOutputModel otroTributo) {
        this.otroTributo = otroTributo;
    }

    public ImpuestoDetalladoISCOutputModel getIsc() {
        return isc;
    }

    public void setIsc(ImpuestoDetalladoISCOutputModel isc) {
        this.isc = isc;
    }

    public DetallePrecioReferenciaOutputModel getPrecioDeReferencia() {
        return precioDeReferencia;
    }

    public void setPrecioDeReferencia(DetallePrecioReferenciaOutputModel precioDeReferencia) {
        this.precioDeReferencia = precioDeReferencia;
    }

    public BigDecimal getValorVenta() {
        return valorVenta;
    }

    public void setValorVenta(BigDecimal valorVenta) {
        this.valorVenta = valorVenta;
    }
}
