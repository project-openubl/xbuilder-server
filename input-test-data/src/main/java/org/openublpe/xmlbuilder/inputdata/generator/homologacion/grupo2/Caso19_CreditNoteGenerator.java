package org.openublpe.xmlbuilder.inputdata.generator.homologacion.grupo2;

import org.openublpe.xmlbuilder.core.models.input.standard.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.note.creditNote.CreditNoteInputModel;
import org.openublpe.xmlbuilder.inputdata.generator.CreditNoteInputGenerator;

import java.util.Calendar;

public class Caso19_CreditNoteGenerator implements CreditNoteInputGenerator {

    private static volatile CreditNoteInputModel CREDIT_NOTE;

    public static CreditNoteInputModel getInstance() {
        CreditNoteInputModel creditNote = CREDIT_NOTE;
        if (creditNote == null) {
            synchronized (Caso19_CreditNoteGenerator.class) {
                creditNote = CREDIT_NOTE;
                if (creditNote == null) {

                    CREDIT_NOTE = creditNote = new CreditNoteInputModel();
                    creditNote.setSerie("FF12");
                    creditNote.setNumero(3);

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(2019, Calendar.NOVEMBER, 9, 8, 30, 0);
                    creditNote.setFechaEmision(calendar.getTimeInMillis());

                    // get invoice
                    InvoiceInputModel invoice = Caso16_InvoiceGenerator.getInstance();

                    // copy
                    creditNote.setFirmante(invoice.getFirmante());
                    creditNote.setProveedor(invoice.getProveedor());
                    creditNote.setCliente(invoice.getCliente());
                    creditNote.setDetalle(invoice.getDetalle());

                    creditNote.setSerieNumeroInvoiceReference(invoice.getSerie() + "-" + invoice.getNumero());
                    creditNote.setDescripcionSustentoInvoiceReference("mi descripcion o sustento");
                }
            }
        }

        return creditNote;
    }

    @Override
    public CreditNoteInputModel getInput() {
        return getInstance();
    }

}
