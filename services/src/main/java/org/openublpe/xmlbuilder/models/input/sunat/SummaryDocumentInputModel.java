package org.openublpe.xmlbuilder.models.input.sunat;

import org.openublpe.xmlbuilder.models.input.common.FirmanteInputModel;
import org.openublpe.xmlbuilder.models.input.common.ProveedorInputModel;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class SummaryDocumentInputModel {

    @Min(1)
    @NotNull
    private Integer numero;

    private String moneda;
    private Long fechaEmision;

    @NotNull
    private Long fechaEmisionDocumentReference;

    @Valid
    @NotNull
    private ProveedorInputModel proveedor;

    @Valid
    @NotNull
    private FirmanteInputModel firmante;

    @NotNull
    @Valid
    @NotEmpty
    private List<SummaryDocumentLineInputModel> detalle;

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

    public Long getFechaEmisionDocumentReference() {
        return fechaEmisionDocumentReference;
    }

    public void setFechaEmisionDocumentReference(Long fechaEmisionDocumentReference) {
        this.fechaEmisionDocumentReference = fechaEmisionDocumentReference;
    }

    public ProveedorInputModel getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorInputModel proveedor) {
        this.proveedor = proveedor;
    }

    public FirmanteInputModel getFirmante() {
        return firmante;
    }

    public void setFirmante(FirmanteInputModel firmante) {
        this.firmante = firmante;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public List<SummaryDocumentLineInputModel> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<SummaryDocumentLineInputModel> detalle) {
        this.detalle = detalle;
    }
}
