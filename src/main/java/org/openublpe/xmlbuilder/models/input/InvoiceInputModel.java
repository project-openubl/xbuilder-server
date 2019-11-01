package org.openublpe.xmlbuilder.models.input;

public class InvoiceInputModel {

    private String serie;
    private int numero;
    private long fechaHoraEmision;
    private String moneda;
    private ProveedorInputModel proveedor;
    private ClienteInputModel cliente;

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public long getFechaHoraEmision() {
        return fechaHoraEmision;
    }

    public void setFechaHoraEmision(long fechaHoraEmision) {
        this.fechaHoraEmision = fechaHoraEmision;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
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
}
