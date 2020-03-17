package org.openublpe.xmlbuilder.core.models.output.standard;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class DocumentImpuestosOutputModel {

    @Min(0)
    @NotNull
    @Digits(integer = 100, fraction = 2)
    private BigDecimal importeTotal;

    @Valid
    private ImpuestoTotalOutputModel ivap;

    @Valid
    private ImpuestoTotalOutputModel gravadas;

    @Valid
    private ImpuestoTotalOutputModel inafectas;

    @Valid
    private ImpuestoTotalOutputModel exoneradas;

    @Valid
    private ImpuestoTotalOutputModel gratuitas;

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }

    public ImpuestoTotalOutputModel getIvap() {
        return ivap;
    }

    public void setIvap(ImpuestoTotalOutputModel ivap) {
        this.ivap = ivap;
    }

    public ImpuestoTotalOutputModel getGravadas() {
        return gravadas;
    }

    public void setGravadas(ImpuestoTotalOutputModel gravadas) {
        this.gravadas = gravadas;
    }

    public ImpuestoTotalOutputModel getInafectas() {
        return inafectas;
    }

    public void setInafectas(ImpuestoTotalOutputModel inafectas) {
        this.inafectas = inafectas;
    }

    public ImpuestoTotalOutputModel getExoneradas() {
        return exoneradas;
    }

    public void setExoneradas(ImpuestoTotalOutputModel exoneradas) {
        this.exoneradas = exoneradas;
    }

    public ImpuestoTotalOutputModel getGratuitas() {
        return gratuitas;
    }

    public void setGratuitas(ImpuestoTotalOutputModel gratuitas) {
        this.gratuitas = gratuitas;
    }

    public static final class Builder {
        private BigDecimal importeTotal;
        private ImpuestoTotalOutputModel ivap;
        private ImpuestoTotalOutputModel gravadas;
        private ImpuestoTotalOutputModel inafectas;
        private ImpuestoTotalOutputModel exoneradas;
        private ImpuestoTotalOutputModel gratuitas;

        private Builder() {
        }

        public static Builder aDocumentImpuestosOutputModel() {
            return new Builder();
        }

        public Builder withImporteTotal(BigDecimal importeTotal) {
            this.importeTotal = importeTotal;
            return this;
        }

        public Builder withIvap(ImpuestoTotalOutputModel ivap) {
            this.ivap = ivap;
            return this;
        }

        public Builder withGravadas(ImpuestoTotalOutputModel gravadas) {
            this.gravadas = gravadas;
            return this;
        }

        public Builder withInafectas(ImpuestoTotalOutputModel inafectas) {
            this.inafectas = inafectas;
            return this;
        }

        public Builder withExoneradas(ImpuestoTotalOutputModel exoneradas) {
            this.exoneradas = exoneradas;
            return this;
        }

        public Builder withGratuitas(ImpuestoTotalOutputModel gratuitas) {
            this.gratuitas = gratuitas;
            return this;
        }

        public DocumentImpuestosOutputModel build() {
            DocumentImpuestosOutputModel documentImpuestosOutputModel = new DocumentImpuestosOutputModel();
            documentImpuestosOutputModel.setImporteTotal(importeTotal);
            documentImpuestosOutputModel.setIvap(ivap);
            documentImpuestosOutputModel.setGravadas(gravadas);
            documentImpuestosOutputModel.setInafectas(inafectas);
            documentImpuestosOutputModel.setExoneradas(exoneradas);
            documentImpuestosOutputModel.setGratuitas(gratuitas);
            return documentImpuestosOutputModel;
        }
    }
}
