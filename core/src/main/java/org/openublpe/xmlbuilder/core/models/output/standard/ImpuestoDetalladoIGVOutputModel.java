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

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog7;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ImpuestoDetalladoIGVOutputModel extends ImpuestoOutputModel {

    @NotNull
    @Min(0)
    private BigDecimal baseImponible;

    @NotNull
    private Catalog7 tipo;

    public BigDecimal getBaseImponible() {
        return baseImponible;
    }

    public void setBaseImponible(BigDecimal baseImponible) {
        this.baseImponible = baseImponible;
    }

    public Catalog7 getTipo() {
        return tipo;
    }

    public void setTipo(Catalog7 tipo) {
        this.tipo = tipo;
    }

}
