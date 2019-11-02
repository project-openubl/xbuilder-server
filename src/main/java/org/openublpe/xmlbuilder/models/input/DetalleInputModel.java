package org.openublpe.xmlbuilder.models.input;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class DetalleInputModel {

    @NotBlank
    private String descripcion;

    @NotNull
    @Positive
    @Digits(integer = Integer.MAX_VALUE, fraction = 3)
    private BigDecimal cantidad;
    private String unidadMedida;

    // Sin impuestos
    private BigDecimal valorUnitario;

    // Con impuestos
    @NotNull
    @Positive
    private BigDecimal precioUnitario;

    @Positive
    private BigDecimal subtotal;

    @Positive
    private BigDecimal total;

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
}
