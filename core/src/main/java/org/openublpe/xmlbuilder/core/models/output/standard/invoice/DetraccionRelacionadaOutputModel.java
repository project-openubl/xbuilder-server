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

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog54;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog59;
import org.openublpe.xmlbuilder.core.models.output.standard.DocumentoTributarioRelacionadoOutputModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class DetraccionRelacionadaOutputModel extends DocumentoTributarioRelacionadoOutputModel {

    @NotNull
    private Catalog59 medioDePago;

    @NotNull
    private Catalog54 tipoBienServicio;

    @Min(0)
    @NotNull
    private BigDecimal porcentaje;

    private String numeroCuentaBancaria;

    public Catalog59 getMedioDePago() {
        return medioDePago;
    }

    public void setMedioDePago(Catalog59 medioDePago) {
        this.medioDePago = medioDePago;
    }

    public Catalog54 getTipoBienServicio() {
        return tipoBienServicio;
    }

    public void setTipoBienServicio(Catalog54 tipoBienServicio) {
        this.tipoBienServicio = tipoBienServicio;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getNumeroCuentaBancaria() {
        return numeroCuentaBancaria;
    }

    public void setNumeroCuentaBancaria(String numeroCuentaBancaria) {
        this.numeroCuentaBancaria = numeroCuentaBancaria;
    }

}
