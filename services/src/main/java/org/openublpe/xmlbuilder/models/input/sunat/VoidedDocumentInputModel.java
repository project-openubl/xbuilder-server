package org.openublpe.xmlbuilder.models.input.sunat;

import org.openublpe.xmlbuilder.models.input.common.FirmanteInputModel;
import org.openublpe.xmlbuilder.models.input.common.ProveedorInputModel;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class VoidedDocumentInputModel {

    @Min(1)
    @NotNull
    private Integer numero;

    private Long fechaEmision;

    @Valid
    @NotNull
    private ProveedorInputModel proveedor;

    @Valid
    private FirmanteInputModel firmante;

    @NotNull
    @NotBlank
    private String tipoDocumentReference;

    @NotNull
    private Long fechaEmisionDocumentReference;

    @NotNull
    @NotBlank
    private String serieNumeroDocumentReference;

    @NotNull
    @NotBlank
    private String motivoBajaDocumentReference;

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer serieNumero) {
        this.numero = serieNumero;
    }

    public Long getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Long fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public ProveedorInputModel getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorInputModel proveedor) {
        this.proveedor = proveedor;
    }

    public String getTipoDocumentReference() {
        return tipoDocumentReference;
    }

    public void setTipoDocumentReference(String tipoDocumentReference) {
        this.tipoDocumentReference = tipoDocumentReference;
    }

    public Long getFechaEmisionDocumentReference() {
        return fechaEmisionDocumentReference;
    }

    public void setFechaEmisionDocumentReference(Long fechaEmisionDocumentReference) {
        this.fechaEmisionDocumentReference = fechaEmisionDocumentReference;
    }

    public String getSerieNumeroDocumentReference() {
        return serieNumeroDocumentReference;
    }

    public void setSerieNumeroDocumentReference(String serieNumeroDocumentReference) {
        this.serieNumeroDocumentReference = serieNumeroDocumentReference;
    }

    public String getMotivoBajaDocumentReference() {
        return motivoBajaDocumentReference;
    }

    public void setMotivoBajaDocumentReference(String motivoBajaDocumentReference) {
        this.motivoBajaDocumentReference = motivoBajaDocumentReference;
    }

    public FirmanteInputModel getFirmante() {
        return firmante;
    }

    public void setFirmante(FirmanteInputModel firmante) {
        this.firmante = firmante;
    }
}
