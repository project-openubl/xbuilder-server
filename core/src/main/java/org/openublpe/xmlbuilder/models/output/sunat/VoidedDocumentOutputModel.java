package org.openublpe.xmlbuilder.models.output.sunat;

import org.openublpe.xmlbuilder.models.catalogs.Catalog1;
import org.openublpe.xmlbuilder.models.output.common.FirmanteOutputModel;
import org.openublpe.xmlbuilder.models.output.common.ProveedorOutputModel;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class VoidedDocumentOutputModel {

    @NotBlank
    private String serieNumero;

    @NotBlank
    private String fechaEmision;

    @Valid
    @NotNull
    private ProveedorOutputModel proveedor;

    @Valid
    @NotNull
    private FirmanteOutputModel firmante;

    @NotBlank
    private String fechaEmisionDocumentReference;

    @NotNull
    private Catalog1 tipoDocumentReference;

    @NotBlank
    private String serieDocumentReference;

    @NotBlank
    private String numeroDocumentReference;

    @NotBlank
    private String motivoBajaDocumentReference;

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

    public ProveedorOutputModel getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorOutputModel proveedor) {
        this.proveedor = proveedor;
    }

    public String getFechaEmisionDocumentReference() {
        return fechaEmisionDocumentReference;
    }

    public void setFechaEmisionDocumentReference(String fechaEmisionDocumentReference) {
        this.fechaEmisionDocumentReference = fechaEmisionDocumentReference;
    }

    public Catalog1 getTipoDocumentReference() {
        return tipoDocumentReference;
    }

    public void setTipoDocumentReference(Catalog1 tipoDocumentReference) {
        this.tipoDocumentReference = tipoDocumentReference;
    }

    public String getSerieDocumentReference() {
        return serieDocumentReference;
    }

    public void setSerieDocumentReference(String serieDocumentReference) {
        this.serieDocumentReference = serieDocumentReference;
    }

    public String getNumeroDocumentReference() {
        return numeroDocumentReference;
    }

    public void setNumeroDocumentReference(String numeroDocumentReference) {
        this.numeroDocumentReference = numeroDocumentReference;
    }

    public String getMotivoBajaDocumentReference() {
        return motivoBajaDocumentReference;
    }

    public void setMotivoBajaDocumentReference(String motivoBajaDocumentReference) {
        this.motivoBajaDocumentReference = motivoBajaDocumentReference;
    }

    public FirmanteOutputModel getFirmante() {
        return firmante;
    }

    public void setFirmante(FirmanteOutputModel firmante) {
        this.firmante = firmante;
    }
}
