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
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog52;
import org.openublpe.xmlbuilder.core.models.input.common.DireccionInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.invoice.CargoDescuentoInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.invoice.DetraccionRelacionadaInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.invoice.PercepcionRelacionadaInputModel;
import org.openublpe.xmlbuilder.core.models.output.common.ClienteOutputModel;
import org.openublpe.xmlbuilder.core.models.output.common.DireccionOutputModel;
import org.openublpe.xmlbuilder.core.models.output.common.FirmanteOutputModel;
import org.openublpe.xmlbuilder.core.models.output.common.ProveedorOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.DocumentImpuestosOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.DocumentLineOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.DocumentMonetaryTotalOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.DocumentOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.DocumentoTributarioRelacionadoOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.GuiaRemisionRelacionadaOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.ImpuestoOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.ImpuestoTotalOutputModel;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
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

    public static final class Builder {
        DocumentMonetaryTotalOutputModel totales;
        private String moneda;
        private String serieNumero;
        private String horaEmision;
        private String fechaEmision;
        private ClienteOutputModel cliente;
        private FirmanteOutputModel firmante;
        private ProveedorOutputModel proveedor;
        private DocumentImpuestosOutputModel impuestos;
        private List<DocumentLineOutputModel> detalle;
        private Catalog1 tipoInvoice;

        private Builder() {
        }

        public static Builder anInvoiceOutputModel() {
            return new Builder();
        }

        public Builder withMoneda(String moneda) {
            this.moneda = moneda;
            return this;
        }

        public Builder withSerieNumero(String serieNumero) {
            this.serieNumero = serieNumero;
            return this;
        }

        public Builder withHoraEmision(String horaEmision) {
            this.horaEmision = horaEmision;
            return this;
        }

        public Builder withFechaEmision(String fechaEmision) {
            this.fechaEmision = fechaEmision;
            return this;
        }

        public Builder withCliente(ClienteOutputModel cliente) {
            this.cliente = cliente;
            return this;
        }

        public Builder withFirmante(FirmanteOutputModel firmante) {
            this.firmante = firmante;
            return this;
        }

        public Builder withProveedor(ProveedorOutputModel proveedor) {
            this.proveedor = proveedor;
            return this;
        }

        public Builder withTotales(DocumentMonetaryTotalOutputModel totales) {
            this.totales = totales;
            return this;
        }

        public Builder withImpuestos(DocumentImpuestosOutputModel impuestos) {
            this.impuestos = impuestos;
            return this;
        }

        public Builder withDetalle(List<DocumentLineOutputModel> detalle) {
            this.detalle = detalle;
            return this;
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
