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
package org.openublpe.xmlbuilder.rules.executors;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.kie.api.runtime.KieSession;
import org.kie.kogito.rules.KieRuntimeBuilder;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog10;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog22;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog23;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog7;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog9;
import org.openublpe.xmlbuilder.core.models.input.standard.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.note.creditNote.CreditNoteInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.note.debitNote.DebitNoteInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.PerceptionInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.RetentionInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.SummaryDocumentInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.VoidedDocumentInputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.invoice.InvoiceOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.note.creditNote.CreditNoteOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.note.debitNote.DebitNoteOutputModel;
import org.openublpe.xmlbuilder.core.models.output.sunat.PerceptionOutputModel;
import org.openublpe.xmlbuilder.core.models.output.sunat.RetentionOutputModel;
import org.openublpe.xmlbuilder.core.models.output.sunat.SummaryDocumentOutputModel;
import org.openublpe.xmlbuilder.core.models.output.sunat.VoidedDocumentOutputModel;
import org.openublpe.xmlbuilder.rules.UBLConstants;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;

@ApplicationScoped
public class KieExecutor {

    @ConfigProperty(name = UBLConstants.IGV_KEY)
    BigDecimal igv;

    @ConfigProperty(name = UBLConstants.ICB_KEY)
    BigDecimal icb;

    @ConfigProperty(name = UBLConstants.DEFAULT_MONEDA)
    String defaultMoneda;

    @ConfigProperty(name = UBLConstants.DEFAULT_UNIDAD_MEDIDA)
    String defaultUnidadMedida;

    @ConfigProperty(name = UBLConstants.DEFAULT_TIPO_IGV)
    String defaultTipoIgv;

    @ConfigProperty(name = UBLConstants.DEFAULT_TIPO_NOTA_CREDITO)
    String defaultTipoNotaCredito;

    @ConfigProperty(name = UBLConstants.DEFAULT_TIPO_NOTA_DEBITO)
    String defaultTipoNotaDebito;

    @ConfigProperty(name = UBLConstants.DEFAULT_REGIMEN_PERCEPCION)
    String defaultRegimenPercepcion;

    @ConfigProperty(name = UBLConstants.DEFAULT_REGIMEN_RETENCION)
    String defaultRegimenRetencion;

    @Inject
    KieRuntimeBuilder runtimeBuilder;

    private void setGlobalVariables(KieSession kSession) {
        kSession.setGlobal("IGV", igv);
        kSession.setGlobal("ICB", icb);
        kSession.setGlobal("DEFAULT_MONEDA", defaultMoneda);
        kSession.setGlobal("DEFAULT_UNIDAD_MEDIDA", defaultUnidadMedida);
        kSession.setGlobal("DEFAULT_TIPO_IGV", Catalog.valueOfCode(Catalog7.class, defaultTipoIgv)
                .orElseThrow(() -> new IllegalStateException("application.properties does not have a valid value for DEFAULT_TIPO_IGV"))
        );
        kSession.setGlobal("DEFAULT_TIPO_NOTA_CREDITO", Catalog.valueOfCode(Catalog9.class, defaultTipoNotaCredito)
                .orElseThrow(() -> new IllegalStateException("application.properties does not have a valid value for DEFAULT_TIPO_NOTA_CREDITO"))
        );
        kSession.setGlobal("DEFAULT_TIPO_NOTA_DEBITO", Catalog.valueOfCode(Catalog10.class, defaultTipoNotaDebito)
                .orElseThrow(() -> new IllegalStateException("application.properties does not have a valid value for DEFAULT_TIPO_NOTA_DEBITO"))
        );
        kSession.setGlobal("DEFAULT_REGIMEN_PERCEPCION", Catalog.valueOfCode(Catalog22.class, defaultRegimenPercepcion)
                .orElseThrow(() -> new IllegalStateException("application.properties does not have a valid value for DEFAULT_REGIMEN_PERCEPCION"))
        );
        kSession.setGlobal("DEFAULT_REGIMEN_RETENCION", Catalog.valueOfCode(Catalog23.class, defaultRegimenRetencion)
                .orElseThrow(() -> new IllegalStateException("application.properties does not have a valid value for DEFAULT_REGIMEN_RETENCION"))
        );
    }

    public InvoiceOutputModel getInvoiceOutputModel(InvoiceInputModel input) {
        InvoiceOutputModel output = new InvoiceOutputModel();

        KieSession ksession = runtimeBuilder.newKieSession();
        setGlobalVariables(ksession);

        ksession.insert(output);
        ksession.insert(input);
        ksession.fireAllRules();

        return output;
    }

    public CreditNoteOutputModel getCreditNoteOutputModel(CreditNoteInputModel input) {
        CreditNoteOutputModel output = new CreditNoteOutputModel();

        KieSession ksession = runtimeBuilder.newKieSession();
        setGlobalVariables(ksession);

        ksession.insert(output);
        ksession.insert(input);
        ksession.fireAllRules();

        return output;
    }

    public DebitNoteOutputModel getDebitNoteOutputModel(DebitNoteInputModel input) {
        DebitNoteOutputModel output = new DebitNoteOutputModel();

        KieSession ksession = runtimeBuilder.newKieSession();
        setGlobalVariables(ksession);

        ksession.insert(output);
        ksession.insert(input);
        ksession.fireAllRules();

        return output;
    }

    public VoidedDocumentOutputModel getVoidedDocumentOutputModel(VoidedDocumentInputModel input) {
        VoidedDocumentOutputModel output = new VoidedDocumentOutputModel();

        KieSession ksession = runtimeBuilder.newKieSession();
        setGlobalVariables(ksession);

        ksession.insert(output);
        ksession.insert(input);
        ksession.fireAllRules();

        return output;
    }

    public SummaryDocumentOutputModel getSummaryDocumentOutputModel(SummaryDocumentInputModel input) {
        SummaryDocumentOutputModel output = new SummaryDocumentOutputModel();

        KieSession ksession = runtimeBuilder.newKieSession();
        setGlobalVariables(ksession);

        ksession.insert(output);
        ksession.insert(input);
        ksession.fireAllRules();

        return output;
    }

    public PerceptionOutputModel getPerceptionOutputModel(PerceptionInputModel input) {
        PerceptionOutputModel output = new PerceptionOutputModel();

        KieSession ksession = runtimeBuilder.newKieSession();
        setGlobalVariables(ksession);

        ksession.insert(output);
        ksession.insert(input);
        ksession.fireAllRules();

        return output;
    }

    public RetentionOutputModel getRetentionOutputModel(RetentionInputModel input) {
        RetentionOutputModel output = new RetentionOutputModel();

        KieSession ksession = runtimeBuilder.newKieSession();
        setGlobalVariables(ksession);

        ksession.insert(output);
        ksession.insert(input);
        ksession.fireAllRules();

        return output;
    }

}
