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
package io.github.project.openubl.xmlbuilder.idm;

import java.math.BigDecimal;
import java.util.Date;
import java.util.TimeZone;

public class ServerInfoRepresentation {

    private Clock clock;
    private ApplicationConfig applicationConfig;

    public Clock getClock() {
        return clock;
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    public ApplicationConfig getApplicationConfig() {
        return applicationConfig;
    }

    public void setApplicationConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    public static class Clock {
        private Date time;
        private TimeZone timeZone;

        public TimeZone getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(TimeZone timeZone) {
            this.timeZone = timeZone;
        }

        public Date getTime() {
            return time;
        }

        public void setTime(Date time) {
            this.time = time;
        }
    }

    public static class ApplicationConfig {
        private BigDecimal igv;
        private BigDecimal ivap;
        private BigDecimal icb;
        private String defaultMoneda;
        private String defaultUnidadMedida;
        private String defaultTipoIgv;
        private String defaultTipoNotaCredito;
        private String defaultTipoNotaDebito;
        private String defaultRegimenPercepcion;
        private String defaultRegimenRetencion;
        private String timeZone;
        private String serverKeystoreLocation;
        private String serverKeystorePassword;

        public BigDecimal getIgv() {
            return igv;
        }

        public void setIgv(BigDecimal igv) {
            this.igv = igv;
        }

        public BigDecimal getIvap() {
            return ivap;
        }

        public void setIvap(BigDecimal ivap) {
            this.ivap = ivap;
        }

        public BigDecimal getIcb() {
            return icb;
        }

        public void setIcb(BigDecimal icb) {
            this.icb = icb;
        }

        public String getDefaultMoneda() {
            return defaultMoneda;
        }

        public void setDefaultMoneda(String defaultMoneda) {
            this.defaultMoneda = defaultMoneda;
        }

        public String getDefaultUnidadMedida() {
            return defaultUnidadMedida;
        }

        public void setDefaultUnidadMedida(String defaultUnidadMedida) {
            this.defaultUnidadMedida = defaultUnidadMedida;
        }

        public String getDefaultTipoIgv() {
            return defaultTipoIgv;
        }

        public void setDefaultTipoIgv(String defaultTipoIgv) {
            this.defaultTipoIgv = defaultTipoIgv;
        }

        public String getDefaultTipoNotaCredito() {
            return defaultTipoNotaCredito;
        }

        public void setDefaultTipoNotaCredito(String defaultTipoNotaCredito) {
            this.defaultTipoNotaCredito = defaultTipoNotaCredito;
        }

        public String getDefaultTipoNotaDebito() {
            return defaultTipoNotaDebito;
        }

        public void setDefaultTipoNotaDebito(String defaultTipoNotaDebito) {
            this.defaultTipoNotaDebito = defaultTipoNotaDebito;
        }

        public String getDefaultRegimenPercepcion() {
            return defaultRegimenPercepcion;
        }

        public void setDefaultRegimenPercepcion(String defaultRegimenPercepcion) {
            this.defaultRegimenPercepcion = defaultRegimenPercepcion;
        }

        public String getDefaultRegimenRetencion() {
            return defaultRegimenRetencion;
        }

        public void setDefaultRegimenRetencion(String defaultRegimenRetencion) {
            this.defaultRegimenRetencion = defaultRegimenRetencion;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(String timeZone) {
            this.timeZone = timeZone;
        }

        public String getServerKeystoreLocation() {
            return serverKeystoreLocation;
        }

        public void setServerKeystoreLocation(String serverKeystoreLocation) {
            this.serverKeystoreLocation = serverKeystoreLocation;
        }

        public String getServerKeystorePassword() {
            return serverKeystorePassword;
        }

        public void setServerKeystorePassword(String serverKeystorePassword) {
            this.serverKeystorePassword = serverKeystorePassword;
        }
    }
}
