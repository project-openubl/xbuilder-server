package org.openublpe.xmlbuilder.resources;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.kie.api.runtime.KieSession;
import org.kie.kogito.rules.KieRuntimeBuilder;
import org.openublpe.xmlbuilder.FreemarkerConstants;
import org.openublpe.xmlbuilder.UBLConstants;
import org.openublpe.xmlbuilder.models.input.InvoiceInputModel;
import org.openublpe.xmlbuilder.models.output.InvoiceOutputModel;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
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
    public Response createCreditNote(@Valid InvoiceInputModel input) {
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
    @Path("/debit-note/create")
    @Produces(MediaType.TEXT_XML)
    public Response createDebitNote(@Valid InvoiceInputModel input) {
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
}