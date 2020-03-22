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
package org.openublpe.xmlbuilder.core.models.output.standard.invoice;

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog1;
import org.openublpe.xmlbuilder.core.models.output.common.ClienteOutputModel;
import org.openublpe.xmlbuilder.core.models.output.common.FirmanteOutputModel;
import org.openublpe.xmlbuilder.core.models.output.common.ProveedorOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.DocumentImpuestosOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.DocumentLineOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.DocumentMonetaryTotalOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.DocumentOutputModel;

import javax.validation.constraints.NotNull;
import java.util.List;

public class InvoiceOutputModel extends DocumentOutputModel {

    @NotNull
    private Catalog1 tipoInvoice;

    public Catalog1 getTipoInvoice() {
        return tipoInvoice;
    }

    public void setTipoInvoice(Catalog1 tipoInvoice) {
        this.tipoInvoice = tipoInvoice;
    }

    public static final class Builder extends DocumentOutputModel.Builder {
        private Catalog1 tipoInvoice;

        private Builder() {
        }

        public static Builder anInvoiceOutputModel() {
            return new Builder();
        }

        public Builder withTipoInvoice(Catalog1 tipoInvoice) {
            this.tipoInvoice = tipoInvoice;
            return this;
        }

        public InvoiceOutputModel build() {
            InvoiceOutputModel invoiceOutputModel = new InvoiceOutputModel();
            invoiceOutputModel.setMoneda(moneda);
            invoiceOutputModel.setSerieNumero(serieNumero);
            invoiceOutputModel.setHoraEmision(horaEmision);
            invoiceOutputModel.setFechaEmision(fechaEmision);
            invoiceOutputModel.setCliente(cliente);
            invoiceOutputModel.setFirmante(firmante);
            invoiceOutputModel.setProveedor(proveedor);
            invoiceOutputModel.setTotales(totales);
            invoiceOutputModel.setImpuestos(impuestos);
            invoiceOutputModel.setDetalle(detalle);
            invoiceOutputModel.setTipoInvoice(tipoInvoice);
            return invoiceOutputModel;
        }
    }
}
