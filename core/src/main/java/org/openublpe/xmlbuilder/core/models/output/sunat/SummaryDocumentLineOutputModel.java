package org.openublpe.xmlbuilder.core.models.output.sunat;

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog1;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog19;
import org.openublpe.xmlbuilder.core.models.output.common.ClienteOutputModel;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public class SummaryDocumentLineOutputModel {

    @NotNull
    private Catalog1 tipoComprobante;

    @NotBlank
    private String serieNumero;

    @Valid
    @NotNull
    private ClienteOutputModel cliente;

    @NotNull
    private Catalog19 tipoOperacion;

    @NotNull
    @Min(0)
    private BigDecimal importeTotal;

    @Min(0)
    private BigDecimal totalOtrosCargos;

    @Valid
    @NotEmpty
    private List<TotalValorVentaOutputModel> totales;

    @Valid
    @NotEmpty
    private List<ImpuestoTotalResumenDiarioOutputModel> impuestos;

    public Catalog1 getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(Catalog1 tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public String getSerieNumero() {
        return serieNumero;
    }

    public void setSerieNumero(String serieNumero) {
        this.serieNumero = serieNumero;
    }

    public ClienteOutputModel getCliente() {
        return cliente;
    }

    public void setCliente(ClienteOutputModel cliente) {
        this.cliente = cliente;
    }

    public Catalog19 getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(Catalog19 tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }

    public BigDecimal getTotalOtrosCargos() {
        return totalOtrosCargos;
    }

    public void setTotalOtrosCargos(BigDecimal totalOtrosCargos) {
        this.totalOtrosCargos = totalOtrosCargos;
    }

    public List<TotalValorVentaOutputModel> getTotales() {
        return totales;
    }

    public void setTotales(List<TotalValorVentaOutputModel> totales) {
        this.totales = totales;
    }

    public List<ImpuestoTotalResumenDiarioOutputModel> getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(List<ImpuestoTotalResumenDiarioOutputModel> impuestos) {
        this.impuestos = impuestos;
    }

}
