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

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ImpuestoTotalOutputModel extends ImpuestoOutputModel {

    @NotNull
    @Min(0)
    private BigDecimal baseImponible;

    public BigDecimal getBaseImponible() {
        return baseImponible;
    }

    public void setBaseImponible(BigDecimal baseImponible) {
        this.baseImponible = baseImponible;
    }

    public static final class Builder {
        private BigDecimal baseImponible;
        private BigDecimal importe;
        private Catalog5 categoria;

        private Builder() {
        }

        public static Builder anImpuestoTotalOutputModel() {
            return new Builder();
        }

        public Builder withBaseImponible(BigDecimal baseImponible) {
            this.baseImponible = baseImponible;
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

        public ImpuestoTotalOutputModel build() {
            ImpuestoTotalOutputModel impuestoTotalOutputModel = new ImpuestoTotalOutputModel();
            impuestoTotalOutputModel.setBaseImponible(baseImponible);
            impuestoTotalOutputModel.setImporte(importe);
            impuestoTotalOutputModel.setCategoria(categoria);
            return impuestoTotalOutputModel;
        }
    }
}
