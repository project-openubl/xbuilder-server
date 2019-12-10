package org.openublpe.xmlbuilder.signer.resources;

import org.openublpe.xmlbuilder.templates.executors.FreemarkerExecutor;
import org.openublpe.xmlbuilder.rules.executors.KieExecutor;
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
import org.openublpe.xmlbuilder.signer.models.KeyManager;
import org.openublpe.xmlbuilder.signer.models.ModelRuntimeException;
import org.openublpe.xmlbuilder.signer.models.OrganizationModel;
import org.openublpe.xmlbuilder.signer.models.OrganizationProvider;
import org.openublpe.xmlbuilder.signer.xml.XMLSigner;
import org.openublpe.xmlbuilder.signer.xml.XMLUtils;
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
import javax.xml.transform.TransformerException;
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
            OrganizationModel masterOrganization = organizationProvider.getOrganization(OrganizationModel.MASTER_ID)
                    .orElseThrow(() -> new ModelRuntimeException("No se encontró la organización master"));
            return keystore.getActiveRsaKey(masterOrganization);
        }
    }

    private Document signXML(KeyManager.ActiveRsaKey activeRsaKey, String xml)
            throws ParserConfigurationException, SAXException, IOException, NoSuchAlgorithmException, XMLSignatureException, InvalidAlgorithmParameterException, MarshalException {
        Document xmlDocument = XMLUtils.stringToDocument(xml);
        return XMLSigner.firmarXML(xmlDocument, SIGN_REFERENCE_ID, activeRsaKey.getCertificate(), activeRsaKey.getPrivateKey());
    }

    @POST
    @Path("/invoice/create")
    @Produces(MediaType.TEXT_XML)
    public Response createInvoiceXml(
            @PathParam(ORGANIZATION_ID) String organizationId,
            @Valid InvoiceInputModel input
    ) throws TransformerException {
        OrganizationModel organization = organizationProvider.getOrganization(organizationId).orElseThrow(() -> new NotFoundException("Organización no encontrada"));
        KeyManager.ActiveRsaKey activeRsaKey = getActiveRsaKey(organization);

        InvoiceOutputModel output = kieExecutor.getInvoiceOutputModel(input);
        String xml = freemarkerExecutor.createInvoiceXml(output);

        Document xmlSignedDocument;
        try {
            xmlSignedDocument = signXML(activeRsaKey, xml);
        } catch (ParserConfigurationException | SAXException | IOException | NoSuchAlgorithmException | XMLSignatureException | InvalidAlgorithmParameterException | MarshalException e) {
            throw new InternalServerErrorException(e);
        }

        return Response.ok(XMLUtils.getBytesFromDocument(xmlSignedDocument))
                .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                .build();
    }

    @POST
    @Path("/credit-note/create")
    @Produces(MediaType.TEXT_XML)
    public Response createCreditNoteXml(
            @PathParam(ORGANIZATION_ID) String organizationId,
            @Valid CreditNoteInputModel input
    ) throws TransformerException {
        OrganizationModel organization = organizationProvider.getOrganization(organizationId).orElseThrow(() -> new NotFoundException("Organización no encontrada"));
        KeyManager.ActiveRsaKey activeRsaKey = getActiveRsaKey(organization);

        CreditNoteOutputModel output = kieExecutor.getCreditNoteOutputModel(input);
        String xml = freemarkerExecutor.createCreditNote(output);

        Document xmlSignedDocument;
        try {
            xmlSignedDocument = signXML(activeRsaKey, xml);
        } catch (ParserConfigurationException | SAXException | IOException | NoSuchAlgorithmException | XMLSignatureException | InvalidAlgorithmParameterException | MarshalException e) {
            throw new InternalServerErrorException(e);
        }

        return Response.ok(XMLUtils.getBytesFromDocument(xmlSignedDocument))
                .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                .build();
    }

    @POST
    @Path("/debit-note/create")
    @Produces(MediaType.TEXT_XML)
    public Response createDebitNoteXml(
            @PathParam(ORGANIZATION_ID) String organizationId,
            @Valid DebitNoteInputModel input
    ) throws TransformerException {
        OrganizationModel organization = organizationProvider.getOrganization(organizationId).orElseThrow(() -> new NotFoundException("Organización no encontrada"));
        KeyManager.ActiveRsaKey activeRsaKey = getActiveRsaKey(organization);

        DebitNoteOutputModel output = kieExecutor.getDebitNoteOutputModel(input);
        String xml = freemarkerExecutor.createDebitNote(output);

        Document xmlSignedDocument;
        try {
            xmlSignedDocument = signXML(activeRsaKey, xml);
        } catch (ParserConfigurationException | SAXException | IOException | NoSuchAlgorithmException | XMLSignatureException | InvalidAlgorithmParameterException | MarshalException e) {
            throw new InternalServerErrorException(e);
        }

        return Response.ok(XMLUtils.getBytesFromDocument(xmlSignedDocument))
                .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                .build();
    }

    @POST
    @Path("/voided-document/create")
    @Produces(MediaType.TEXT_XML)
    public Response createVoidedDocumentXml(
            @PathParam(ORGANIZATION_ID) String organizationId,
            @Valid VoidedDocumentInputModel input
    ) throws TransformerException {
        OrganizationModel organization = organizationProvider.getOrganization(organizationId).orElseThrow(() -> new NotFoundException("Organización no encontrada"));
        KeyManager.ActiveRsaKey activeRsaKey = getActiveRsaKey(organization);

        VoidedDocumentOutputModel output = kieExecutor.getVoidedDocumentOutputModel(input);
        String xml = freemarkerExecutor.createVoidedDocument(output);

        Document xmlSignedDocument;
        try {
            xmlSignedDocument = signXML(activeRsaKey, xml);
        } catch (ParserConfigurationException | SAXException | IOException | NoSuchAlgorithmException | XMLSignatureException | InvalidAlgorithmParameterException | MarshalException e) {
            throw new InternalServerErrorException(e);
        }

        return Response.ok(XMLUtils.getBytesFromDocument(xmlSignedDocument))
                .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                .build();
    }

    @POST
    @Path("/summary-document/create")
    @Produces(MediaType.TEXT_XML)
    public Response createSummaryDocumentXml(
            @PathParam(ORGANIZATION_ID) String organizationId,
            @Valid SummaryDocumentInputModel input
    ) throws TransformerException {
        OrganizationModel organization = organizationProvider.getOrganization(organizationId).orElseThrow(() -> new NotFoundException("Organización no encontrada"));
        KeyManager.ActiveRsaKey activeRsaKey = getActiveRsaKey(organization);

        SummaryDocumentOutputModel output = kieExecutor.getSummaryDocumentOutputModel(input);
        String xml = freemarkerExecutor.createSummaryDocument(output);

        Document xmlSignedDocument;
        try {
            xmlSignedDocument = signXML(activeRsaKey, xml);
        } catch (ParserConfigurationException | SAXException | IOException | NoSuchAlgorithmException | XMLSignatureException | InvalidAlgorithmParameterException | MarshalException e) {
            throw new InternalServerErrorException(e);
        }

        return Response.ok(XMLUtils.getBytesFromDocument(xmlSignedDocument))
                .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                .build();
    }

}
