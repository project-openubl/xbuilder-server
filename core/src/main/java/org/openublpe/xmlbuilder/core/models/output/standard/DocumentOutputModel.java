package org.openublpe.xmlbuilder.core.models.output.standard;

import org.openublpe.xmlbuilder.core.models.output.common.ClienteOutputModel;
import org.openublpe.xmlbuilder.core.models.output.common.FirmanteOutputModel;
import org.openublpe.xmlbuilder.core.models.output.common.ProveedorOutputModel;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

public class DocumentOutputModel {

    @Size(min = 3, max = 3)
    @NotBlank
    private String moneda;

    @NotBlank
    private String serieNumero;

    @NotBlank
    private String horaEmision;

    @NotBlank
    private String fechaEmision;

    @Valid
    @NotNull
    private ClienteOutputModel cliente;

    @Valid
    private FirmanteOutputModel firmante;

    @Valid
    @NotNull
    private ProveedorOutputModel proveedor;

    @NotNull
    @Min(1)
    private Integer detalleSize;

    @Valid
    @NotEmpty
    private List<DetalleOutputModel> detalle;

    @NotNull
    private BigDecimal importeTotalImpuestos;

    @Valid
    @NotEmpty
    private List<ImpuestoTotalIGVOutputModel> totalImpuestosIgv;

    @Valid
    @NotNull
    private List<ImpuestoTotalICBOutputModel> totalImpuestosIcb;

    private BigDecimal totalValorVenta;
    private BigDecimal totalPrecioVenta;

    @NotNull
    private BigDecimal totalDescuentos;

    @NotNull
    private BigDecimal totalOtrosCargos;

    @NotNull
    private BigDecimal importeTotal;

    public String getSerieNumero() {
        return serieNumero;
    }

    public void setSerieNumero(String serieNumero) {
        this.serieNumero = serieNumero;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getHoraEmision() {
        return horaEmision;
    }

    public void setHoraEmision(String horaEmision) {
        this.horaEmision = horaEmision;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
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

    public ClienteOutputModel getCliente() {
        return cliente;
    }

    public void setCliente(ClienteOutputModel cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getImporteTotalImpuestos() {
        return importeTotalImpuestos;
    }

    public void setImporteTotalImpuestos(BigDecimal importeTotalImpuestos) {
        this.importeTotalImpuestos = importeTotalImpuestos;
    }

    public List<ImpuestoTotalIGVOutputModel> getTotalImpuestosIgv() {
        return totalImpuestosIgv;
    }

    public void setTotalImpuestosIgv(List<ImpuestoTotalIGVOutputModel> totalImpuestosIgv) {
        this.totalImpuestosIgv = totalImpuestosIgv;
    }

    public Integer getDetalleSize() {
        return detalleSize;
    }

    public void setDetalleSize(Integer detalleSize) {
        this.detalleSize = detalleSize;
    }

    public List<DetalleOutputModel> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<DetalleOutputModel> detalle) {
        this.detalle = detalle;
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

    public List<ImpuestoTotalICBOutputModel> getTotalImpuestosIcb() {
        return totalImpuestosIcb;
    }

    public void setTotalImpuestosIcb(List<ImpuestoTotalICBOutputModel> totalImpuestosIcb) {
        this.totalImpuestosIcb = totalImpuestosIcb;
    }
}
