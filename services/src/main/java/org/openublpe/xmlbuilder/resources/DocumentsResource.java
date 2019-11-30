package org.openublpe.xmlbuilder.resources;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.kie.api.runtime.KieSession;
import org.kie.kogito.rules.KieRuntimeBuilder;
import org.openublpe.xmlbuilder.FreemarkerConstants;
import org.openublpe.xmlbuilder.UBLConstants;
import org.openublpe.xmlbuilder.models.catalogs.Catalog;
import org.openublpe.xmlbuilder.models.catalogs.Catalog7;
import org.openublpe.xmlbuilder.models.input.standard.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.models.input.standard.note.creditNote.CreditNoteInputModel;
import org.openublpe.xmlbuilder.models.input.standard.note.debitNote.DebitNoteInputModel;
import org.openublpe.xmlbuilder.models.input.sunat.SummaryDocumentInputModel;
import org.openublpe.xmlbuilder.models.input.sunat.VoidedDocumentInputModel;
import org.openublpe.xmlbuilder.models.output.standard.invoice.InvoiceOutputModel;
import org.openublpe.xmlbuilder.models.output.standard.note.creditNote.CreditNoteOutputModel;
import org.openublpe.xmlbuilder.models.output.standard.note.debitNote.DebitNoteOutputModel;
import org.openublpe.xmlbuilder.models.output.sunat.SummaryDocumentOutputModel;
import org.openublpe.xmlbuilder.models.output.sunat.VoidedDocumentOutputModel;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;

@Path("/documents")
@Consumes(MediaType.APPLICATION_JSON)
public class DocumentsResource {

    @ConfigProperty(name = UBLConstants.IGV_KEY)
    BigDecimal igv;

    @ConfigProperty(name = UBLConstants.ICB_KEY)
    BigDecimal icb;

    @ConfigProperty(name = UBLConstants.MONEDA)
    String moneda;

    @ConfigProperty(name = UBLConstants.UNIDAD_MEDIDA)
    String unidadMedida;

    @ConfigProperty(name = UBLConstants.TIPO_IGV)
    String tipoIgv;


    @Inject
    Configuration configuration;

    @Inject
    KieRuntimeBuilder runtimeBuilder;

    private void setGlobalVariables(KieSession kSession) {
        kSession.setGlobal("IGV", igv);
        kSession.setGlobal("ICB", icb);
        kSession.setGlobal("MONEDA", moneda);
        kSession.setGlobal("UNIDAD_MEDIDA", unidadMedida);
        kSession.setGlobal("TIPO_IGV", Catalog.valueOfCode(Catalog7.class, tipoIgv)
                .orElseThrow(() -> new IllegalStateException("application.properties does not have a valid value for TIPO_IGV"))
        );
    }

    private String getAttachmentFileName(String fileName) {
        return "attachment; filename=\"" + fileName + "\"";
    }

    private InvoiceOutputModel getInvoiceOutputModel(InvoiceInputModel input) {
        InvoiceOutputModel output = new InvoiceOutputModel();

        KieSession ksession = runtimeBuilder.newKieSession();
        setGlobalVariables(ksession);

        ksession.insert(output);
        ksession.insert(input);
        ksession.fireAllRules();

        return output;
    }

    private CreditNoteOutputModel getCreditNoteOutputModel(CreditNoteInputModel input) {
        CreditNoteOutputModel output = new CreditNoteOutputModel();

        KieSession ksession = runtimeBuilder.newKieSession();
        setGlobalVariables(ksession);

        ksession.insert(output);
        ksession.insert(input);
        ksession.fireAllRules();

        return output;
    }

    private DebitNoteOutputModel getDebitNoteOutputModel(DebitNoteInputModel input) {
        DebitNoteOutputModel output = new DebitNoteOutputModel();

        KieSession ksession = runtimeBuilder.newKieSession();
        setGlobalVariables(ksession);

        ksession.insert(output);
        ksession.insert(input);
        ksession.fireAllRules();

        return output;
    }

    private VoidedDocumentOutputModel getVoidedDocumentOutputModel(VoidedDocumentInputModel input) {
        VoidedDocumentOutputModel output = new VoidedDocumentOutputModel();

        KieSession ksession = runtimeBuilder.newKieSession();
        setGlobalVariables(ksession);

        ksession.insert(output);
        ksession.insert(input);
        ksession.fireAllRules();

        return output;
    }

    private SummaryDocumentOutputModel getSummaryDocumentOutputModel(SummaryDocumentInputModel input) {
        SummaryDocumentOutputModel output = new SummaryDocumentOutputModel();

        KieSession ksession = runtimeBuilder.newKieSession();
        setGlobalVariables(ksession);

        ksession.insert(output);
        ksession.insert(input);
        ksession.fireAllRules();

        return output;
    }


    @POST
    @Path("/invoice/enrich")
    @Produces(MediaType.APPLICATION_JSON)
    public InvoiceOutputModel enrichInvoiceModel(@Valid InvoiceInputModel input) {
        return getInvoiceOutputModel(input);
    }

    @POST
    @Path("/credit-note/enrich")
    @Produces(MediaType.APPLICATION_JSON)
    public CreditNoteOutputModel enrichCreditNoteModel(@Valid CreditNoteInputModel input) {
        return getCreditNoteOutputModel(input);
    }

    @POST
    @Path("/debit-note/enrich")
    @Produces(MediaType.APPLICATION_JSON)
    public DebitNoteOutputModel enrichDebitNoteModel(@Valid DebitNoteInputModel input) {
        return getDebitNoteOutputModel(input);
    }

    @POST
    @Path("/voided-document/enrich")
    @Produces(MediaType.APPLICATION_JSON)
    public VoidedDocumentOutputModel enrichVoidedDocumentModel(@Valid VoidedDocumentInputModel input) {
        return getVoidedDocumentOutputModel(input);
    }

    @POST
    @Path("/summary-document/enrich")
    @Produces(MediaType.APPLICATION_JSON)
    public SummaryDocumentOutputModel enrichSummaryDocumentModel(@Valid SummaryDocumentInputModel input) {
        return getSummaryDocumentOutputModel(input);
    }


    @POST
    @Path("/invoice/create")
    @Produces(MediaType.TEXT_XML)
    public Response createInvoiceXml(@Valid InvoiceInputModel input) {
        InvoiceOutputModel output = getInvoiceOutputModel(input);

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
                .header(HttpHeaders.CONTENT_DISPOSITION, getAttachmentFileName("invoice.xml"))
                .build();
    }

    @POST
    @Path("/credit-note/create")
    @Produces(MediaType.TEXT_XML)
    public Response createCreditNote(@Valid CreditNoteInputModel input) {
        CreditNoteOutputModel output = getCreditNoteOutputModel(input);

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
                .header(HttpHeaders.CONTENT_DISPOSITION, getAttachmentFileName("creditNote.xml"))
                .build();
    }

    @POST
    @Path("/debit-note/create")
    @Produces(MediaType.TEXT_XML)
    public Response createDebitNote(@Valid DebitNoteInputModel input) {
        DebitNoteOutputModel output = getDebitNoteOutputModel(input);

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
                .header(HttpHeaders.CONTENT_DISPOSITION, getAttachmentFileName("debitNote.xml"))
                .build();
    }

    @POST
    @Path("/voided-document/create")
    @Produces(MediaType.TEXT_XML)
    public Response createVoidedDocument(@Valid VoidedDocumentInputModel input) {
        VoidedDocumentOutputModel output = getVoidedDocumentOutputModel(input);

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
                .header(HttpHeaders.CONTENT_DISPOSITION, getAttachmentFileName("voidedDocument.xml"))
                .build();
    }

    @POST
    @Path("/summary-document/create")
    @Produces(MediaType.TEXT_XML)
    public Response createSummaryDocument(@Valid SummaryDocumentInputModel input) {
        SummaryDocumentOutputModel output = getSummaryDocumentOutputModel(input);

        StringWriter buffer;
        try {
            Template template = configuration.getTemplate(FreemarkerConstants.SUMMARY_DOCUMENT_TEMPLATE_2_0);

            buffer = new StringWriter();
            template.process(output, buffer);
            buffer.flush();
        } catch (IOException | TemplateException e) {
            throw new InternalServerErrorException(e);
        }

        return Response.ok(buffer.toString())
                .header(HttpHeaders.CONTENT_DISPOSITION, getAttachmentFileName("summaryDocument.xml"))
                .build();
    }
}
