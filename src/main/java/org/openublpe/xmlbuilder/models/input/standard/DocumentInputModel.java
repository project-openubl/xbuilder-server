package org.openublpe.xmlbuilder.models.input.standard;

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

public abstract class DocumentInputModel {

    @NotBlank
    @Size(min = 4, max = 4)
    protected String serie;

    @Min(1)
    @NotNull
    private Integer numero;

    private String moneda;
    private Long fechaEmision;

    @Positive
    private BigDecimal totalDescuentos;

    @Positive
    private BigDecimal totalOtrosCargos;

    @Valid
    @NotNull
    private ClienteInputModel cliente;

    @Valid
    private FirmanteInputModel firmante;

    @Valid
    @NotNull
    private ProveedorInputModel proveedor;

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

    public ClienteInputModel getCliente() {
        return cliente;
    }

    public void setCliente(ClienteInputModel cliente) {
        this.cliente = cliente;
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

    public List<DetalleInputModel> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<DetalleInputModel> detalle) {
        this.detalle = detalle;
    }
}
