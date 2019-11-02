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

@Path("/invoices")
@Consumes(MediaType.APPLICATION_JSON)
public class InvoiceResource {

    @ConfigProperty(name = UBLConstants.IGV_KEY, defaultValue = UBLConstants.IGV_DEFAULT_VALUE)
    BigDecimal IGV;

    @Inject
    Configuration configuration;

    @Inject
    KieRuntimeBuilder runtimeBuilder;

//    @Inject @Named("invoiceKS")
//    RuleUnit<SessionMemory> ruleUnit;

    @POST
    @Path("/build-xml")
    @Produces(MediaType.TEXT_XML)
    public Response generateXML(@Valid InvoiceInputModel input) {
        InvoiceOutputModel output = new InvoiceOutputModel();

        KieSession ksession = runtimeBuilder.newKieSession();
        ksession.setGlobal("IGV", IGV);
        ksession.insert(output);
        ksession.insert(input);
        ksession.fireAllRules();

//        ProveedorOutputModel proveedor = new ProveedorOutputModel();
//        proveedor.setRuc("10467793549");
//        proveedor.setCodigoPostal("123");
//        proveedor.setNombreComercial("-");
//        proveedor.setRazonSocial("carlos");
//
//        ClienteOutputModel cliente = new ClienteOutputModel();
//        cliente.setNombre("fer");
//        cliente.setCodigoDocumentoIdentidad("46779354");
//        cliente.setNumeroDocumentoIdentidad("06");

//        InvoiceOutputModel output = new InvoiceOutputModel();
//        output.setCantidadItemsVendidos(5);
//        output.setCodigoTipoComprobante("01");
//        output.setFechaEmision("123456");
//        output.setHoraEmision("321654");
//        output.setMoneda("PEN");
//        output.setSerieNumero("F001");
//        output.setProveedor(proveedor);
//        output.setCliente(cliente);

        StringWriter buffer;
        try {
            Template template = configuration.getTemplate(FreemarkerConstants.INVOICE_TEMPLATE_2_1);

            buffer = new StringWriter();
            template.process(output, buffer);
            buffer.flush();
        } catch (IOException | TemplateException e) {
            throw new InternalServerErrorException(e);
        }

        System.out.println(buffer);

        return Response.ok(buffer.toString())
                .header("Content-Disposition", "attachment; filename=\"" + "invoice.xml" + "\"")
                .build();
    }
}