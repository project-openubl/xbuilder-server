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
package org.openublpe.xmlbuilder.templates.executors;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.openublpe.xmlbuilder.core.models.output.standard.invoice.InvoiceOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.note.creditNote.CreditNoteOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.note.debitNote.DebitNoteOutputModel;
import org.openublpe.xmlbuilder.core.models.output.sunat.PerceptionOutputModel;
import org.openublpe.xmlbuilder.core.models.output.sunat.RetentionOutputModel;
import org.openublpe.xmlbuilder.core.models.output.sunat.SummaryDocumentOutputModel;
import org.openublpe.xmlbuilder.core.models.output.sunat.VoidedDocumentOutputModel;
import org.openublpe.xmlbuilder.templates.FreemarkerConstants;
import org.openublpe.xmlbuilder.templates.FreemarkerGlobalConfiguration;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.io.StringWriter;

@ApplicationScoped
public class FreemarkerExecutor {

    @Inject
    FreemarkerGlobalConfiguration freemarkerGlobalConfiguration;

    public String createInvoiceXml(InvoiceOutputModel output) {
        StringWriter buffer;
        try {
            Template template = freemarkerGlobalConfiguration.getConfiguration().getTemplate(FreemarkerConstants.INVOICE_TEMPLATE_2_1);

            buffer = new StringWriter();
            template.process(output, buffer);
            buffer.flush();
        } catch (IOException | TemplateException e) {
            throw new IllegalStateException(e);
        }

        return buffer.toString();
    }

    public String createCreditNote(CreditNoteOutputModel output) {
        StringWriter buffer;
        try {
            Template template = freemarkerGlobalConfiguration.getConfiguration().getTemplate(FreemarkerConstants.CREDIT_NOTE_TEMPLATE_2_1);

            buffer = new StringWriter();
            template.process(output, buffer);
            buffer.flush();
        } catch (IOException | TemplateException e) {
            throw new IllegalStateException(e);
        }

        return buffer.toString();
    }

    public String createDebitNote(DebitNoteOutputModel output) {
        StringWriter buffer;
        try {
            Template template = freemarkerGlobalConfiguration.getConfiguration().getTemplate(FreemarkerConstants.DEBIT_NOTE_TEMPLATE_2_1);

            buffer = new StringWriter();
            template.process(output, buffer);
            buffer.flush();
        } catch (IOException | TemplateException e) {
            throw new IllegalStateException(e);
        }

        return buffer.toString();
    }

    public String createVoidedDocument(VoidedDocumentOutputModel output) {
        StringWriter buffer;
        try {
            Template template = freemarkerGlobalConfiguration.getConfiguration().getTemplate(FreemarkerConstants.VOIDED_DOCUMENT_TEMPLATE_2_0);

            buffer = new StringWriter();
            template.process(output, buffer);
            buffer.flush();
        } catch (IOException | TemplateException e) {
            throw new IllegalStateException(e);
        }

        return buffer.toString();
    }

    public String createSummaryDocument(SummaryDocumentOutputModel output) {
        StringWriter buffer;
        try {
            Template template = freemarkerGlobalConfiguration.getConfiguration().getTemplate(FreemarkerConstants.SUMMARY_DOCUMENT_TEMPLATE_2_0);

            buffer = new StringWriter();
            template.process(output, buffer);
            buffer.flush();
        } catch (IOException | TemplateException e) {
            throw new IllegalStateException(e);
        }

        return buffer.toString();
    }

    public String createPerception(PerceptionOutputModel output) {
        StringWriter buffer;
        try {
            Template template = freemarkerGlobalConfiguration.getConfiguration().getTemplate(FreemarkerConstants.PERCEPTION_TEMPLATE_2_0);

            buffer = new StringWriter();
            template.process(output, buffer);
            buffer.flush();
        } catch (IOException | TemplateException e) {
            throw new IllegalStateException(e);
        }

        return buffer.toString();
    }

    public String createRetention(RetentionOutputModel output) {
        StringWriter buffer;
        try {
            Template template = freemarkerGlobalConfiguration.getConfiguration().getTemplate(FreemarkerConstants.RETENTION_TEMPLATE_2_0);

            buffer = new StringWriter();
            template.process(output, buffer);
            buffer.flush();
        } catch (IOException | TemplateException e) {
            throw new IllegalStateException(e);
        }

        return buffer.toString();
    }
}
