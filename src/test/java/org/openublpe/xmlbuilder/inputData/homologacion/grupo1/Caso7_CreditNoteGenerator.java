package org.openublpe.xmlbuilder.inputData.homologacion.grupo1;

import org.openublpe.xmlbuilder.inputData.CreditNoteInputGenerator;
import org.openublpe.xmlbuilder.models.input.standard.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.models.input.standard.note.creditNote.CreditNoteInputModel;

import java.util.Date;

/**
 * Nota de credito de caso 3
 */
public class Caso7_CreditNoteGenerator implements CreditNoteInputGenerator {

    private static volatile CreditNoteInputModel CREDIT_NOTE;

    public static CreditNoteInputModel getInstance() {
        CreditNoteInputModel creditNote = CREDIT_NOTE;
        if (creditNote == null) {
            synchronized (Caso7_CreditNoteGenerator.class) {
                creditNote = CREDIT_NOTE;
                if (creditNote == null) {

                    CREDIT_NOTE = creditNote = new CreditNoteInputModel();

                    creditNote.setSerie("FF11");
                    creditNote.setNumero(2);
                    creditNote.setFechaEmision(new Date().getTime());

                    // Get invoice
                    InvoiceInputModel invoice = Caso3_InvoiceGenerator.getInstance();

                    // Copy
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
