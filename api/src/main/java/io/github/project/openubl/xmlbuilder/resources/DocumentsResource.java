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
package io.github.project.openubl.xmlbuilder.resources;

import io.github.project.openubl.xmlbuilder.resources.utils.ResourceUtils;
import io.github.project.openubl.xmlbuilderlib.config.XMLBuilderConfig;
import io.github.project.openubl.xmlbuilderlib.facade.DocumentFacade;
import io.github.project.openubl.xmlbuilderlib.facade.DocumentWrapper;
import io.github.project.openubl.xmlbuilderlib.models.input.constraints.CompleteValidation;
import io.github.project.openubl.xmlbuilderlib.models.input.standard.invoice.InvoiceInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.standard.note.creditNote.CreditNoteInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.standard.note.debitNote.DebitNoteInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.sunat.PerceptionInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.sunat.RetentionInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.sunat.SummaryDocumentInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.sunat.VoidedDocumentInputModel;
import io.github.project.openubl.xmlbuilderlib.models.output.standard.invoice.InvoiceOutputModel;
import io.github.project.openubl.xmlbuilderlib.models.output.standard.note.creditNote.CreditNoteOutputModel;
import io.github.project.openubl.xmlbuilderlib.models.output.standard.note.debitNote.DebitNoteOutputModel;
import io.github.project.openubl.xmlbuilderlib.models.output.sunat.PerceptionOutputModel;
import io.github.project.openubl.xmlbuilderlib.models.output.sunat.RetentionOutputModel;
import io.github.project.openubl.xmlbuilderlib.models.output.sunat.SummaryDocumentOutputModel;
import io.github.project.openubl.xmlbuilderlib.models.output.sunat.VoidedDocumentOutputModel;
import io.github.project.openubl.xmlbuilderlib.utils.InputToOutput;
import io.github.project.openubl.xmlbuilderlib.utils.SystemClock;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
    XMLBuilderConfig xmlBuilderConfig;

    @Inject
    SystemClock systemClock;

    @POST
    @Path("/invoice/enrich")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Enriched object created.")
    })
    @Operation(summary = "Enriches the input")
    @Tag(name = "enrich")
    public InvoiceOutputModel enrichInvoiceModel(
            @NotNull @Valid @ConvertGroup(to = CompleteValidation.class) InvoiceInputModel input
    ) {
        return InputToOutput.toOutput(input, xmlBuilderConfig, systemClock);
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
            @NotNull @Valid @ConvertGroup(to = CompleteValidation.class) CreditNoteInputModel input
    ) {
        return InputToOutput.toOutput(input, xmlBuilderConfig, systemClock);
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
            @NotNull @Valid @ConvertGroup(to = CompleteValidation.class) DebitNoteInputModel input
    ) {
        return InputToOutput.toOutput(input, xmlBuilderConfig, systemClock);
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
            @NotNull @Valid @ConvertGroup(to = CompleteValidation.class) VoidedDocumentInputModel input
    ) {
        return InputToOutput.toOutput(input, xmlBuilderConfig, systemClock);
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
            @NotNull @Valid @ConvertGroup(to = CompleteValidation.class) SummaryDocumentInputModel input
    ) {
        return InputToOutput.toOutput(input, xmlBuilderConfig, systemClock);
    }

    @POST
    @Path("/perception/enrich")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Enriched object created.")
    })
    @Operation(summary = "Enriches the input")
    @Tag(name = "enrich")
    public PerceptionOutputModel enrichPerceptionOutputModel(
            @NotNull @Valid @ConvertGroup(to = CompleteValidation.class) PerceptionInputModel input
    ) {
        return InputToOutput.toOutput(input, xmlBuilderConfig, systemClock);
    }

    @POST
    @Path("/retention/enrich")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Enriched object created.")
    })
    @Operation(summary = "Enriches the input")
    @Tag(name = "enrich")
    public RetentionOutputModel enrichRetentionOutputModel(
            @NotNull @Valid @ConvertGroup(to = CompleteValidation.class) RetentionInputModel input
    ) {
        return InputToOutput.toOutput(input, xmlBuilderConfig, systemClock);
    }

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
            @APIResponse(responseCode = "200", description = "XML created.", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @Operation(summary = "Create a XML file from the input")
    @Tag(name = "create")
    public Response createInvoiceXml(
            @NotNull InvoiceInputModel input
    ) {
        DocumentWrapper<InvoiceOutputModel> documentWrapper = DocumentFacade.createXML(input, xmlBuilderConfig, systemClock);
        InvoiceOutputModel output = documentWrapper.getOutput();
        return Response.ok(documentWrapper.getXml())
                .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                .build();
    }

    @POST
    @Path("/credit-note/create")
    @Produces(MediaType.TEXT_XML)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "XML created.", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @Operation(summary = "Create a XML file from the input")
    @Tag(name = "create")
    public Response createCreditNote(
            @NotNull CreditNoteInputModel input
    ) {
        DocumentWrapper<CreditNoteOutputModel> documentWrapper = DocumentFacade.createXML(input, xmlBuilderConfig, systemClock);
        CreditNoteOutputModel output = documentWrapper.getOutput();
        return Response.ok(documentWrapper.getXml())
                .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                .build();
    }

    @POST
    @Path("/debit-note/create")
    @Produces(MediaType.TEXT_XML)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "XML created.", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @Operation(summary = "Create a XML file from the input")
    @Tag(name = "create")
    public Response createDebitNote(
            @NotNull DebitNoteInputModel input
    ) {
        DocumentWrapper<DebitNoteOutputModel> documentWrapper = DocumentFacade.createXML(input, xmlBuilderConfig, systemClock);
        DebitNoteOutputModel output = documentWrapper.getOutput();
        return Response.ok(documentWrapper.getXml())
                .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                .build();
    }

    @POST
    @Path("/voided-document/create")
    @Produces(MediaType.TEXT_XML)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "XML created.", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @Operation(summary = "Create a XML file from the input")
    @Tag(name = "create")
    public Response createVoidedDocument(
            @NotNull VoidedDocumentInputModel input
    ) {
        DocumentWrapper<VoidedDocumentOutputModel> documentWrapper = DocumentFacade.createXML(input, xmlBuilderConfig, systemClock);
        VoidedDocumentOutputModel output = documentWrapper.getOutput();
        return Response.ok(documentWrapper.getXml())
                .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                .build();
    }

    @POST
    @Path("/summary-document/create")
    @Produces(MediaType.TEXT_XML)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "XML created.", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @Operation(summary = "Create a XML file from the input")
    @Tag(name = "create")
    public Response createSummaryDocument(
            @NotNull SummaryDocumentInputModel input
    ) {
        DocumentWrapper<SummaryDocumentOutputModel> documentWrapper = DocumentFacade.createXML(input, xmlBuilderConfig, systemClock);
        SummaryDocumentOutputModel output = documentWrapper.getOutput();
        return Response.ok(documentWrapper.getXml())
                .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                .build();
    }

    @POST
    @Path("/perception/create")
    @Produces(MediaType.TEXT_XML)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "XML created.", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @Operation(summary = "Create a XML file from the input")
    @Tag(name = "create")
    public Response createPerception(
            @NotNull PerceptionInputModel input
    ) {
        DocumentWrapper<PerceptionOutputModel> documentWrapper = DocumentFacade.createXML(input, xmlBuilderConfig, systemClock);
        PerceptionOutputModel output = documentWrapper.getOutput();
        return Response.ok(documentWrapper.getXml())
                .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                .build();
    }

    @POST
    @Path("/retention/create")
    @Produces(MediaType.TEXT_XML)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "XML created.", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @Operation(summary = "Create a XML file from the input")
    @Tag(name = "create")
    public Response createRetention(
            @NotNull RetentionInputModel input
    ) {
        DocumentWrapper<RetentionOutputModel> documentWrapper = DocumentFacade.createXML(input, xmlBuilderConfig, systemClock);
        RetentionOutputModel output = documentWrapper.getOutput();
        return Response.ok(documentWrapper.getXml())
                .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                .build();
    }

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
