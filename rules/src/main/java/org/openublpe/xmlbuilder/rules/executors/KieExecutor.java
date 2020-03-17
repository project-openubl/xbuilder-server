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
package org.openublpe.xmlbuilder.rules.executors;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.openublpe.xmlbuilder.core.models.input.standard.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.invoice.InvoiceOutputModel;
import org.openublpe.xmlbuilder.rules.EnvironmentVariables;
import org.openublpe.xmlbuilder.rules.factory.DocumentFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;

@ApplicationScoped
public class KieExecutor {

    @ConfigProperty(name = EnvironmentVariables.IGV_KEY)
    BigDecimal igv;

    @ConfigProperty(name = EnvironmentVariables.ICB_KEY)
    BigDecimal icb;

    @ConfigProperty(name = EnvironmentVariables.DEFAULT_MONEDA)
    String defaultMoneda;

    @ConfigProperty(name = EnvironmentVariables.DEFAULT_UNIDAD_MEDIDA)
    String defaultUnidadMedida;

    @ConfigProperty(name = EnvironmentVariables.DEFAULT_TIPO_IGV)
    String defaultTipoIgv;

    @ConfigProperty(name = EnvironmentVariables.DEFAULT_TIPO_NOTA_CREDITO)
    String defaultTipoNotaCredito;

    @ConfigProperty(name = EnvironmentVariables.DEFAULT_TIPO_NOTA_DEBITO)
    String defaultTipoNotaDebito;

    @ConfigProperty(name = EnvironmentVariables.DEFAULT_REGIMEN_PERCEPCION)
    String defaultRegimenPercepcion;

    @ConfigProperty(name = EnvironmentVariables.DEFAULT_REGIMEN_RETENCION)
    String defaultRegimenRetencion;

    @Inject
    DocumentFactory documentFactory;

    @OutputValidator
    public InvoiceOutputModel getInvoiceOutputModel(InvoiceInputModel input) {
        return documentFactory.getInvoiceOutput(input);
    }
}
