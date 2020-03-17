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
package org.openublpe.xmlbuilder.core.models.output.standard;

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog5;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ImpuestoDetalladoICBOutputModel extends ImpuestoOutputModel {

    @Min(0)
    @NotNull
    @Digits(integer = 100, fraction = 2)
    private BigDecimal icbValor;

    public BigDecimal getIcbValor() {
        return icbValor;
    }

    public void setIcbValor(BigDecimal icbValor) {
        this.icbValor = icbValor;
    }

    public static final class Builder {
        private BigDecimal importe;
        private BigDecimal icbValor;
        private Catalog5 categoria;

        private Builder() {
        }

        public static Builder anImpuestoDetalladoICBOutputModel() {
            return new Builder();
        }

        public Builder withImporte(BigDecimal importe) {
            this.importe = importe;
            return this;
        }

        public Builder withIcbValor(BigDecimal icbValor) {
            this.icbValor = icbValor;
            return this;
        }

        public Builder withCategoria(Catalog5 categoria) {
            this.categoria = categoria;
            return this;
        }

        public ImpuestoDetalladoICBOutputModel build() {
            ImpuestoDetalladoICBOutputModel impuestoDetalladoICBOutputModel = new ImpuestoDetalladoICBOutputModel();
            impuestoDetalladoICBOutputModel.setImporte(importe);
            impuestoDetalladoICBOutputModel.setIcbValor(icbValor);
            impuestoDetalladoICBOutputModel.setCategoria(categoria);
            return impuestoDetalladoICBOutputModel;
        }
    }
}
