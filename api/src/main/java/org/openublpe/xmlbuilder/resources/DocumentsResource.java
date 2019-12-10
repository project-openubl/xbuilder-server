package org.openublpe.xmlbuilder.resources;

import org.openublpe.xmlbuilder.templates.executors.FreemarkerExecutor;
import org.openublpe.xmlbuilder.templates.executors.KieExecutor;
import org.openublpe.xmlbuilder.core.models.input.standard.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.note.creditNote.CreditNoteInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.note.debitNote.DebitNoteInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.SummaryDocumentInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.VoidedDocumentInputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.invoice.InvoiceOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.note.creditNote.CreditNoteOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.note.debitNote.DebitNoteOutputModel;
import org.openublpe.xmlbuilder.core.models.output.sunat.SummaryDocumentOutputModel;
import org.openublpe.xmlbuilder.core.models.output.sunat.VoidedDocumentOutputModel;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/documents")
@Consumes(MediaType.APPLICATION_JSON)
public class DocumentsResource {

    @Inject
    KieExecutor kieExecutor;

    @Inject
    FreemarkerExecutor freemarkerExecutor;

    @POST
    @Path("/invoice/enrich")
    @Produces(MediaType.APPLICATION_JSON)
    public InvoiceOutputModel enrichInvoiceModel(@Valid InvoiceInputModel input) {
        return kieExecutor.getInvoiceOutputModel(input);
    }

    @POST
    @Path("/credit-note/enrich")
    @Produces(MediaType.APPLICATION_JSON)
    public CreditNoteOutputModel enrichCreditNoteModel(@Valid CreditNoteInputModel input) {
        return kieExecutor.getCreditNoteOutputModel(input);
    }

    @POST
    @Path("/debit-note/enrich")
    @Produces(MediaType.APPLICATION_JSON)
    public DebitNoteOutputModel enrichDebitNoteModel(@Valid DebitNoteInputModel input) {
        return kieExecutor.getDebitNoteOutputModel(input);
    }

    @POST
    @Path("/voided-document/enrich")
    @Produces(MediaType.APPLICATION_JSON)
    public VoidedDocumentOutputModel enrichVoidedDocumentModel(@Valid VoidedDocumentInputModel input) {
        return kieExecutor.getVoidedDocumentOutputModel(input);
    }

    @POST
    @Path("/summary-document/enrich")
    @Produces(MediaType.APPLICATION_JSON)
    public SummaryDocumentOutputModel enrichSummaryDocumentModel(@Valid SummaryDocumentInputModel input) {
        return kieExecutor.getSummaryDocumentOutputModel(input);
    }


    @POST
    @Path("/invoice/create")
    @Produces(MediaType.TEXT_XML)
    public Response createInvoiceXml(@Valid InvoiceInputModel input) {
        InvoiceOutputModel output = kieExecutor.getInvoiceOutputModel(input);
        String xml = freemarkerExecutor.createInvoiceXml(output);

        return Response.ok(xml)
                .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                .build();
    }

    @POST
    @Path("/credit-note/create")
    @Produces(MediaType.TEXT_XML)
    public Response createCreditNote(@Valid CreditNoteInputModel input) {
        CreditNoteOutputModel output = kieExecutor.getCreditNoteOutputModel(input);
        String xml = freemarkerExecutor.createCreditNote(output);

        return Response.ok(xml)
                .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                .build();
    }

    @POST
    @Path("/debit-note/create")
    @Produces(MediaType.TEXT_XML)
    public Response createDebitNote(@Valid DebitNoteInputModel input) {
        DebitNoteOutputModel output = kieExecutor.getDebitNoteOutputModel(input);
        String xml = freemarkerExecutor.createDebitNote(output);

        return Response.ok(xml)
                .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                .build();
    }

    @POST
    @Path("/voided-document/create")
    @Produces(MediaType.TEXT_XML)
    public Response createVoidedDocument(@Valid VoidedDocumentInputModel input) {
        VoidedDocumentOutputModel output = kieExecutor.getVoidedDocumentOutputModel(input);
        String xml = freemarkerExecutor.createVoidedDocument(output);

        return Response.ok(xml)
                .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                .build();
    }

    @POST
    @Path("/summary-document/create")
    @Produces(MediaType.TEXT_XML)
    public Response createSummaryDocument(@Valid SummaryDocumentInputModel input) {
        SummaryDocumentOutputModel output = kieExecutor.getSummaryDocumentOutputModel(input);
        String xml = freemarkerExecutor.createSummaryDocument(output);

        return Response.ok(xml)
                .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                .build();
    }
}
