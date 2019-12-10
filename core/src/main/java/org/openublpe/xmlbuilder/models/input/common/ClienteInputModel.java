package org.openublpe.xmlbuilder.models.input.common;

import org.openublpe.xmlbuilder.models.catalogs.Catalog6;
import org.openublpe.xmlbuilder.models.catalogs.constraints.CatalogConstraint;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ClienteInputModel {

    @NotNull
    @NotBlank
    @CatalogConstraint(value = Catalog6.class)
    private String tipoDocumentoIdentidad;

    @NotNull
    @NotBlank
    private String numeroDocumentoIdentidad;

    @NotNull
    @NotBlank
    private String nombre;

    public String getTipoDocumentoIdentidad() {
        return tipoDocumentoIdentidad;
    }

    public void setTipoDocumentoIdentidad(String tipoDocumentoIdentidad) {
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
