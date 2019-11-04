package org.openublpe.xmlbuilder.models.output.general;

import org.openublpe.xmlbuilder.models.ubl.Catalog16;
import org.openublpe.xmlbuilder.models.ubl.Catalog7;

import java.math.BigDecimal;

public class DetalleOutputModel {

    private Integer index;
    private String descripcion;
    private BigDecimal cantidad;
    private String unidadMedida;

    // Sin impuestos
    private BigDecimal valorUnitario;

    // Con impuestos
    private BigDecimal precioUnitario;

    // valorUnitario * cantidad
    private BigDecimal subtotal;

    // precioUnitario * cantidad
    private BigDecimal total;

    private BigDecimal igv;

    private Catalog7 tipoIgv;
    private Catalog16 tipoPrecio;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

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

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Catalog7 getTipoIgv() {
        return tipoIgv;
    }

    public void setTipoIgv(Catalog7 tipoIgv) {
        this.tipoIgv = tipoIgv;
    }

    public Catalog16 getTipoPrecio() {
        return tipoPrecio;
    }

    public void setTipoPrecio(Catalog16 tipoPrecio) {
        this.tipoPrecio = tipoPrecio;
    }

    public BigDecimal getIgv() {
        return igv;
    }

    public void setIgv(BigDecimal igv) {
        this.igv = igv;
    }
}
