package org.openublpe.xmlbuilder.data;

import org.openublpe.xmlbuilder.models.input.standard.DetalleInputModel;
import org.openublpe.xmlbuilder.models.input.standard.invoice.InvoiceInputModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public abstract class AbstractInvoiceInputGenerator implements InvoiceInputGenerator {

    protected InvoiceInputModel getInvoiceTemplate() {
        InvoiceInputModel input = new InvoiceInputModel();
        input.setSerie("F001");
        input.setNumero(1);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.FEBRUARY, 1, 18, 30, 0);
        input.setFechaEmision(calendar.getTimeInMillis());

        input.setFirmante(GeneralData.getFirmante());
        input.setProveedor(GeneralData.getProveedor());
        input.setCliente(GeneralData.getClienteConRUC());

        List<DetalleInputModel> detalle = new ArrayList<>();
        input.setDetalle(detalle);

        return input;
    }

}
