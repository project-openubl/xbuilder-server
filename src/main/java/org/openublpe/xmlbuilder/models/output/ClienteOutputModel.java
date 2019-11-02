package org.openublpe.xmlbuilder.models.output;

import org.openublpe.xmlbuilder.models.ubl.Catalog6;

public class ClienteOutputModel {

    private Catalog6 tipoDocumentoIdentidad;
    private String numeroDocumentoIdentidad;
    private String nombre;

    public Catalog6 getTipoDocumentoIdentidad() {
        return tipoDocumentoIdentidad;
    }

    public void setTipoDocumentoIdentidad(Catalog6 tipoDocumentoIdentidad) {
        this.tipoDocumentoIdentidad = tipoDocumentoIdentidad;
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
