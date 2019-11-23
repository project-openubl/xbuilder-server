package org.openublpe.xmlbuilder.inputData.homologacion.grupo8;

import org.openublpe.xmlbuilder.inputData.CreditNoteInputGenerator;
import org.openublpe.xmlbuilder.models.input.standard.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.models.input.standard.note.creditNote.CreditNoteInputModel;

import java.util.Calendar;

public class Caso58_CreditNoteGenerator implements CreditNoteInputGenerator {

    private static volatile CreditNoteInputModel CREDIT_NOTE;

    public static CreditNoteInputModel getInstance() {
        CreditNoteInputModel creditNote = CREDIT_NOTE;
        if (creditNote == null) {
            synchronized (Caso58_CreditNoteGenerator.class) {
                creditNote = CREDIT_NOTE;
                if (creditNote == null) {

                    CREDIT_NOTE = creditNote = new CreditNoteInputModel();
                    creditNote.setSerie("FF14");
                    creditNote.setNumero(2);

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(2019, Calendar.NOVEMBER, 9, 8, 30, 0);
                    creditNote.setFechaEmision(calendar.getTimeInMillis());

                    // get invoice
                    InvoiceInputModel invoice = Caso54_InvoiceGenerator.getInstance();

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
