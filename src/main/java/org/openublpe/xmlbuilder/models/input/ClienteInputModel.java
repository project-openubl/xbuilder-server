package org.openublpe.xmlbuilder.models.input;

public class ClienteInputModel {

    private String codigoDocumentoIdentidad;
    private String numeroDocumentoIdentidad;
    private String nombre;

    public String getCodigoDocumentoIdentidad() {
        return codigoDocumentoIdentidad;
    }

    public void setCodigoDocumentoIdentidad(String codigoDocumentoIdentidad) {
        this.codigoDocumentoIdentidad = codigoDocumentoIdentidad;
    }

    public String getNumeroDocumentoIdentidad() {
        return numeroDocumentoIdentidad;
    }

    public void setNumeroDocumentoIdentidad(String numeroDocumentoIdentidad) {
        this.numeroDocumentoIdentidad = numeroDocumentoIdentidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
