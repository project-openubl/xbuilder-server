package org.openublpe.xmlbuilder.executors;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.openublpe.xmlbuilder.FreemarkerConstants;
import org.openublpe.xmlbuilder.FreemarkerGlobalConfiguration;
import org.openublpe.xmlbuilder.core.models.output.standard.invoice.InvoiceOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.note.creditNote.CreditNoteOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.note.debitNote.DebitNoteOutputModel;
import org.openublpe.xmlbuilder.core.models.output.sunat.SummaryDocumentOutputModel;
import org.openublpe.xmlbuilder.core.models.output.sunat.VoidedDocumentOutputModel;

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
}
