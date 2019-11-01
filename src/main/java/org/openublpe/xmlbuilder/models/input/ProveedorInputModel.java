package org.openublpe.xmlbuilder.models.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ProveedorInputModel {

    @NotBlank
    @Size(min = 11, max = 11)
    private String ruc;

    private String nombreComercial;

    @NotBlank
    private String razonSocial;

    @NotBlank
    private String codigoPostal;

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }
}
