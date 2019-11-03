package org.openublpe.xmlbuilder.models.output;

import javax.validation.constraints.NotNull;

public class FirmanteOutputModel {

    @NotNull
    private String ruc;

    @NotNull
    private String razonSocial;

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }
}
