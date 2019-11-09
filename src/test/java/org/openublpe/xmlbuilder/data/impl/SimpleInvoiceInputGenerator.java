package org.openublpe.xmlbuilder.data.impl;

import org.openublpe.xmlbuilder.data.ClienteInputGenerator;
import org.openublpe.xmlbuilder.data.FirmanteInputGenerator;
import org.openublpe.xmlbuilder.data.InvoiceInputGenerator;
import org.openublpe.xmlbuilder.data.ProveedorInputGenerator;
import org.openublpe.xmlbuilder.models.input.general.DetalleInputModel;
import org.openublpe.xmlbuilder.models.input.general.invoice.InvoiceInputModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SimpleInvoiceInputGenerator implements InvoiceInputGenerator {

    @Override
    public InvoiceInputModel getInvoice() {
        InvoiceInputModel input = new InvoiceInputModel();
        input.setSerie("F004");
        input.setNumero(584);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.NOVEMBER, 8);
        input.setFechaEmision(calendar.getTimeInMillis());

        input.setFirmante(FirmanteInputGenerator.getFirmante());

        input.setProveedor(ProveedorInputGenerator.getProveedor());
        input.setCliente(ClienteInputGenerator.getClienteConRUC());

        List<DetalleInputModel> detalle = new ArrayList<>();
        input.setDetalle(detalle);

        DetalleInputModel item1 = new DetalleInputModel();
        detalle.add(item1);
        item1.setDescripcion("Item1");
        item1.setCantidad(BigDecimal.ONE);
        item1.setPrecioUnitario(new BigDecimal("65"));

        return input;
    }

}
