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
package org.openublpe.xmlbuilder.apisigner.resources;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.openublpe.xmlbuilder.api.resources.utils.ResourceUtils;
import org.openublpe.xmlbuilder.apisigner.models.KeyManager;
import org.openublpe.xmlbuilder.apisigner.models.ModelRuntimeException;
import org.openublpe.xmlbuilder.apisigner.models.OrganizationModel;
import org.openublpe.xmlbuilder.apisigner.models.OrganizationProvider;
import org.openublpe.xmlbuilder.apisigner.xml.XMLSigner;
import org.openublpe.xmlbuilder.apisigner.xml.XmlSignatureHelper;
import org.openublpe.xmlbuilder.core.models.input.constraints.CompleteValidation;
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
import org.openublpe.xmlbuilder.rules.executors.KieExecutor;
import org.openublpe.xmlbuilder.templates.executors.FreemarkerExecutor;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.ws.rs.Consumes;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;

@Path("/organizations/{" + OrganizationsDocumentsResource.ORGANIZATION_ID + "}/documents")
@Consumes(MediaType.APPLICATION_JSON)
public class OrganizationsDocumentsResource {

    static final String SIGN_REFERENCE_ID = "SignID";
    static final String ORGANIZATION_ID = "organizationId";

    @Inject
    KieExecutor kieExecutor;

    @Inject
    FreemarkerExecutor freemarkerExecutor;

    @Inject
    KeyManager keystore;

    @Inject
    OrganizationProvider organizationProvider;

    private KeyManager.ActiveRsaKey getActiveRsaKey(OrganizationModel organization) {
        if (organization.getUseCustomCertificates()) {
            return keystore.getActiveRsaKey(organization);
        } else {
            OrganizationModel masterOrganization = organizationProvider.getOrganizationById(OrganizationModel.MASTER_ID)
                    .orElseThrow(() -> new ModelRuntimeException("No se encontró la organización master"));
            return keystore.getActiveRsaKey(masterOrganization);
        }
    }

    private Document signXML(KeyManager.ActiveRsaKey activeRsaKey, String xml)
            throws ParserConfigurationException, SAXException, IOException, NoSuchAlgorithmException, XMLSignatureException, InvalidAlgorithmParameterException, MarshalException {
        return XMLSigner.firmarXML(xml, SIGN_REFERENCE_ID, activeRsaKey.getCertificate(), activeRsaKey.getPrivateKey());
    }

    @POST
    @Path("/invoice/enrich")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Enriched object created.")
    })
    @Operation(summary = "Enriches the input")
    @Tag(name = "enrich")
    public InvoiceOutputModel enrichInvoiceModel(
            @Parameter(example = "master")
            @PathParam(ORGANIZATION_ID) String organizationId,
            @NotNull @Valid @ConvertGroup(to = CompleteValidation.class) InvoiceInputModel input
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
            @Parameter(example = "master")
            @PathParam(ORGANIZATION_ID) String organizationId,
            @NotNull @Valid @ConvertGroup(to = CompleteValidation.class) CreditNoteInputModel input
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
            @Parameter(example = "master")
            @PathParam(ORGANIZATION_ID) String organizationId,
            @NotNull @Valid @ConvertGroup(to = CompleteValidation.class) DebitNoteInputModel input
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
            @Parameter(example = "master")
            @PathParam(ORGANIZATION_ID) String organizationId,
            @NotNull @Valid @ConvertGroup(to = CompleteValidation.class) VoidedDocumentInputModel input
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
            @Parameter(example = "master")
            @PathParam(ORGANIZATION_ID) String organizationId,
            @NotNull @Valid @ConvertGroup(to = CompleteValidation.class) SummaryDocumentInputModel input
    ) {
        return kieExecutor.getSummaryDocumentOutputModel(input);
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
            @Parameter(example = "master")
            @PathParam(ORGANIZATION_ID) String organizationId,
            @NotNull @Valid @ConvertGroup(to = CompleteValidation.class) PerceptionInputModel input
    ) {
        return kieExecutor.getPerceptionOutputModel(input);
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
            @Parameter(example = "master")
            @PathParam(ORGANIZATION_ID) String organizationId,
            @NotNull @Valid @ConvertGroup(to = CompleteValidation.class) RetentionInputModel input
    ) {
        return kieExecutor.getRetentionOutputModel(input);
    }

//    @POST
//    @Path("/despatch-advice/enrich")
//    @Produces(MediaType.APPLICATION_JSON)
//    public DespatchAdviceOutputModel enrichDespatchAdviceOutputModel(
//            @PathParam(ORGANIZATION_ID) String organizationId,
//            @Valid DespatchAdviceInputModel input
//    ) {
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
            @Parameter(example = "master")
            @PathParam(ORGANIZATION_ID) String organizationId,
            @NotNull @Valid @ConvertGroup(to = CompleteValidation.class) InvoiceInputModel input
    ) throws Exception {
        OrganizationModel organization = organizationProvider.getOrganizationById(organizationId).orElseThrow(() -> new NotFoundException("Organización no encontrada"));
        KeyManager.ActiveRsaKey activeRsaKey = getActiveRsaKey(organization);

        InvoiceOutputModel output = kieExecutor.getInvoiceOutputModel(input);
        String xml = freemarkerExecutor.createInvoiceXml(output);

        Document xmlSignedDocument;
        try {
            xmlSignedDocument = signXML(activeRsaKey, xml);
        } catch (ParserConfigurationException | SAXException | IOException | NoSuchAlgorithmException | XMLSignatureException | InvalidAlgorithmParameterException | MarshalException e) {
            throw new InternalServerErrorException(e);
        }

        return Response.ok(XmlSignatureHelper.getBytesFromDocument(xmlSignedDocument))
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
    public Response createCreditNoteXml(
            @Parameter(example = "master")
            @PathParam(ORGANIZATION_ID) String organizationId,
            @NotNull @Valid @ConvertGroup(to = CompleteValidation.class) CreditNoteInputModel input
    ) throws Exception {
        OrganizationModel organization = organizationProvider.getOrganizationById(organizationId).orElseThrow(() -> new NotFoundException("Organización no encontrada"));
        KeyManager.ActiveRsaKey activeRsaKey = getActiveRsaKey(organization);

        CreditNoteOutputModel output = kieExecutor.getCreditNoteOutputModel(input);
        String xml = freemarkerExecutor.createCreditNote(output);

        Document xmlSignedDocument;
        try {
            xmlSignedDocument = signXML(activeRsaKey, xml);
        } catch (ParserConfigurationException | SAXException | IOException | NoSuchAlgorithmException | XMLSignatureException | InvalidAlgorithmParameterException | MarshalException e) {
            throw new InternalServerErrorException(e);
        }

        return Response.ok(XmlSignatureHelper.getBytesFromDocument(xmlSignedDocument))
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
    public Response createDebitNoteXml(
            @Parameter(example = "master")
            @PathParam(ORGANIZATION_ID) String organizationId,
            @NotNull @Valid @ConvertGroup(to = CompleteValidation.class) DebitNoteInputModel input
    ) throws Exception {
        OrganizationModel organization = organizationProvider.getOrganizationById(organizationId).orElseThrow(() -> new NotFoundException("Organización no encontrada"));
        KeyManager.ActiveRsaKey activeRsaKey = getActiveRsaKey(organization);

        DebitNoteOutputModel output = kieExecutor.getDebitNoteOutputModel(input);
        String xml = freemarkerExecutor.createDebitNote(output);

        Document xmlSignedDocument;
        try {
            xmlSignedDocument = signXML(activeRsaKey, xml);
        } catch (ParserConfigurationException | SAXException | IOException | NoSuchAlgorithmException | XMLSignatureException | InvalidAlgorithmParameterException | MarshalException e) {
            throw new InternalServerErrorException(e);
        }

        return Response.ok(XmlSignatureHelper.getBytesFromDocument(xmlSignedDocument))
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
    public Response createVoidedDocumentXml(
            @Parameter(example = "master")
            @PathParam(ORGANIZATION_ID) String organizationId,
            @NotNull @Valid @ConvertGroup(to = CompleteValidation.class) VoidedDocumentInputModel input
    ) throws Exception {
        OrganizationModel organization = organizationProvider.getOrganizationById(organizationId).orElseThrow(() -> new NotFoundException("Organización no encontrada"));
        KeyManager.ActiveRsaKey activeRsaKey = getActiveRsaKey(organization);

        VoidedDocumentOutputModel output = kieExecutor.getVoidedDocumentOutputModel(input);
        String xml = freemarkerExecutor.createVoidedDocument(output);

        Document xmlSignedDocument;
        try {
            xmlSignedDocument = signXML(activeRsaKey, xml);
        } catch (ParserConfigurationException | SAXException | IOException | NoSuchAlgorithmException | XMLSignatureException | InvalidAlgorithmParameterException | MarshalException e) {
            throw new InternalServerErrorException(e);
        }

        return Response.ok(XmlSignatureHelper.getBytesFromDocument(xmlSignedDocument))
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
    public Response createSummaryDocumentXml(
            @Parameter(example = "master")
            @PathParam(ORGANIZATION_ID) String organizationId,
            @NotNull @Valid @ConvertGroup(to = CompleteValidation.class) SummaryDocumentInputModel input
    ) throws Exception {
        OrganizationModel organization = organizationProvider.getOrganizationById(organizationId).orElseThrow(() -> new NotFoundException("Organización no encontrada"));
        KeyManager.ActiveRsaKey activeRsaKey = getActiveRsaKey(organization);

        SummaryDocumentOutputModel output = kieExecutor.getSummaryDocumentOutputModel(input);
        String xml = freemarkerExecutor.createSummaryDocument(output);

        Document xmlSignedDocument;
        try {
            xmlSignedDocument = signXML(activeRsaKey, xml);
        } catch (ParserConfigurationException | SAXException | IOException | NoSuchAlgorithmException | XMLSignatureException | InvalidAlgorithmParameterException | MarshalException e) {
            throw new InternalServerErrorException(e);
        }

        return Response.ok(XmlSignatureHelper.getBytesFromDocument(xmlSignedDocument))
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
    public Response createPerceptionXml(
            @Parameter(example = "master")
            @PathParam(ORGANIZATION_ID) String organizationId,
            @NotNull @Valid @ConvertGroup(to = CompleteValidation.class) PerceptionInputModel input
    ) throws Exception {
        OrganizationModel organization = organizationProvider.getOrganizationById(organizationId).orElseThrow(() -> new NotFoundException("Organización no encontrada"));
        KeyManager.ActiveRsaKey activeRsaKey = getActiveRsaKey(organization);

        PerceptionOutputModel output = kieExecutor.getPerceptionOutputModel(input);
        String xml = freemarkerExecutor.createPerception(output);

        Document xmlSignedDocument;
        try {
            xmlSignedDocument = signXML(activeRsaKey, xml);
        } catch (ParserConfigurationException | SAXException | IOException | NoSuchAlgorithmException | XMLSignatureException | InvalidAlgorithmParameterException | MarshalException e) {
            throw new InternalServerErrorException(e);
        }

        return Response.ok(XmlSignatureHelper.getBytesFromDocument(xmlSignedDocument))
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
    public Response createRetentionXml(
            @Parameter(example = "master")
            @PathParam(ORGANIZATION_ID) String organizationId,
            @NotNull @Valid @ConvertGroup(to = CompleteValidation.class) RetentionInputModel input
    ) throws Exception {
        OrganizationModel organization = organizationProvider.getOrganizationById(organizationId).orElseThrow(() -> new NotFoundException("Organización no encontrada"));
        KeyManager.ActiveRsaKey activeRsaKey = getActiveRsaKey(organization);

        RetentionOutputModel output = kieExecutor.getRetentionOutputModel(input);
        String xml = freemarkerExecutor.createRetention(output);

        Document xmlSignedDocument;
        try {
            xmlSignedDocument = signXML(activeRsaKey, xml);
        } catch (ParserConfigurationException | SAXException | IOException | NoSuchAlgorithmException | XMLSignatureException | InvalidAlgorithmParameterException | MarshalException e) {
            throw new InternalServerErrorException(e);
        }

        return Response.ok(XmlSignatureHelper.getBytesFromDocument(xmlSignedDocument))
                .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                .build();
    }

//    @POST
//    @Path("/despatch-advice/create")
//    @Produces(MediaType.TEXT_XML)
//    public Response createDespatchAdviceXml(
//            @PathParam(ORGANIZATION_ID) String organizationId,
//            @Valid DespatchAdviceInputModel input
//    ) throws Exception {
//        OrganizationModel organization = organizationProvider.getOrganizationById(organizationId).orElseThrow(() -> new NotFoundException("Organización no encontrada"));
//        KeyManager.ActiveRsaKey activeRsaKey = getActiveRsaKey(organization);
//
//        DespatchAdviceOutputModel output = kieExecutor.getDespatchAdviceOutputModel(input);
//        String xml = freemarkerExecutor.createDespatchAdvice(output);
//
//        Document xmlSignedDocument;
//        try {
//            xmlSignedDocument = signXML(activeRsaKey, xml);
//        } catch (ParserConfigurationException | SAXException | IOException | NoSuchAlgorithmException | XMLSignatureException | InvalidAlgorithmParameterException | MarshalException e) {
//            throw new InternalServerErrorException(e);
//        }
//
//        return Response.ok(XmlSignatureHelper.getBytesFromDocument(xmlSignedDocument))
//                .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
//                .build();
//    }

}
