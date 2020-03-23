package org.openublpe.xmlbuilder.core.models.output.standard;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class DocumentLineImpuestosOutputModel {

    @Min(0)
    @NotNull
    @Digits(integer = 100, fraction = 2)
    private BigDecimal importeTotal;

    @Valid
    @NotNull
    private ImpuestoDetalladoIGVOutputModel igv;

    @Valid
    private ImpuestoDetalladoICBOutputModel icb;

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }

    public ImpuestoDetalladoIGVOutputModel getIgv() {
        return igv;
    }

    public void setIgv(ImpuestoDetalladoIGVOutputModel igv) {
        this.igv = igv;
    }

    public ImpuestoDetalladoICBOutputModel getIcb() {
        return icb;
    }

    public void setIcb(ImpuestoDetalladoICBOutputModel icb) {
        this.icb = icb;
    }

    public static final class Builder {
        private BigDecimal importeTotal;
        private ImpuestoDetalladoIGVOutputModel igv;
        private ImpuestoDetalladoICBOutputModel icb;

        private Builder() {
        }

        public static Builder aDocumentLineImpuestosOutputModel() {
            return new Builder();
        }

        public Builder withImporteTotal(BigDecimal importeTotal) {
            this.importeTotal = importeTotal;
            return this;
        }

        public Builder withIgv(ImpuestoDetalladoIGVOutputModel igv) {
            this.igv = igv;
            return this;
        }

        public Builder withIcb(ImpuestoDetalladoICBOutputModel icb) {
            this.icb = icb;
            return this;
        }

        public DocumentLineImpuestosOutputModel build() {
            DocumentLineImpuestosOutputModel documentLineImpuestosOutputModel = new DocumentLineImpuestosOutputModel();
            documentLineImpuestosOutputModel.setImporteTotal(importeTotal);
            documentLineImpuestosOutputModel.setIgv(igv);
            documentLineImpuestosOutputModel.setIcb(icb);
            return documentLineImpuestosOutputModel;
        }
    }
}
