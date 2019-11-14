package org.openublpe.xmlbuilder.data.impl;

import org.openublpe.xmlbuilder.data.DebitNoteInputGenerator;
import org.openublpe.xmlbuilder.data.GeneralData;
import org.openublpe.xmlbuilder.models.input.standard.DetalleInputModel;
import org.openublpe.xmlbuilder.models.input.standard.note.debitNote.DebitNoteInputModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DebitNote_SimpleInputGenerator implements DebitNoteInputGenerator {
    @Override
    public DebitNoteInputModel getInput() {
        DebitNoteInputModel input = new DebitNoteInputModel();

        input.setSerie("F001");
        input.setNumero(123);
        input.setFechaEmision(new Date().getTime());

        input.setFirmante(GeneralData.getFirmante());

        input.setProveedor(GeneralData.getProveedor());
        input.setCliente(GeneralData.getClienteConRUC());

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
}
