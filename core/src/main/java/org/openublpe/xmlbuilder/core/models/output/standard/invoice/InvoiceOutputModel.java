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
package org.openublpe.xmlbuilder.core.models.output.standard.invoice;

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog1;
import org.openublpe.xmlbuilder.core.models.input.common.DireccionInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.invoice.CargoDescuentoInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.invoice.DetraccionRelacionadaInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.invoice.PercepcionRelacionadaInputModel;
import org.openublpe.xmlbuilder.core.models.output.common.DireccionOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.DocumentOutputModel;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public class InvoiceOutputModel extends DocumentOutputModel {

    @NotNull
    private Catalog1 tipoInvoice;

    @Valid
    private List<AnticipoOutputModel> anticipos;

    @Valid
    private DireccionOutputModel direccionDeEntrega;

    @Valid
    private DetraccionRelacionadaOutputModel detraccion;

    @Valid
    private List<CargoDescuentoOutputModel> cargos;

    @Valid
    private List<CargoDescuentoOutputModel> descuentos;

    @Valid
    private PercepcionRelacionadaOutputModel percepcion;

    @Min(0)
    private BigDecimal totalAnticipos;

    public Catalog1 getTipoInvoice() {
        return tipoInvoice;
    }

    public void setTipoInvoice(Catalog1 tipoInvoice) {
        this.tipoInvoice = tipoInvoice;
    }

    public List<AnticipoOutputModel> getAnticipos() {
        return anticipos;
    }

    public void setAnticipos(List<AnticipoOutputModel> anticipos) {
        this.anticipos = anticipos;
    }

    public DireccionOutputModel getDireccionDeEntrega() {
        return direccionDeEntrega;
    }

    public void setDireccionDeEntrega(DireccionOutputModel direccionDeEntrega) {
        this.direccionDeEntrega = direccionDeEntrega;
    }

    public DetraccionRelacionadaOutputModel getDetraccion() {
        return detraccion;
    }

    public void setDetraccion(DetraccionRelacionadaOutputModel detraccion) {
        this.detraccion = detraccion;
    }

    public List<CargoDescuentoOutputModel> getCargos() {
        return cargos;
    }

    public void setCargos(List<CargoDescuentoOutputModel> cargos) {
        this.cargos = cargos;
    }

    public List<CargoDescuentoOutputModel> getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(List<CargoDescuentoOutputModel> descuentos) {
        this.descuentos = descuentos;
    }

    public PercepcionRelacionadaOutputModel getPercepcion() {
        return percepcion;
    }

    public void setPercepcion(PercepcionRelacionadaOutputModel percepcion) {
        this.percepcion = percepcion;
    }

    public BigDecimal getTotalAnticipos() {
        return totalAnticipos;
    }

    public void setTotalAnticipos(BigDecimal totalAnticipos) {
        this.totalAnticipos = totalAnticipos;
    }
}
