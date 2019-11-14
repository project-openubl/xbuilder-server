package org.openublpe.xmlbuilder.inputData.homologacion.grupo4;

import org.openublpe.xmlbuilder.inputData.CreditNoteInputGenerator;
import org.openublpe.xmlbuilder.models.input.standard.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.models.input.standard.note.creditNote.CreditNoteInputModel;

import java.util.Calendar;

public class Caso37_CreditNoteGenerator implements CreditNoteInputGenerator {

    public static CreditNoteInputModel CREDIT_NOTE;

    @Override
    public CreditNoteInputModel getInput() {
        if (CREDIT_NOTE == null) {
            synchronized (this) {
                if (CREDIT_NOTE == null) {

                    CREDIT_NOTE = new CreditNoteInputModel();
                    CREDIT_NOTE.setSerie("FF14");
                    CREDIT_NOTE.setNumero(1);

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(2019, Calendar.NOVEMBER, 9, 8, 30, 0);
                    CREDIT_NOTE.setFechaEmision(calendar.getTimeInMillis());

                    // get invoice
                    InvoiceInputModel invoice = Caso33_InvoiceGenerator.INVOICE;

                    // copy
                    CREDIT_NOTE.setFirmante(invoice.getFirmante());
                    CREDIT_NOTE.setProveedor(invoice.getProveedor());
                    CREDIT_NOTE.setCliente(invoice.getCliente());
                    CREDIT_NOTE.setDetalle(invoice.getDetalle());

                    CREDIT_NOTE.setSerieNumeroInvoiceReference(invoice.getSerie() + "-" + invoice.getNumero());
                    CREDIT_NOTE.setDescripcionSustentoInvoiceReference("mi descripcion o sustento");
                }
            }
        }

        return CREDIT_NOTE;
    }

}
