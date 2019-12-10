package org.openublpe.xmlbuilder.core.models.input.standard;

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog7;
import org.openublpe.xmlbuilder.core.models.catalogs.constraints.CatalogConstraint;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class DetalleInputModel {

    @NotNull
    @NotBlank
    private String descripcion;

    private String unidadMedida;

    @NotNull
    @Positive
    @Digits(integer = Integer.MAX_VALUE, fraction = 3)
    private BigDecimal cantidad;

    /**
     * Precio con impuestos
     */
    @NotNull
    @Positive
    private BigDecimal precioUnitario;

    @CatalogConstraint(value = Catalog7.class)
    private String tipoIGV;
    private boolean icb;

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

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public String getTipoIGV() {
        return tipoIGV;
    }

    public void setTipoIGV(String tipoIGV) {
        this.tipoIGV = tipoIGV;
    }

    public boolean isIcb() {
        return icb;
    }

    public void setIcb(boolean icb) {
        this.icb = icb;
    }
}
