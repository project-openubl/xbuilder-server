package org.openublpe.xmlbuilder.models.output.standard;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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

    @NotNull
    @Min(0)
    private BigDecimal valorUnitario;

    @NotNull
    @Min(0)
    private BigDecimal precioUnitario;

    @NotNull
    @Min(0)
    private BigDecimal subtotal;

    @NotNull
    @Min(0)
    private BigDecimal total;

    @NotNull
    @Min(0)
    @Max(100)
    private BigDecimal igvPorcentual;

    @NotNull
    @Min(0)
    private BigDecimal icbAplicado;

    @Valid
    @NotNull
    private ImpuestoDetalladoIGVOutputModel igv;

    @Valid
    @NotNull
    private ImpuestoDetalladoICBOutputModel icb;

    @Valid
    @NotEmpty
    private List<DetallePrecioReferenciaOutputModel> preciosDeReferencia;

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

    public BigDecimal getIgvPorcentual() {
        return igvPorcentual;
    }

    public void setIgvPorcentual(BigDecimal igvPorcentual) {
        this.igvPorcentual = igvPorcentual;
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

    public List<DetallePrecioReferenciaOutputModel> getPreciosDeReferencia() {
        return preciosDeReferencia;
    }

    public void setPreciosDeReferencia(List<DetallePrecioReferenciaOutputModel> preciosDeReferencia) {
        this.preciosDeReferencia = preciosDeReferencia;
    }

    public BigDecimal getIcbAplicado() {
        return icbAplicado;
    }

    public void setIcbAplicado(BigDecimal icbAplicado) {
        this.icbAplicado = icbAplicado;
    }
}
