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
package io.github.project.openubl.xmlbuilder.resources;

import io.github.project.openubl.xmlbuilder.resources.utils.ResourceUtils;
import io.github.project.openubl.xmlbuilderlib.clock.SystemClock;
import io.github.project.openubl.xmlbuilderlib.config.Config;
import io.github.project.openubl.xmlbuilderlib.freemarker.FreemarkerExecutor;
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
import io.github.project.openubl.xmlbuilderlib.xml.XMLSigner;
import org.keycloak.common.util.KeyUtils;
import org.keycloak.common.util.PemUtils;
import org.w3c.dom.Document;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

@Path("/documents")
@Consumes(MediaType.APPLICATION_JSON)
public class DocumentsResource {

    public final static String SIGNATURE_ID = "PROJECT-OPENUBL";

    public final static String X_HEADER_PRIVATEKEY = "X-OPENBUL-PRIVATEKEY";
    public final static String X_HEADER_CERTIFICATEKEY = "X-OPENUBL-CERTIFICATEKEY";

    @Inject
    Config config;

    @Inject
    SystemClock systemClock;

    @POST
    @Path("/invoice/enrich")
    @Produces(MediaType.APPLICATION_JSON)
    public InvoiceOutputModel enrichInvoiceModel(
            @NotNull @Valid @ConvertGroup(to = CompleteValidation.class) InvoiceInputModel input
    ) {
        return InputToOutput.toOutput(input, config, systemClock);
    }

    @POST
    @Path("/credit-note/enrich")
    @Produces(MediaType.APPLICATION_JSON)
    public CreditNoteOutputModel enrichCreditNoteModel(
            @NotNull @Valid @ConvertGroup(to = CompleteValidation.class) CreditNoteInputModel input
    ) {
        return InputToOutput.toOutput(input, config, systemClock);
    }

    @POST
    @Path("/debit-note/enrich")
    @Produces(MediaType.APPLICATION_JSON)
    public DebitNoteOutputModel enrichDebitNoteModel(
            @NotNull @Valid @ConvertGroup(to = CompleteValidation.class) DebitNoteInputModel input
    ) {
        return InputToOutput.toOutput(input, config, systemClock);
    }

    @POST
    @Path("/voided-document/enrich")
    @Produces(MediaType.APPLICATION_JSON)
    public VoidedDocumentOutputModel enrichVoidedDocumentModel(
            @NotNull @Valid @ConvertGroup(to = CompleteValidation.class) VoidedDocumentInputModel input
    ) {
        return InputToOutput.toOutput(input, config, systemClock);
    }

    @POST
    @Path("/summary-document/enrich")
    @Produces(MediaType.APPLICATION_JSON)
    public SummaryDocumentOutputModel enrichSummaryDocumentModel(
            @NotNull @Valid @ConvertGroup(to = CompleteValidation.class) SummaryDocumentInputModel input
    ) {
        return InputToOutput.toOutput(input, config, systemClock);
    }

    @POST
    @Path("/perception/enrich")
    @Produces(MediaType.APPLICATION_JSON)
    public PerceptionOutputModel enrichPerceptionOutputModel(
            @NotNull @Valid @ConvertGroup(to = CompleteValidation.class) PerceptionInputModel input
    ) {
        return InputToOutput.toOutput(input, config, systemClock);
    }

    @POST
    @Path("/retention/enrich")
    @Produces(MediaType.APPLICATION_JSON)
    public RetentionOutputModel enrichRetentionOutputModel(
            @NotNull @Valid @ConvertGroup(to = CompleteValidation.class) RetentionInputModel input
    ) {
        return InputToOutput.toOutput(input, config, systemClock);
    }

//    @POST
//    @Path("/despatch-advice/enrich")
//    @Produces(MediaType.APPLICATION_JSON)
//    public DespatchAdviceOutputModel enrichDespatchAdviceOutputModel(@Valid DespatchAdviceInputModel input) {
//        return kieExecutor.getDespatchAdviceOutputModel(input);
//    }

    private Document signXML(String xml, String privateRsaKeyPem, String certificatePem) {
        PrivateKey privateKey = PemUtils.decodePrivateKey(privateRsaKeyPem);
        X509Certificate certificate = PemUtils.decodeCertificate(certificatePem);

        PublicKey publicKey = KeyUtils.extractPublicKey(privateKey);
        KeyPair keyPair = new KeyPair(publicKey, privateKey);

        try {
            return XMLSigner.signXML(xml, SIGNATURE_ID, certificate, keyPair.getPrivate());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @POST
    @Path("/invoice/create")
    @Produces(MediaType.TEXT_XML)
    public Response createInvoiceXml(
            @HeaderParam(X_HEADER_PRIVATEKEY) String privateRsaKeyPem,
            @HeaderParam(X_HEADER_CERTIFICATEKEY) String certificatePem,
            @NotNull @Valid @ConvertGroup(to = CompleteValidation.class) InvoiceInputModel input
    ) {
        InvoiceOutputModel output = InputToOutput.toOutput(input, config, systemClock);
        String xml = FreemarkerExecutor.createXML(output);

        if (privateRsaKeyPem != null && certificatePem != null) {
            Document document = signXML(xml, privateRsaKeyPem, certificatePem);

            return Response.ok(document)
                    .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                    .build();
        }

        return Response.ok(xml)
                .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                .build();
    }

    @POST
    @Path("/credit-note/create")
    @Produces(MediaType.TEXT_XML)
    public Response createCreditNote(
            @HeaderParam(X_HEADER_PRIVATEKEY) String privateRsaKeyPem,
            @HeaderParam(X_HEADER_CERTIFICATEKEY) String certificatePem,
            @NotNull @Valid @ConvertGroup(to = CompleteValidation.class) CreditNoteInputModel input
    ) {
        CreditNoteOutputModel output = InputToOutput.toOutput(input, config, systemClock);
        String xml = FreemarkerExecutor.createXML(output);

        if (privateRsaKeyPem != null && certificatePem != null) {
            Document document = signXML(xml, privateRsaKeyPem, certificatePem);
            return Response.ok(document)
                    .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                    .build();
        }

        return Response.ok(xml)
                .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                .build();
    }

    @POST
    @Path("/debit-note/create")
    @Produces(MediaType.TEXT_XML)
    public Response createDebitNote(
            @HeaderParam(X_HEADER_PRIVATEKEY) String privateRsaKeyPem,
            @HeaderParam(X_HEADER_CERTIFICATEKEY) String certificatePem,
            @NotNull @Valid @ConvertGroup(to = CompleteValidation.class) DebitNoteInputModel input
    ) {
        DebitNoteOutputModel output = InputToOutput.toOutput(input, config, systemClock);
        String xml = FreemarkerExecutor.createXML(output);

        if (privateRsaKeyPem != null && certificatePem != null) {
            Document document = signXML(xml, privateRsaKeyPem, certificatePem);
            return Response.ok(document)
                    .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                    .build();
        }

        return Response.ok(xml)
                .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                .build();
    }

    @POST
    @Path("/voided-document/create")
    @Produces(MediaType.TEXT_XML)
    public Response createVoidedDocument(
            @HeaderParam(X_HEADER_PRIVATEKEY) String privateRsaKeyPem,
            @HeaderParam(X_HEADER_CERTIFICATEKEY) String certificatePem,
            @NotNull @Valid @ConvertGroup(to = CompleteValidation.class) VoidedDocumentInputModel input
    ) {
        VoidedDocumentOutputModel output = InputToOutput.toOutput(input, config, systemClock);
        String xml = FreemarkerExecutor.createXML(output);

        if (privateRsaKeyPem != null && certificatePem != null) {
            Document document = signXML(xml, privateRsaKeyPem, certificatePem);
            return Response.ok(document)
                    .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                    .build();
        }

        return Response.ok(xml)
                .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                .build();
    }

    @POST
    @Path("/summary-document/create")
    @Produces(MediaType.TEXT_XML)
    public Response createSummaryDocument(
            @HeaderParam(X_HEADER_PRIVATEKEY) String privateRsaKeyPem,
            @HeaderParam(X_HEADER_CERTIFICATEKEY) String certificatePem,
            @NotNull @Valid @ConvertGroup(to = CompleteValidation.class) SummaryDocumentInputModel input
    ) {
        SummaryDocumentOutputModel output = InputToOutput.toOutput(input, config, systemClock);
        String xml = FreemarkerExecutor.createXML(output);

        if (privateRsaKeyPem != null && certificatePem != null) {
            Document document = signXML(xml, privateRsaKeyPem, certificatePem);
            return Response.ok(document)
                    .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                    .build();
        }

        return Response.ok(xml)
                .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                .build();
    }

    @POST
    @Path("/perception/create")
    @Produces(MediaType.TEXT_XML)
    public Response createPerception(
            @HeaderParam(X_HEADER_PRIVATEKEY) String privateRsaKeyPem,
            @HeaderParam(X_HEADER_CERTIFICATEKEY) String certificatePem,
            @NotNull @Valid @ConvertGroup(to = CompleteValidation.class) PerceptionInputModel input
    ) {
        PerceptionOutputModel output = InputToOutput.toOutput(input, config, systemClock);
        String xml = FreemarkerExecutor.createXML(output);

        if (privateRsaKeyPem != null && certificatePem != null) {
            Document document = signXML(xml, privateRsaKeyPem, certificatePem);
            return Response.ok(document)
                    .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                    .build();
        }

        return Response.ok(xml)
                .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                .build();
    }

    @POST
    @Path("/retention/create")
    @Produces(MediaType.TEXT_XML)
    public Response createRetention(
            @HeaderParam(X_HEADER_PRIVATEKEY) String privateRsaKeyPem,
            @HeaderParam(X_HEADER_CERTIFICATEKEY) String certificatePem,
            @NotNull @Valid @ConvertGroup(to = CompleteValidation.class) RetentionInputModel input
    ) {
        RetentionOutputModel output = InputToOutput.toOutput(input, config, systemClock);
        String xml = FreemarkerExecutor.createXML(output);

        if (privateRsaKeyPem != null && certificatePem != null) {
            Document document = signXML(xml, privateRsaKeyPem, certificatePem);
            return Response.ok(document)
                    .header(HttpHeaders.CONTENT_DISPOSITION, ResourceUtils.getAttachmentFileName(output.getSerieNumero() + ".xml"))
                    .build();
        }

        return Response.ok(xml)
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
