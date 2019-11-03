package org.openublpe.xmlbuilder;

import org.openublpe.xmlbuilder.models.input.debitNote.DebitNoteInputModel;

import java.util.Date;

public class SimpleDebitNoteInputGenerator implements DebitNoteInputGenerator {
    @Override
    public DebitNoteInputModel getDebitNote() {
        DebitNoteInputModel input = new DebitNoteInputModel();

        input.setSerie("F001");
        input.setNumero(123);
        input.setFechaEmision(new Date().getTime());

        input.setFirmante(FirmanteInputGenerator.getFirmante());

        input.setProveedor(ProveedorInputGenerator.getProveedor());
        input.setCliente(ClienteInputGenerator.getCliente());


        input.setSerieNumeroInvoiceReference("F009-9");
        input.setDescripcionSustentoInvoiceReference("El cliente lo rechazó");
        return input;
    }
}
