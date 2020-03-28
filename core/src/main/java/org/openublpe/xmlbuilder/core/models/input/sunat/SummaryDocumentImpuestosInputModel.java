/**
 * Copyright 2019 Project OpenUBL, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Eclipse Public License - v 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openublpe.xmlbuilder.core.models.input.sunat;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(name = "SummaryDocumentLineComprobanteImpuestos")
public class SummaryDocumentImpuestosInputModel {

    @NotNull
    @Min(0)
    @Digits(integer = 100, fraction = 2)
    @Schema(example = "18", description = "Importe total de IGV")
    private BigDecimal igv;

    @Min(0)
    @Digits(integer = 100, fraction = 2)
    @Schema(example = "0", description = "Importe total de ICB_PE")
    private BigDecimal icb;

    public BigDecimal getIgv() {
        return igv;
    }

    public void setIgv(BigDecimal igv) {
        this.igv = igv;
    }

    public BigDecimal getIcb() {
        return icb;
    }

    public void setIcb(BigDecimal icb) {
        this.icb = icb;
    }

    public static final class Builder {
        private BigDecimal igv;
        private BigDecimal icb;

        private Builder() {
        }

        public static Builder aSummaryDocumentImpuestosInputModel() {
            return new Builder();
        }

        public Builder withIgv(BigDecimal igv) {
            this.igv = igv;
            return this;
        }

        public Builder withIcb(BigDecimal icb) {
            this.icb = icb;
            return this;
        }

        public SummaryDocumentImpuestosInputModel build() {
            SummaryDocumentImpuestosInputModel summaryDocumentImpuestosInputModel = new SummaryDocumentImpuestosInputModel();
            summaryDocumentImpuestosInputModel.setIgv(igv);
            summaryDocumentImpuestosInputModel.setIcb(icb);
            return summaryDocumentImpuestosInputModel;
        }
    }
}
