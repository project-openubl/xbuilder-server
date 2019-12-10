package org.openublpe.xmlbuilder.apisigner.resources;

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog1;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog19;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog6;
import org.openublpe.xmlbuilder.core.models.input.common.ClienteInputModel;
import org.openublpe.xmlbuilder.core.models.input.common.FirmanteInputModel;
import org.openublpe.xmlbuilder.core.models.input.common.ProveedorInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.DetalleInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.note.creditNote.CreditNoteInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.note.debitNote.DebitNoteInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.SummaryDocumentInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.SummaryDocumentLineInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.VoidedDocumentInputModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class InputGenerator {

    public static FirmanteInputModel getFirmante() {
        FirmanteInputModel result = new FirmanteInputModel();
        result.setRuc("11111111111");
        result.setRazonSocial("Firmante");
        return result;
    }

    public static ProveedorInputModel getProveedor() {
        ProveedorInputModel proveedor = new ProveedorInputModel();
        proveedor.setRuc("22222222222");
        proveedor.setRazonSocial("Proveedor");
        proveedor.setCodigoPostal("010101");
        return proveedor;
    }

    public static ClienteInputModel getClienteConRUC() {
        ClienteInputModel cliente = new ClienteInputModel();
        cliente.setNombre("Cliente");
        cliente.setNumeroDocumentoIdentidad("33333333333");
        cliente.setTipoDocumentoIdentidad(Catalog6.RUC.toString());
        return cliente;
    }

    public static ClienteInputModel getClienteConDNI() {
        ClienteInputModel cliente = new ClienteInputModel();
        cliente.setNombre("Cliente");
        cliente.setNumeroDocumentoIdentidad("33333333");
        cliente.setTipoDocumentoIdentidad(Catalog6.DNI.toString());
        return cliente;
    }

    public static InvoiceInputModel buildInvoiceInputModel() {
        InvoiceInputModel input = new InvoiceInputModel();
        input.setSerie("F001");
        input.setNumero(1);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.NOVEMBER, 8);
        input.setFechaEmision(calendar.getTimeInMillis());

        input.setFirmante(getFirmante());
        input.setProveedor(getProveedor());
        input.setCliente(getClienteConRUC());

        List<DetalleInputModel> detalle = new ArrayList<>();
        input.setDetalle(detalle);

        DetalleInputModel item1 = new DetalleInputModel();
        detalle.add(item1);
        item1.setDescripcion("Item");
        item1.setCantidad(BigDecimal.ONE);
        item1.setPrecioUnitario(new BigDecimal("100"));

        return input;
    }

    public static CreditNoteInputModel buildCreditNoteInputModel() {
        CreditNoteInputModel input = new CreditNoteInputModel();

        input.setSerie("F001");
        input.setNumero(123);
        input.setFechaEmision(new Date().getTime());

        input.setFirmante(getFirmante());
        input.setProveedor(getProveedor());
        input.setCliente(getClienteConRUC());


        List<DetalleInputModel> detalle = new ArrayList<>();
        input.setDetalle(detalle);

        //
        DetalleInputModel item1 = new DetalleInputModel();
        detalle.add(item1);
        item1.setDescripcion("Item1");
        item1.setCantidad(BigDecimal.ONE);
        item1.setPrecioUnitario(BigDecimal.TEN);

        DetalleInputModel item2 = new DetalleInputModel();
        detalle.add(item2);
        item2.setDescripcion("item2");
        item2.setCantidad(BigDecimal.TEN);
        item2.setPrecioUnitario(BigDecimal.ONE);


        input.setSerieNumeroInvoiceReference("F009-9");
        input.setDescripcionSustentoInvoiceReference("El cliente lo rechazó");
        return input;
    }

    public static DebitNoteInputModel buildDebitNoteInputModel() {
        DebitNoteInputModel input = new DebitNoteInputModel();

        input.setSerie("F001");
        input.setNumero(123);
        input.setFechaEmision(new Date().getTime());

        input.setFirmante(getFirmante());

        input.setProveedor(getProveedor());
        input.setCliente(getClienteConRUC());

        List<DetalleInputModel> detalle = new ArrayList<>();
        input.setDetalle(detalle);

        DetalleInputModel item1 = new DetalleInputModel();
        detalle.add(item1);
        item1.setDescripcion("Item1");
        item1.setCantidad(BigDecimal.ONE);
        item1.setPrecioUnitario(BigDecimal.TEN);

        DetalleInputModel item2 = new DetalleInputModel();
        detalle.add(item2);
        item2.setDescripcion("item2");
        item2.setCantidad(BigDecimal.TEN);
        item2.setPrecioUnitario(BigDecimal.ONE);


        //
        input.setSerieNumeroInvoiceReference("F009-9");
        input.setDescripcionSustentoInvoiceReference("El cliente lo rechazó");
        return input;
    }

    public static VoidedDocumentInputModel buildVoidedDocumentInputModel() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.NOVEMBER, 8);


        VoidedDocumentInputModel input = new VoidedDocumentInputModel();
        input.setNumero(1);
        input.setFechaEmision(calendar.getTimeInMillis());

        input.setFirmante(getFirmante());
        input.setProveedor(getProveedor());

        calendar.add(Calendar.DAY_OF_MONTH, -1);
        input.setSerieNumeroDocumentReference("F001-1");
        input.setFechaEmisionDocumentReference(calendar.getTimeInMillis());
        input.setMotivoBajaDocumentReference("motivo baja");
        input.setTipoDocumentReference(Catalog1.FACTURA.toString());

        return input;
    }

    public static SummaryDocumentInputModel buildSummaryDocumentInputModel() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.NOVEMBER, 8);


        SummaryDocumentInputModel input = new SummaryDocumentInputModel();
        input.setNumero(1);
        input.setFechaEmision(calendar.getTimeInMillis());

        calendar.add(Calendar.DAY_OF_MONTH, -1);
        input.setFechaEmisionDocumentReference(calendar.getTimeInMillis());

        input.setFirmante(getFirmante());
        input.setProveedor(getProveedor());

        List<SummaryDocumentLineInputModel> detalle = new ArrayList<>();
        input.setDetalle(detalle);

        SummaryDocumentLineInputModel item = new SummaryDocumentLineInputModel();
        detalle.add(item);


        item.setTipoComprobante(Catalog1.BOLETA.toString());
        item.setSerieNumero("B001-1");
        item.setCliente(getClienteConDNI());
        item.setTipoOperacion(Catalog19.ADICIONAR.toString());
        item.setImporteTotal(new BigDecimal("100"));
        item.setTotalOperacionesGravadas(new BigDecimal("100"));
        item.setIgv(new BigDecimal("18"));

        return input;
    }
}
