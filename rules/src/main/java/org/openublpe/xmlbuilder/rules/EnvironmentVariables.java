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
package org.openublpe.xmlbuilder.rules;

public class EnvironmentVariables {
    public static final String OUTPUT_VALIDATOR_ENABLE = "openubl.interceptor.output-validator.enable";

    public static final String IGV_KEY = "openubl.igv";
    public static final String IVAP_KEY = "openubl.ivap";
    public static final String ICB_KEY = "openubl.icb";
    public static final String DEFAULT_MONEDA = "openubl.defaultMoneda";
    public static final String DEFAULT_UNIDAD_MEDIDA = "openubl.defaultUnidadMedida";
    public static final String DEFAULT_TIPO_IGV = "openubl.defaultTipoIgv";
    public static final String DEFAULT_TIPO_NOTA_CREDITO = "openubl.defaultTipoNotaCredito";
    public static final String DEFAULT_TIPO_NOTA_DEBITO = "openubl.defaultTipoNotaDebito";
    public static final String DEFAULT_REGIMEN_PERCEPCION = "openubl.defaultRegimenPercepcion";
    public static final String DEFAULT_REGIMEN_RETENCION = "openubl.defaultRegimenRetencion";

    private EnvironmentVariables() {
        // Just constants
    }
}
