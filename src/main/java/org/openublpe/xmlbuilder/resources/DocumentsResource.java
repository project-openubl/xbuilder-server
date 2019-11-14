package org.openublpe.xmlbuilder.resources;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.kie.api.runtime.KieSession;
import org.kie.kogito.rules.KieRuntimeBuilder;
import org.openublpe.xmlbuilder.FreemarkerConstants;
import org.openublpe.xmlbuilder.UBLConstants;
import org.openublpe.xmlbuilder.models.input.standard.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.models.input.standard.note.creditNote.CreditNoteInputModel;
import org.openublpe.xmlbuilder.models.input.standard.note.debitNote.DebitNoteInputModel;
import org.openublpe.xmlbuilder.models.input.sunat.VoidedDocumentInputModel;
import org.openublpe.xmlbuilder.models.output.standard.invoice.InvoiceOutputModel;
import org.openublpe.xmlbuilder.models.output.standard.note.creditNote.CreditNoteOutputModel;
import org.openublpe.xmlbuilder.models.output.standard.note.debitNote.DebitNoteOutputModel;
import org.openublpe.xmlbuilder.models.output.sunat.VoidedDocumentOutputModel;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;

@Path("/documents")
@Consumes(MediaType.APPLICATION_JSON)
public class DocumentsResource {

    @ConfigProperty(name = UBLConstants.IGV_KEY, defaultValue = UBLConstants.IGV_DEFAULT_VALUE)
    BigDecimal IGV;

    @Inject
    Configuration configuration;

    @Inject
    KieRuntimeBuilder runtimeBuilder;

//    @Inject @Named("invoiceKS")
//    RuleUnit<SessionMemory> ruleUnit;

    @Inject
    Validator validator;

    @POST
    @Path("/invoice/create")
    @Produces(MediaType.TEXT_XML)
    public Response createInvoice(@Valid InvoiceInputModel input) {
        InvoiceOutputModel output = new InvoiceOutputModel();

        KieSession ksession = runtimeBuilder.newKieSession();
        ksession.setGlobal("IGV", IGV);
        ksession.insert(output);
        ksession.insert(input);
        ksession.fireAllRules();

        StringWriter buffer;
        try {
            Template template = configuration.getTemplate(FreemarkerConstants.INVOICE_TEMPLATE_2_1);

            buffer = new StringWriter();
            template.process(output, buffer);
            buffer.flush();
        } catch (IOException | TemplateException e) {
            throw new InternalServerErrorException(e);
        }

        return Response.ok(buffer.toString())
                .header("Content-Disposition", "attachment; filename=\"" + "invoice.xml" + "\"")
                .build();
    }

    @POST
    @Path("/credit-note/create")
    @Produces(MediaType.TEXT_XML)
    public Response createCreditNote(@Valid CreditNoteInputModel input) {
        CreditNoteOutputModel output = new CreditNoteOutputModel();

        KieSession ksession = runtimeBuilder.newKieSession();
        ksession.setGlobal("IGV", IGV);
        ksession.insert(output);
        ksession.insert(input);
        ksession.fireAllRules();

        StringWriter buffer;
        try {
            Template template = configuration.getTemplate(FreemarkerConstants.CREDIT_NOTE_TEMPLATE_2_1);

            buffer = new StringWriter();
            template.process(output, buffer);
            buffer.flush();
        } catch (IOException | TemplateException e) {
            throw new InternalServerErrorException(e);
        }

        return Response.ok(buffer.toString())
                .header("Content-Disposition", "attachment; filename=\"" + "creditNote.xml" + "\"")
                .build();
    }

    @POST
    @Path("/debit-note/create")
    @Produces(MediaType.TEXT_XML)
    public Response createDebitNote(@Valid DebitNoteInputModel input) {
        DebitNoteOutputModel output = new DebitNoteOutputModel();

        KieSession ksession = runtimeBuilder.newKieSession();
        ksession.setGlobal("IGV", IGV);
        ksession.insert(output);
        ksession.insert(input);
        ksession.fireAllRules();

        StringWriter buffer;
        try {
            Template template = configuration.getTemplate(FreemarkerConstants.DEBIT_NOTE_TEMPLATE_2_1);

            buffer = new StringWriter();
            template.process(output, buffer);
            buffer.flush();
        } catch (IOException | TemplateException e) {
            throw new InternalServerErrorException(e);
        }

        return Response.ok(buffer.toString())
                .header("Content-Disposition", "attachment; filename=\"" + "debitNote.xml" + "\"")
                .build();
    }

    @POST
    @Path("/voided-document/create")
    @Produces(MediaType.TEXT_XML)
    public Response createVoidedDocument(@Valid VoidedDocumentInputModel input) {
        VoidedDocumentOutputModel output = new VoidedDocumentOutputModel();

        KieSession ksession = runtimeBuilder.newKieSession();

        ksession.insert(output);
        ksession.insert(input);
        ksession.fireAllRules();

        StringWriter buffer;
        try {
            Template template = configuration.getTemplate(FreemarkerConstants.VOIDED_DOCUMENT_TEMPLATE_2_0);

            buffer = new StringWriter();
            template.process(output, buffer);
            buffer.flush();
        } catch (IOException | TemplateException e) {
            throw new InternalServerErrorException(e);
        }

        return Response.ok(buffer.toString())
                .header("Content-Disposition", "attachment; filename=\"" + "voidedDocument.xml" + "\"")
                .build();
    }
}
