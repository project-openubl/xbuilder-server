package org.openublpe.xmlbuilder.models.output.invoice;

import org.openublpe.xmlbuilder.models.output.AbstractOutputDocumentModel;
import org.openublpe.xmlbuilder.models.output.DetalleOutputModel;
import org.openublpe.xmlbuilder.models.ubl.Catalog1;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public class InvoiceOutputModel extends AbstractOutputDocumentModel {

    private BigDecimal igv;
    private BigDecimal igvPercent;

    @NotNull
    private Catalog1 tipoComprobante;
    private int cantidadItemsVendidos;
    private List<DetalleOutputModel> detalle;

    public Catalog1 getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(Catalog1 tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public int getCantidadItemsVendidos() {
        return cantidadItemsVendidos;
    }

    public void setCantidadItemsVendidos(int cantidadItemsVendidos) {
        this.cantidadItemsVendidos = cantidadItemsVendidos;
    }

    public List<DetalleOutputModel> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<DetalleOutputModel> detalle) {
        this.detalle = detalle;
    }

    public BigDecimal getIgv() {
        return igv;
    }

    public void setIgv(BigDecimal igv) {
        this.igv = igv;
    }

    public BigDecimal getIgvPercent() {
        return igvPercent;
    }

    public void setIgvPercent(BigDecimal igvPercent) {
        this.igvPercent = igvPercent;
    }
}
