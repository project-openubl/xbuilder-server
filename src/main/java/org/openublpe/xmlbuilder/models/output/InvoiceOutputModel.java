package org.openublpe.xmlbuilder.models.output;

public class InvoiceOutputModel {

    private String serieNumero;
    private String fechaEmision;
    private String horaEmision;
    private String codigoTipoComprobante;
    private String moneda;
    private int cantidadItemsVendidos;
    private ProveedorOutputModel proveedor;
    private ClienteOutputModel cliente;

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

    public String getCodigoTipoComprobante() {
        return codigoTipoComprobante;
    }

    public void setCodigoTipoComprobante(String codigoTipoComprobante) {
        this.codigoTipoComprobante = codigoTipoComprobante;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public int getCantidadItemsVendidos() {
        return cantidadItemsVendidos;
    }

    public void setCantidadItemsVendidos(int cantidadItemsVendidos) {
        this.cantidadItemsVendidos = cantidadItemsVendidos;
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
}
