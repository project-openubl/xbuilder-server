package org.openublpe.xmlbuilder.inputData.invoice;

import org.openublpe.xmlbuilder.inputData.GeneralData;
import org.openublpe.xmlbuilder.inputData.InvoiceInputGenerator;
import org.openublpe.xmlbuilder.models.input.standard.DetalleInputModel;
import org.openublpe.xmlbuilder.models.input.standard.invoice.InvoiceInputModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Invoice_SimpleInputGenerator implements InvoiceInputGenerator {

    @Override
    public InvoiceInputModel getInput() {
        InvoiceInputModel input = new InvoiceInputModel();
        input.setSerie("F001");
        input.setNumero(1);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.NOVEMBER, 8);
        input.setFechaEmision(calendar.getTimeInMillis());

        input.setFirmante(GeneralData.getFirmante());
        input.setProveedor(GeneralData.getProveedor());
        input.setCliente(GeneralData.getClienteConRUC());

        List<DetalleInputModel> detalle = new ArrayList<>();
        input.setDetalle(detalle);

        DetalleInputModel item1 = new DetalleInputModel();
        detalle.add(item1);
        item1.setDescripcion("Item");
        item1.setCantidad(BigDecimal.ONE);
        item1.setPrecioUnitario(new BigDecimal("100"));

        return input;
    }

}
