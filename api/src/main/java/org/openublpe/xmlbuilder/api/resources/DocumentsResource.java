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
package org.openublpe.xmlbuilder.api.resources;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.openublpe.xmlbuilder.api.resources.utils.ResourceUtils;
import org.openublpe.xmlbuilder.core.models.input.constraints.CompleteValidation;
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
import org.openublpe.xmlbuilder.rules.executors.KieExecutor;
import org.openublpe.xmlbuilder.templates.executors.FreemarkerExecutor;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.groups.ConvertGroup;
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
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Enriched object created.")
    })
    @Operation(summary = "Enriches the input")
    @Tag(name = "enrich")
    public InvoiceOutputModel enrichInvoiceModel(
            @Valid @ConvertGroup(to = CompleteValidation.class) InvoiceInputModel input
    ) {
        return kieExecutor.getInvoiceOutputModel(input);
    }

    @POST
    @Path("/credit-note/enrich")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Enriched object created.")
    })
    @Operation(summary = "Enriches the input")
    @Tag(name = "enrich")
    public CreditNoteOutputModel enrichCreditNoteModel(
            @Valid @ConvertGroup(to = CompleteValidation.class) CreditNoteInputModel input
    ) {
        return kieExecutor.getCreditNoteOutputModel(input);
    }

    @POST
    @Path("/debit-note/enrich")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Enriched object created.")
    })
    @Operation(summary = "Enriches the input")
    @Tag(name = "enrich")
    public DebitNoteOutputModel enrichDebitNoteModel(
            @Valid @ConvertGroup(to = CompleteValidation.class) DebitNoteInputModel input
    ) {
        return kieExecutor.getDebitNoteOutputModel(input);
    }

    @POST
    @Path("/voided-document/enrich")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Enriched object created.")
    })
    @Operation(summary = "Enriches the input")
    @Tag(name = "enrich")
    public VoidedDocumentOutputModel enrichVoidedDocumentModel(
            @Valid @ConvertGroup(to = CompleteValidation.class) VoidedDocumentInputModel input
    ) {
        return kieExecutor.getVoidedDocumentOutputModel(input);
    }

    @POST
    @Path("/summary-document/enrich")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Enriched object created.")
    })
    @Operation(summary = "Enriches the input")
    @Tag(name = "enrich")
    public SummaryDocumentOutputModel enrichSummaryDocumentModel(
            @Valid @ConvertGroup(to = CompleteValidation.class) SummaryDocumentInputModel input
    ) {
        return kieExecutor.getSummaryDocumentOutputModel(input);
    }

//    @POST
//    @Path("/perception/enrich")
//    @Produces(MediaType.APPLICATION_JSON)
//    public PerceptionOutputModel enrichPerceptionOutputModel(@Valid PerceptionInputModel input) {
//        return kieExecutor.getPerceptionOutputModel(input);
//    }
//
//    @POST
//    @Path("/retention/enrich")
//    @Produces(MediaType.APPLICATION_JSON)
//    public RetentionOutputModel enrichRetentionOutputModel(@Valid RetentionInputModel input) {
//        return kieExecutor.getRetentionOutputModel(input);
//    }
//
//    @POST
//    @Path("/despatch-advice/enrich")
//    @Produces(MediaType.APPLICATION_JSON)
//    public DespatchAdviceOutputModel enrichDespatchAdviceOutputModel(@Valid DespatchAdviceInputModel input) {
//        return kieExecutor.getDespatchAdviceOutputModel(input);
//    }


    @POST
    @Path("/invoice/create")
    @Produces(MediaType.TEXT_XML)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "XML created.")
    })
    @Operation(summary = "Create a XML file from the input")
    @Tag(name = "create")
    public Response createInvoiceXml(
            @Valid @ConvertGroup(to = CompleteValidation.class) InvoiceInputModel input
    ) {
        InvoiceOutputModel output = kieExecutor.getInvoiceOutputModel(input);
        String xml = freemarkerExecutor.createInvoiceXml(output);

        return Response.ok(xml)
                .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                .build();
    }

    @POST
    @Path("/credit-note/create")
    @Produces(MediaType.TEXT_XML)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "XML created.")
    })
    @Operation(summary = "Create a XML file from the input")
    @Tag(name = "create")
    public Response createCreditNote(
            @Valid @ConvertGroup(to = CompleteValidation.class) CreditNoteInputModel input
    ) {
        CreditNoteOutputModel output = kieExecutor.getCreditNoteOutputModel(input);
        String xml = freemarkerExecutor.createCreditNote(output);

        return Response.ok(xml)
                .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                .build();
    }

    @POST
    @Path("/debit-note/create")
    @Produces(MediaType.TEXT_XML)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "XML created.")
    })
    @Operation(summary = "Create a XML file from the input")
    @Tag(name = "create")
    public Response createDebitNote(
            @Valid @ConvertGroup(to = CompleteValidation.class) DebitNoteInputModel input
    ) {
        DebitNoteOutputModel output = kieExecutor.getDebitNoteOutputModel(input);
        String xml = freemarkerExecutor.createDebitNote(output);

        return Response.ok(xml)
                .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                .build();
    }

    @POST
    @Path("/voided-document/create")
    @Produces(MediaType.TEXT_XML)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "XML created.")
    })
    @Operation(summary = "Create a XML file from the input")
    @Tag(name = "create")
    public Response createVoidedDocument(
            @Valid @ConvertGroup(to = CompleteValidation.class) VoidedDocumentInputModel input
    ) {
        VoidedDocumentOutputModel output = kieExecutor.getVoidedDocumentOutputModel(input);
        String xml = freemarkerExecutor.createVoidedDocument(output);

        return Response.ok(xml)
                .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                .build();
    }

    @POST
    @Path("/summary-document/create")
    @Produces(MediaType.TEXT_XML)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "XML created.")
    })
    @Operation(summary = "Create a XML file from the input")
    @Tag(name = "create")
    public Response createSummaryDocument(
            @Valid @ConvertGroup(to = CompleteValidation.class) SummaryDocumentInputModel input
    ) {
        SummaryDocumentOutputModel output = kieExecutor.getSummaryDocumentOutputModel(input);
        String xml = freemarkerExecutor.createSummaryDocument(output);

        return Response.ok(xml)
                .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                .build();
    }

//    @POST
//    @Path("/perception/create")
//    @Produces(MediaType.TEXT_XML)
//    public Response createPerception(@Valid PerceptionInputModel input) {
//        PerceptionOutputModel output = kieExecutor.getPerceptionOutputModel(input);
//        String xml = freemarkerExecutor.createPerception(output);
//
//        return Response.ok(xml)
//                .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
//                .build();
//    }
//
//    @POST
//    @Path("/retention/create")
//    @Produces(MediaType.TEXT_XML)
//    public Response createRetention(@Valid RetentionInputModel input) {
//        RetentionOutputModel output = kieExecutor.getRetentionOutputModel(input);
//        String xml = freemarkerExecutor.createRetention(output);
//
//        return Response.ok(xml)
//                .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
//                .build();
//    }
//
//    @POST
//    @Path("/despatch-advice/create")
//    @Produces(MediaType.TEXT_XML)
//    public Response createDespatchAdvice(@Valid DespatchAdviceInputModel input) {
//        DespatchAdviceOutputModel output = kieExecutor.getDespatchAdviceOutputModel(input);
//        String xml = freemarkerExecutor.createDespatchAdvice(output);
//
//        return Response.ok(xml)
//                .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
//                .build();
//    }
}
