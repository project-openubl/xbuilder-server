package org.openublpe.xmlbuilder.models.output.general;

import org.openublpe.xmlbuilder.models.output.ClienteOutputModel;
import org.openublpe.xmlbuilder.models.output.FirmanteOutputModel;
import org.openublpe.xmlbuilder.models.output.ProveedorOutputModel;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

public class AbstractOutputDocumentModel {

    @NotBlank
    private String serieNumero;

    @NotBlank
    private String fechaEmision;

    @NotBlank
    private String horaEmision;

    @NotBlank
    private String moneda;

    @Valid
    private FirmanteOutputModel firmante;

    @Valid
    @NotNull
    private ProveedorOutputModel proveedor;

    @Valid
    @NotNull
    private ClienteOutputModel cliente;

    @NotNull
    private BigDecimal importeTotalImpuestos;

    @Valid
    @NotEmpty
    private List<ImpuestoOutputModel> totalImpuestos;

    @NotNull
    @Min(1)
    private Integer detalleSize;

    @Valid
    @NotEmpty
    private List<DetalleOutputModel> detalle;

    /**
     * Totales
     */

    @NotNull
    private BigDecimal totalValorVenta;

    @NotNull
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

    public List<ImpuestoOutputModel> getTotalImpuestos() {
        return totalImpuestos;
    }

    public void setTotalImpuestos(List<ImpuestoOutputModel> totalImpuestos) {
        this.totalImpuestos = totalImpuestos;
    }

    public BigDecimal getImporteTotalImpuestos() {
        return importeTotalImpuestos;
    }

    public void setImporteTotalImpuestos(BigDecimal importeTotalImpuestos) {
        this.importeTotalImpuestos = importeTotalImpuestos;
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
