package org.openublpe.xmlbuilder.models.output.general;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

public class DetalleOutputModel {

    @NotBlank
    private String descripcion;

    @NotNull
    @Positive
    private BigDecimal cantidad;

    @NotBlank
    private String unidadMedida;

    // Sin impuestos
    private BigDecimal valorUnitario;

    // Con impuestos
    private BigDecimal precioUnitario;

    // valorUnitario * cantidad
    private BigDecimal subtotal;

    // precioUnitario * cantidad
    private BigDecimal total;

    @Valid
    @NotNull
    private DetalleImpuestoOutputModel igv;

    @Valid
    @NotEmpty
    private List<PrecioReferenciaOutputModel> preciosDeReferencia;

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

    public DetalleImpuestoOutputModel getIgv() {
        return igv;
    }

    public void setIgv(DetalleImpuestoOutputModel igv) {
        this.igv = igv;
    }

    public List<PrecioReferenciaOutputModel> getPreciosDeReferencia() {
        return preciosDeReferencia;
    }

    public void setPreciosDeReferencia(List<PrecioReferenciaOutputModel> preciosDeReferencia) {
        this.preciosDeReferencia = preciosDeReferencia;
    }
}
