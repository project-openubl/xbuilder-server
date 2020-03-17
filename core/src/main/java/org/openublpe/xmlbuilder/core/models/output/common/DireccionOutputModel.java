package org.openublpe.xmlbuilder.core.models.output.common;

import javax.validation.constraints.Size;

public class DireccionOutputModel {

    @Size(min = 6, max = 6)
    private String ubigeo;

    private String codigoLocal;
    private String urbanizacion;
    private String provincia;
    private String departamento;
    private String distrito;
    private String direccion;
    private String codigoPais;

    public String getUbigeo() {
        return ubigeo;
    }

    public void setUbigeo(String ubigeo) {
        this.ubigeo = ubigeo;
    }

    public String getCodigoLocal() {
        return codigoLocal;
    }

    public void setCodigoLocal(String codigoLocal) {
        this.codigoLocal = codigoLocal;
    }

    public String getUrbanizacion() {
        return urbanizacion;
    }

    public void setUrbanizacion(String urbanizacion) {
        this.urbanizacion = urbanizacion;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodigoPais() {
        return codigoPais;
    }

    public void setCodigoPais(String codigoPais) {
        this.codigoPais = codigoPais;
    }

    public static final class Builder {
        private String ubigeo;
        private String codigoLocal;
        private String urbanizacion;
        private String provincia;
        private String departamento;
        private String distrito;
        private String direccion;
        private String codigoPais;

        private Builder() {
        }

        public static Builder aDireccionOutputModel() {
            return new Builder();
        }

        public Builder withUbigeo(String ubigeo) {
            this.ubigeo = ubigeo;
            return this;
        }

        public Builder withCodigoLocal(String codigoLocal) {
            this.codigoLocal = codigoLocal;
            return this;
        }

        public Builder withUrbanizacion(String urbanizacion) {
            this.urbanizacion = urbanizacion;
            return this;
        }

        public Builder withProvincia(String provincia) {
            this.provincia = provincia;
            return this;
        }

        public Builder withDepartamento(String departamento) {
            this.departamento = departamento;
            return this;
        }

        public Builder withDistrito(String distrito) {
            this.distrito = distrito;
            return this;
        }

        public Builder withDireccion(String direccion) {
            this.direccion = direccion;
            return this;
        }

        public Builder withCodigoPais(String codigoPais) {
            this.codigoPais = codigoPais;
            return this;
        }

        public DireccionOutputModel build() {
            DireccionOutputModel direccionOutputModel = new DireccionOutputModel();
            direccionOutputModel.setUbigeo(ubigeo);
            direccionOutputModel.setCodigoLocal(codigoLocal);
            direccionOutputModel.setUrbanizacion(urbanizacion);
            direccionOutputModel.setProvincia(provincia);
            direccionOutputModel.setDepartamento(departamento);
            direccionOutputModel.setDistrito(distrito);
            direccionOutputModel.setDireccion(direccion);
            direccionOutputModel.setCodigoPais(codigoPais);
            return direccionOutputModel;
        }
    }
}
