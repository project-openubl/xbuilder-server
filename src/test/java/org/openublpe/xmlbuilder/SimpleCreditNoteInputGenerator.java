package org.openublpe.xmlbuilder;

import org.openublpe.xmlbuilder.models.input.creditNote.CreditNoteInputModel;

import java.util.Date;

public class SimpleCreditNoteInputGenerator implements CreditNoteInputGenerator {
    @Override
    public CreditNoteInputModel getCreditNote() {
        CreditNoteInputModel input = new CreditNoteInputModel();

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
