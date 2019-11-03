package org.openublpe.xmlbuilder.models.output;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    @Min(1)
    private Integer detalleSize;

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
}
