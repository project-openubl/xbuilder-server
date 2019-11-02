package org.openublpe.xmlbuilder.models.output;

import org.openublpe.xmlbuilder.models.ubl.Catalog1;

import java.math.BigDecimal;
import java.util.List;

public class InvoiceOutputModel {

    private BigDecimal igv;
    private BigDecimal igvPercent;

    private String serieNumero;
    private String fechaEmision;
    private String horaEmision;
    private Catalog1 tipoComprobante;
    private String moneda;
    private int cantidadItemsVendidos;
    private ProveedorOutputModel proveedor;
    private ClienteOutputModel cliente;
    private Integer detalleSize;
    private List<DetalleOutputModel> detalle;

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

    public Catalog1 getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(Catalog1 tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public int getCantidadItemsVendidos() {
        return cantidadItemsVendidos;
    }

    public void setCantidadItemsVendidos(int cantidadItemsVendidos) {
        this.cantidadItemsVendidos = cantidadItemsVendidos;
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

    public BigDecimal getIgv() {
        return igv;
    }

    public void setIgv(BigDecimal igv) {
        this.igv = igv;
    }

    public BigDecimal getIgvPercent() {
        return igvPercent;
    }

    public void setIgvPercent(BigDecimal igvPercent) {
        this.igvPercent = igvPercent;
    }
}
