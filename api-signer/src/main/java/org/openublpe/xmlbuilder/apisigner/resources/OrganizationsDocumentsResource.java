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

import org.openublpe.xmlbuilder.api.resources.utils.ResourceUtils;
import org.openublpe.xmlbuilder.apisigner.models.KeyManager;
import org.openublpe.xmlbuilder.apisigner.models.ModelRuntimeException;
import org.openublpe.xmlbuilder.apisigner.models.OrganizationModel;
import org.openublpe.xmlbuilder.apisigner.models.OrganizationProvider;
import org.openublpe.xmlbuilder.apisigner.xml.XMLSigner;
import org.openublpe.xmlbuilder.apisigner.xml.XmlSignatureHelper;
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
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.validation.Valid;
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
    public InvoiceOutputModel enrichInvoiceModel(
            @PathParam(ORGANIZATION_ID) String organizationId,
            @Valid InvoiceInputModel input
    ) {
        return kieExecutor.getInvoiceOutputModel(input);
    }

    @POST
    @Path("/credit-note/enrich")
    @Produces(MediaType.APPLICATION_JSON)
    public CreditNoteOutputModel enrichCreditNoteModel(
            @PathParam(ORGANIZATION_ID) String organizationId,
            @Valid CreditNoteInputModel input
    ) {
        return kieExecutor.getCreditNoteOutputModel(input);
    }

    @POST
    @Path("/debit-note/enrich")
    @Produces(MediaType.APPLICATION_JSON)
    public DebitNoteOutputModel enrichDebitNoteModel(
            @PathParam(ORGANIZATION_ID) String organizationId,
            @Valid DebitNoteInputModel input
    ) {
        return kieExecutor.getDebitNoteOutputModel(input);
    }

    @POST
    @Path("/voided-document/enrich")
    @Produces(MediaType.APPLICATION_JSON)
    public VoidedDocumentOutputModel enrichVoidedDocumentModel(
            @PathParam(ORGANIZATION_ID) String organizationId,
            @Valid VoidedDocumentInputModel input
    ) {
        return kieExecutor.getVoidedDocumentOutputModel(input);
    }

    @POST
    @Path("/summary-document/enrich")
    @Produces(MediaType.APPLICATION_JSON)
    public SummaryDocumentOutputModel enrichSummaryDocumentModel(
            @PathParam(ORGANIZATION_ID) String organizationId,
            @Valid SummaryDocumentInputModel input
    ) {
        return kieExecutor.getSummaryDocumentOutputModel(input);
    }

    @POST
    @Path("/invoice/create")
    @Produces(MediaType.TEXT_XML)
    public Response createInvoiceXml(
            @PathParam(ORGANIZATION_ID) String organizationId,
            @Valid InvoiceInputModel input
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
    public Response createCreditNoteXml(
            @PathParam(ORGANIZATION_ID) String organizationId,
            @Valid CreditNoteInputModel input
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
    public Response createDebitNoteXml(
            @PathParam(ORGANIZATION_ID) String organizationId,
            @Valid DebitNoteInputModel input
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
    public Response createVoidedDocumentXml(
            @PathParam(ORGANIZATION_ID) String organizationId,
            @Valid VoidedDocumentInputModel input
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
    public Response createSummaryDocumentXml(
            @PathParam(ORGANIZATION_ID) String organizationId,
            @Valid SummaryDocumentInputModel input
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

}
