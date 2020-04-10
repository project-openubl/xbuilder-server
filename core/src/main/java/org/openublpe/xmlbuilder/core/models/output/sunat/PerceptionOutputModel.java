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
package org.openublpe.xmlbuilder.core.models.output.sunat;

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog22;
import org.openublpe.xmlbuilder.core.models.output.common.ClienteOutputModel;
import org.openublpe.xmlbuilder.core.models.output.common.FirmanteOutputModel;
import org.openublpe.xmlbuilder.core.models.output.common.ProveedorOutputModel;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public class PerceptionOutputModel extends PerceptionRetentionOutputModel {

    @NotNull
    private Catalog22 regimen;

    public Catalog22 getRegimen() {
        return regimen;
    }

    public void setRegimen(Catalog22 regimen) {
        this.regimen = regimen;
    }

    public static final class Builder extends PerceptionRetentionOutputModel.Builder {
        private Catalog22 regimen;
        private String serieNumero;
        private String fechaEmision;
        private String observacion;
        private String moneda;
        private BigDecimal importeTotalPercibidoRetenido;
        private BigDecimal importeTotalCobradoPagado;
        private ProveedorOutputModel proveedor;
        private ClienteOutputModel cliente;
        private FirmanteOutputModel firmante;
        private List<PerceptionRetentionLineOutputModel> detalle;

        private Builder() {
        }

        public static Builder aPerceptionOutputModel() {
            return new Builder();
        }

        public Builder withRegimen(Catalog22 regimen) {
            this.regimen = regimen;
            return this;
        }

        public Builder withSerieNumero(String serieNumero) {
            this.serieNumero = serieNumero;
            return this;
        }

        public Builder withFechaEmision(String fechaEmision) {
            this.fechaEmision = fechaEmision;
            return this;
        }

        public Builder withObservacion(String observacion) {
            this.observacion = observacion;
            return this;
        }

        public Builder withMoneda(String moneda) {
            this.moneda = moneda;
            return this;
        }

        public Builder withImporteTotalPercibidoRetenido(BigDecimal importeTotalPercibidoRetenido) {
            this.importeTotalPercibidoRetenido = importeTotalPercibidoRetenido;
            return this;
        }

        public Builder withImporteTotalCobradoPagado(BigDecimal importeTotalCobradoPagado) {
            this.importeTotalCobradoPagado = importeTotalCobradoPagado;
            return this;
        }

        public Builder withProveedor(ProveedorOutputModel proveedor) {
            this.proveedor = proveedor;
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

        public Builder withDetalle(List<PerceptionRetentionLineOutputModel> detalle) {
            this.detalle = detalle;
            return this;
        }

        public PerceptionOutputModel build() {
            PerceptionOutputModel perceptionOutputModel = new PerceptionOutputModel();
            perceptionOutputModel.setRegimen(regimen);
            perceptionOutputModel.setSerieNumero(serieNumero);
            perceptionOutputModel.setFechaEmision(fechaEmision);
            perceptionOutputModel.setObservacion(observacion);
            perceptionOutputModel.setMoneda(moneda);
            perceptionOutputModel.setImporteTotalPercibidoRetenido(importeTotalPercibidoRetenido);
            perceptionOutputModel.setImporteTotalCobradoPagado(importeTotalCobradoPagado);
            perceptionOutputModel.setProveedor(proveedor);
            perceptionOutputModel.setCliente(cliente);
            perceptionOutputModel.setFirmante(firmante);
            perceptionOutputModel.setDetalle(detalle);
            return perceptionOutputModel;
        }
    }
}
