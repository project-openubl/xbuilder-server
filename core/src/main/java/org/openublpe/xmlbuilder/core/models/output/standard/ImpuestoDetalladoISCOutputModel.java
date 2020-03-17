package org.openublpe.xmlbuilder.core.models.output.standard;

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog5;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog8;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ImpuestoDetalladoISCOutputModel extends ImpuestoDetalladoOutputModel {

    @NotNull
    private Catalog8 tipo;

    public Catalog8 getTipo() {
        return tipo;
    }

    public void setTipo(Catalog8 tipo) {
        this.tipo = tipo;
    }

    public static final class Builder {
        private Catalog8 tipo;
        private BigDecimal baseImponible;
        private BigDecimal porcentaje;
        private BigDecimal importe;
        private Catalog5 categoria;

        private Builder() {
        }

        public static Builder anImpuestoDetalladoISCOutputModel() {
            return new Builder();
        }

        public Builder withTipo(Catalog8 tipo) {
            this.tipo = tipo;
            return this;
        }

        public Builder withBaseImponible(BigDecimal baseImponible) {
            this.baseImponible = baseImponible;
            return this;
        }

        public Builder withPorcentaje(BigDecimal porcentaje) {
            this.porcentaje = porcentaje;
            return this;
        }

        public Builder withImporte(BigDecimal importe) {
            this.importe = importe;
            return this;
        }

        public Builder withCategoria(Catalog5 categoria) {
            this.categoria = categoria;
            return this;
        }

        public ImpuestoDetalladoISCOutputModel build() {
            ImpuestoDetalladoISCOutputModel impuestoDetalladoISCOutputModel = new ImpuestoDetalladoISCOutputModel();
            impuestoDetalladoISCOutputModel.setTipo(tipo);
            impuestoDetalladoISCOutputModel.setBaseImponible(baseImponible);
            impuestoDetalladoISCOutputModel.setPorcentaje(porcentaje);
            impuestoDetalladoISCOutputModel.setImporte(importe);
            impuestoDetalladoISCOutputModel.setCategoria(categoria);
            return impuestoDetalladoISCOutputModel;
        }
    }
}
