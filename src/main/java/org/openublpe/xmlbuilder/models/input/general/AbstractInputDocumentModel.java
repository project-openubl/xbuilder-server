package org.openublpe.xmlbuilder.models.input.general;

import org.openublpe.xmlbuilder.models.input.ClienteInputModel;
import org.openublpe.xmlbuilder.models.input.FirmanteInputModel;
import org.openublpe.xmlbuilder.models.input.ProveedorInputModel;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

public abstract class AbstractInputDocumentModel {

    @NotBlank
    @Size(min = 4, max = 4)
    protected String serie;

    @Min(1)
    @NotNull
    private Integer numero;

    private String moneda;

    private Long fechaEmision;

    @Positive
    private BigDecimal descuentoGlobal;

    @Positive
    private BigDecimal otrosCargosGlobal;

    @Valid
    private FirmanteInputModel firmante;

    @Valid
    @NotNull
    private ProveedorInputModel proveedor;

    @Valid
    @NotNull
    private ClienteInputModel cliente;

    @Valid
    @NotEmpty
    private List<DetalleInputModel> detalle;

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Long getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Long fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public FirmanteInputModel getFirmante() {
        return firmante;
    }

    public void setFirmante(FirmanteInputModel firmante) {
        this.firmante = firmante;
    }

    public ProveedorInputModel getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorInputModel proveedor) {
        this.proveedor = proveedor;
    }

    public ClienteInputModel getCliente() {
        return cliente;
    }

    public void setCliente(ClienteInputModel cliente) {
        this.cliente = cliente;
    }

    public List<DetalleInputModel> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<DetalleInputModel> detalle) {
        this.detalle = detalle;
    }

    public BigDecimal getDescuentoGlobal() {
        return descuentoGlobal;
    }

    public void setDescuentoGlobal(BigDecimal descuentoGlobal) {
        this.descuentoGlobal = descuentoGlobal;
    }

    public BigDecimal getOtrosCargosGlobal() {
        return otrosCargosGlobal;
    }

    public void setOtrosCargosGlobal(BigDecimal otrosCargosGlobal) {
        this.otrosCargosGlobal = otrosCargosGlobal;
    }
}
