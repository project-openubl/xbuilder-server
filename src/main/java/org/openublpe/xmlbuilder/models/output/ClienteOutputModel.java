package org.openublpe.xmlbuilder.models.output;

import org.openublpe.xmlbuilder.models.catalogs.Catalog6;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ClienteOutputModel {

    @NotNull
    private Catalog6 tipoDocumentoIdentidad;

    @NotBlank
    private String numeroDocumentoIdentidad;

    @NotBlank
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
