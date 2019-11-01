package org.openublpe.xmlbuilder.resources;

import freemarker.template.*;
import org.openublpe.xmlbuilder.FreemarkerInvoiceTemplates;
import org.openublpe.xmlbuilder.models.output.ClienteOutputModel;
import org.openublpe.xmlbuilder.models.output.InvoiceOutputModel;
import org.openublpe.xmlbuilder.models.output.ProveedorOutputModel;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

@Path("/invoices")
public class InvoiceResource {

    @Inject
    Configuration configuration;

    @POST
    @Path("/build-xml")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generateXML() {
        ProveedorOutputModel proveedor = new ProveedorOutputModel();
        proveedor.setRuc("10467793549");
        proveedor.setCodigoPostal("123");
        proveedor.setNombreComercial("-");
        proveedor.setRazonSocial("carlos");

        ClienteOutputModel cliente = new ClienteOutputModel();
        cliente.setNombre("fer");
        cliente.setCodigoDocumentoIdentidad("46779354");
        cliente.setNumeroDocumentoIdentidad("06");

        InvoiceOutputModel output = new InvoiceOutputModel();
        output.setCantidadItemsVendidos(5);
        output.setCodigoTipoComprobante("01");
        output.setFechaEmision("123456");
        output.setHoraEmision("321654");
        output.setMoneda("PEN");
        output.setSerieNumero("F001");
        output.setProveedor(proveedor);
        output.setCliente(cliente);

        StringWriter buffer;
        try {
            Template template = configuration.getTemplate(FreemarkerInvoiceTemplates.INVOICE_TEMPLATE_2_1);

            buffer = new StringWriter();
            template.process(output, buffer);
            buffer.flush();
        } catch (IOException | TemplateException e) {
            throw new InternalServerErrorException(e);
        }

        return Response.ok(buffer.toString(), MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + "myFileName.xml" + "\"")
                .build();
    }
}