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

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog52;
import org.openublpe.xmlbuilder.core.models.output.common.ClienteOutputModel;
import org.openublpe.xmlbuilder.core.models.output.common.FirmanteOutputModel;
import org.openublpe.xmlbuilder.core.models.output.common.ProveedorOutputModel;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

public class DocumentOutputModel {

    @NotBlank
    @Size(min = 3, max = 3)
    private String moneda;

    @NotBlank
    private String serieNumero;

    @NotBlank
    private String horaEmision;

    @NotBlank
    private String fechaEmision;

    private String fechaVencimiento;

    private String observacion;
    private String ordenDeCompra;

    private List<Catalog52> leyendas;

    @Valid
    @NotNull
    private ClienteOutputModel cliente;

    @Valid
    @NotNull
    private FirmanteOutputModel firmante;

    @Valid
    @NotNull
    private ProveedorOutputModel proveedor;

    @Valid
    private List<GuiaRemisionRelacionadaOutputModel> guiasDeRemisionRelacionadas;

    @Valid
    private List<DocumentoTributarioRelacionadoOutputModel> documentosTributariosRelacionados;

    @Valid
    @NotEmpty
    private List<DocumentLineOutputModel> detalle;

    @Min(0)
    @NotNull
    @Digits(integer = 100, fraction = 2)
    private BigDecimal importeTotalImpuestos;

    @Valid
    @NotEmpty
    private List<ImpuestoTotalOutputModel> totalImpuestos;

    @Valid
    @NotNull
    private List<ImpuestoTotalICBOutputModel> totalImpuestosIcb;

    @Min(0)
    @Digits(integer = 100, fraction = 2)
    private BigDecimal totalValorVenta;

    @Min(0)
    @Digits(integer = 100, fraction = 2)
    private BigDecimal totalPrecioVenta;

    @Min(0)
    @NotNull
    @Digits(integer = 100, fraction = 2)
    private BigDecimal totalDescuentos;

    @Min(0)
    @NotNull
    @Digits(integer = 100, fraction = 2)
    private BigDecimal totalOtrosCargos;

    @Min(0)
    @NotNull
    @Digits(integer = 100, fraction = 2)
    private BigDecimal importeTotal;

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getSerieNumero() {
        return serieNumero;
    }

    public void setSerieNumero(String serieNumero) {
        this.serieNumero = serieNumero;
    }

    public String getHoraEmision() {
        return horaEmision;
    }

    public void setHoraEmision(String horaEmision) {
        this.horaEmision = horaEmision;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public List<Catalog52> getLeyendas() {
        return leyendas;
    }

    public void setLeyendas(List<Catalog52> leyendas) {
        this.leyendas = leyendas;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getOrdenDeCompra() {
        return ordenDeCompra;
    }

    public void setOrdenDeCompra(String ordenDeCompra) {
        this.ordenDeCompra = ordenDeCompra;
    }

    public ClienteOutputModel getCliente() {
        return cliente;
    }

    public void setCliente(ClienteOutputModel cliente) {
        this.cliente = cliente;
    }

    public FirmanteOutputModel getFirmante() {
        return firmante;
    }

    public void setFirmante(FirmanteOutputModel firmante) {
        this.firmante = firmante;
    }

    public ProveedorOutputModel getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorOutputModel proveedor) {
        this.proveedor = proveedor;
    }

    public List<GuiaRemisionRelacionadaOutputModel> getGuiasDeRemisionRelacionadas() {
        return guiasDeRemisionRelacionadas;
    }

    public void setGuiasDeRemisionRelacionadas(List<GuiaRemisionRelacionadaOutputModel> guiasDeRemisionRelacionadas) {
        this.guiasDeRemisionRelacionadas = guiasDeRemisionRelacionadas;
    }

    public List<DocumentoTributarioRelacionadoOutputModel> getDocumentosTributariosRelacionados() {
        return documentosTributariosRelacionados;
    }

    public void setDocumentosTributariosRelacionados(List<DocumentoTributarioRelacionadoOutputModel> documentosTributariosRelacionados) {
        this.documentosTributariosRelacionados = documentosTributariosRelacionados;
    }

    public List<DocumentLineOutputModel> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<DocumentLineOutputModel> detalle) {
        this.detalle = detalle;
    }

    public BigDecimal getImporteTotalImpuestos() {
        return importeTotalImpuestos;
    }

    public void setImporteTotalImpuestos(BigDecimal importeTotalImpuestos) {
        this.importeTotalImpuestos = importeTotalImpuestos;
    }

    public List<ImpuestoTotalOutputModel> getTotalImpuestos() {
        return totalImpuestos;
    }

    public void setTotalImpuestos(List<ImpuestoTotalOutputModel> totalImpuestos) {
        this.totalImpuestos = totalImpuestos;
    }

    public List<ImpuestoTotalICBOutputModel> getTotalImpuestosIcb() {
        return totalImpuestosIcb;
    }

    public void setTotalImpuestosIcb(List<ImpuestoTotalICBOutputModel> totalImpuestosIcb) {
        this.totalImpuestosIcb = totalImpuestosIcb;
    }

    public BigDecimal getTotalValorVenta() {
        return totalValorVenta;
    }

    public void setTotalValorVenta(BigDecimal totalValorVenta) {
        this.totalValorVenta = totalValorVenta;
    }

    public BigDecimal getTotalPrecioVenta() {
        return totalPrecioVenta;
    }

    public void setTotalPrecioVenta(BigDecimal totalPrecioVenta) {
        this.totalPrecioVenta = totalPrecioVenta;
    }

    public BigDecimal getTotalDescuentos() {
        return totalDescuentos;
    }

    public void setTotalDescuentos(BigDecimal totalDescuentos) {
        this.totalDescuentos = totalDescuentos;
    }

    public BigDecimal getTotalOtrosCargos() {
        return totalOtrosCargos;
    }

    public void setTotalOtrosCargos(BigDecimal totalOtrosCargos) {
        this.totalOtrosCargos = totalOtrosCargos;
    }

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }
}
