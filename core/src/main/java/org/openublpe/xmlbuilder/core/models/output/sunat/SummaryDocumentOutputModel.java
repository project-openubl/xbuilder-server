package org.openublpe.xmlbuilder.core.models.output.sunat;

import org.openublpe.xmlbuilder.core.models.output.common.FirmanteOutputModel;
import org.openublpe.xmlbuilder.core.models.output.common.ProveedorOutputModel;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class SummaryDocumentOutputModel {

    @NotBlank
    private String serieNumero;

    @NotBlank
    private String moneda;

    @NotBlank
    private String fechaEmision;

    @NotBlank
    private String fechaEmisionDocumentReference;

    @Valid
    @NotNull
    private ProveedorOutputModel proveedor;

    @Valid
    @NotNull
    private FirmanteOutputModel firmante;

    @Valid
    @NotEmpty
    private List<SummaryDocumentLineOutputModel> detalle;

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

    public String getFechaEmisionDocumentReference() {
        return fechaEmisionDocumentReference;
    }

    public void setFechaEmisionDocumentReference(String fechaEmisionDocumentReference) {
        this.fechaEmisionDocumentReference = fechaEmisionDocumentReference;
    }

    public ProveedorOutputModel getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorOutputModel proveedor) {
        this.proveedor = proveedor;
    }

    public FirmanteOutputModel getFirmante() {
        return firmante;
    }

    public void setFirmante(FirmanteOutputModel firmante) {
        this.firmante = firmante;
    }

    public List<SummaryDocumentLineOutputModel>  getDetalle() {
        return detalle;
    }

    public void setDetalle(List<SummaryDocumentLineOutputModel>  detalle) {
        this.detalle = detalle;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }
}
