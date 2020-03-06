/**
 * Copyright 2019 Project OpenUBL, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 * <p>
 * Licensed under the Eclipse Public License - v 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.eclipse.org/legal/epl-2.0/
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openublpe.xmlbuilder.core.models.input.standard.invoice;

import org.openublpe.xmlbuilder.core.models.input.common.DireccionInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.DocumentInputModel;

import javax.validation.Valid;
import java.util.List;

public class InvoiceInputModel extends DocumentInputModel {

    @Valid
    private List<CargoDescuentoInputModel> cargos;

    @Valid
    private List<CargoDescuentoInputModel> descuentos;

    @Valid
    private List<AnticipoInputModel> anticipos;

    @Valid
    private PercepcionRelacionadaInputModel percepcion;

    @Valid
    private DetraccionRelacionadaInputModel detraccion;

    @Valid
    private DireccionInputModel direccionDeEntrega;

    public List<CargoDescuentoInputModel> getCargos() {
        return cargos;
    }

    public void setCargos(List<CargoDescuentoInputModel> cargos) {
        this.cargos = cargos;
    }

    public List<CargoDescuentoInputModel> getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(List<CargoDescuentoInputModel> descuentos) {
        this.descuentos = descuentos;
    }

    public List<AnticipoInputModel> getAnticipos() {
        return anticipos;
    }

    public void setAnticipos(List<AnticipoInputModel> anticipos) {
        this.anticipos = anticipos;
    }

    public PercepcionRelacionadaInputModel getPercepcion() {
        return percepcion;
    }

    public void setPercepcion(PercepcionRelacionadaInputModel percepcion) {
        this.percepcion = percepcion;
    }

    public DetraccionRelacionadaInputModel getDetraccion() {
        return detraccion;
    }

    public void setDetraccion(DetraccionRelacionadaInputModel detraccion) {
        this.detraccion = detraccion;
    }

    public DireccionInputModel getDireccionDeEntrega() {
        return direccionDeEntrega;
    }

    public void setDireccionDeEntrega(DireccionInputModel direccionDeEntrega) {
        this.direccionDeEntrega = direccionDeEntrega;
    }

}
